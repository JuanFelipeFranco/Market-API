package com.felipe.market.persistence.mapper;

import com.felipe.market.domain.Product;
import com.felipe.market.persistence.entity.Producto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
//para convertir categoria en category usamos CategoryMapper, debido a que categoria tiene su mapper propio
@Mapper(componentModel = "spring", uses={CategoryMapper.class})
public interface ProductMapper {
    @Mappings({
            @Mapping(source = "idProducto",target = "productId"),
            @Mapping(source = "nombre",target = "name"),
            @Mapping(source = "idCategoria",target = "categoryId"),
            @Mapping(source = "precioVenta",target = "price"),
            @Mapping(source = "cantidadStock",target = "stock"),
            @Mapping(source = "estado",target = "active"),
            @Mapping(source = "categoria",target = "category"),
    })
    //de producto a product
    Product toProduct(Producto producto);
    List<Product> toProducts(List<Producto> productos); //retorna una lista de product que recibe una lista de productos de la clase producto

    //de product a producto
    @InheritInverseConfiguration
    @Mapping(target="codigoBarras", ignore = true)
    Producto toProducto(Product product);

}
