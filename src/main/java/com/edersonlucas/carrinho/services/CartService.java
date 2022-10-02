package com.edersonlucas.carrinho.services;

import com.edersonlucas.carrinho.controllers.dto.ItemDto;
import com.edersonlucas.carrinho.models.Cart;
import com.edersonlucas.carrinho.models.Item;

public interface CartService {
    Item addItemToCart(ItemDto itemDto);
    Cart seeCart(Long id);
    Cart closeCart(Long id, int formOfPayment);
}
