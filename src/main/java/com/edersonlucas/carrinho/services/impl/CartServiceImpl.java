package com.edersonlucas.carrinho.services.impl;

import com.edersonlucas.carrinho.controllers.dto.ItemDto;
import com.edersonlucas.carrinho.enumeration.FormOfPayment;
import com.edersonlucas.carrinho.models.Cart;
import com.edersonlucas.carrinho.models.Item;
import com.edersonlucas.carrinho.models.Restaurant;
import com.edersonlucas.carrinho.repositorys.CartRepository;
import com.edersonlucas.carrinho.repositorys.ItemRepository;
import com.edersonlucas.carrinho.repositorys.ProductRepository;
import com.edersonlucas.carrinho.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    @Override
    public Item addItemToCart(ItemDto itemDto) {
        Cart cart = seeCart(itemDto.getCartId());
        if (cart.isClosed()) {
            throw new RuntimeException("The cart is closed!");
        }
       Item itemToBeAdded = Item.builder()
                .quantity(itemDto.getQuantity())
                .cart(cart)
                .product(productRepository.findById(itemDto.getProductId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("This product does not exist!");
                        }
                ))
                .build();
        List<Item> cartItems = cart.getItems();
        if (cartItems.isEmpty()) {
            cartItems.add(itemToBeAdded);
        } else {
            Restaurant currentRestaurant = cartItems.get(0).getProduct().getRestaurant();
            Restaurant currentItemRestaurantToBeAdded = itemToBeAdded.getProduct().getRestaurant();
            if (currentRestaurant.equals(currentItemRestaurantToBeAdded)) {
                cartItems.add(itemToBeAdded);
            } else {
                throw new RuntimeException("Cannot add products from different restaurants!");
            }
        }

        List<Double> valueOfItems = new ArrayList<>();

        for (Item cartItem: cartItems) {
            double totalItemValue = cartItem.getProduct().getUnitValue() * cartItem.getQuantity();
            valueOfItems.add(totalItemValue);
        }

        double totalValueCart = valueOfItems.stream()
                .mapToDouble(valueOfEachItem -> valueOfEachItem)
                .sum();

        cart.setTotalValue(totalValueCart);
        cartRepository.save(cart);
        return itemRepository.save(itemToBeAdded);
    }

    @Override
    public Cart seeCart(Long id) {
        return cartRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("This cart does not exist!");
                }
        );
    }

    @Override
    public Cart closeCart(Long id, int numFormOfPayment) {
        Cart cart = seeCart(id);
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Add items to cart!");
        }

        FormOfPayment formOfPayment = numFormOfPayment == 0 ? FormOfPayment.money : FormOfPayment.cardMachine;

        cart.setFormOfPayment(formOfPayment);
        cart.setClosed(true);
        return cartRepository.save(cart);
    }
}
