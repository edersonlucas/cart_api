package com.edersonlucas.carrinho.controllers;

import com.edersonlucas.carrinho.controllers.dto.ItemDto;
import com.edersonlucas.carrinho.models.Cart;
import com.edersonlucas.carrinho.models.Item;
import com.edersonlucas.carrinho.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public Item addItemToCart(@RequestBody ItemDto itemDto) {
        return cartService.addItemToCart(itemDto);
    }

    @GetMapping("/{id}")
    public Cart seeCart(@PathVariable("id") Long id) {
        return cartService.seeCart(id);
    }

    @PutMapping("/close/{cartId}")
    public Cart closeCar(@PathVariable("cartId") Long cartId, @RequestParam("formOfPayment") int formOfPayment) {
        return cartService.closeCart(cartId, formOfPayment);
    }
}
