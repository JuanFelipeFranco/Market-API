package com.felipe.market.persistence.mapper;

import com.felipe.market.domain.PurchaseItem;
import com.felipe.market.persistence.entity.ComprasProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface PurchaseItemMapper {
    @Mappings({
            @Mapping(source = "id.idProducto", target = "productId"),
            @Mapping(source = "cantidad", target = "quantity"),
            @Mapping(source = "estado", target = "active")
            //el total como en la entity compras producto es igual a purchaseitem en ambos se llama total, no la tenemos en cuenta en el mapping.
    })
    //  del entity comprasProducto a purchase Item del dom
    PurchaseItem toPurchaseItem(ComprasProducto producto);

    //inversa: de PurchaseItem a comprasProducto
    @InheritInverseConfiguration
    @Mappings({ //LOS QUE SE VAN A IGNORAR.
            @Mapping(target = "compra",ignore = true),
            @Mapping(target = "producto",ignore = true), //lo referenciamos en el uses.
            @Mapping(target = "id.idCompra",ignore = true)
    })
    ComprasProducto toComprasProducto(PurchaseItem item);
}
