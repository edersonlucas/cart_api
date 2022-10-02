package com.edersonlucas.carrinho.repositorys;

import com.edersonlucas.carrinho.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
