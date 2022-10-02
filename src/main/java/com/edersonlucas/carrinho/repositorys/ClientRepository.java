package com.edersonlucas.carrinho.repositorys;

import com.edersonlucas.carrinho.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
