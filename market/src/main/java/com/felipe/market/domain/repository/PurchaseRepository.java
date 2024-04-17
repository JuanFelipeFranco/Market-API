package com.felipe.market.domain.repository;

import com.felipe.market.domain.Purchase;

import java.util.List;
import java.util.Optional;

//especificacion de lo que quiero definir.
public interface PurchaseRepository {
    List<Purchase> getAll();
    Optional<List<Purchase>> getByClient(String clientId); //como hay clientes que no tiene compras usamos optional
    Purchase save(Purchase purchase);
}
