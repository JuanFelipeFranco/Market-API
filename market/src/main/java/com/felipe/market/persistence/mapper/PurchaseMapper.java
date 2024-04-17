package com.felipe.market.persistence.mapper;

import com.felipe.market.domain.Purchase;
import com.felipe.market.persistence.entity.Compra;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",uses={PurchaseItemMapper.class}) //le decimos que vamos a usar el purchaseItemMapper, porque internamente vamos a mapear dentro de la compra todos sus productos.
public interface PurchaseMapper {
    @Mappings({
            @Mapping(source ="idCompra" ,target = "purchaseId"),
            @Mapping(source ="idCliente" ,target = "clientId"),
            @Mapping(source ="fecha" ,target = "date"),
            @Mapping(source ="medioPago" ,target = "paymentMethod"),
            @Mapping(source ="comentario" ,target = "comment"),
            @Mapping(source ="estado" ,target = "state"),
            @Mapping(source ="productos" ,target = "items") //este mapping es el que usa purchaseitemmapper para convertir los productos 1 a 1
    })
    //obtener un purchase a partir de compra
    Purchase toPurchase (Compra compra);
    List<Purchase> toPurchases (List<Compra> compras);

    //obtener una compra a partir de purchase.
    @InheritInverseConfiguration
    @Mapping(target = "cliente",ignore = true)
    Compra toCompra(Purchase purchase);
    //siempre en la clase destino debe tener todos los mapeos, si no los tiene debemos ignorarlos.

}
