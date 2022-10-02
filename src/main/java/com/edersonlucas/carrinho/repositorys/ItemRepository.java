package com.edersonlucas.carrinho.repositorys;

import com.edersonlucas.carrinho.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
