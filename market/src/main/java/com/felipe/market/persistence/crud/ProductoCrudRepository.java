package com.felipe.market.persistence.crud;

import com.felipe.market.persistence.entity.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto,Integer> {
    //recuperando todos la lista de los productos que pertenecen a una categoria en especifico
    //@Query(value = "SELECT * FROM productos WHERE id_categoria = ?", nativeQuery = true) de manera query y no se necesita la estructura de query method
    List<Producto> findByIdCategoriaOrderByNombreAsc(int idCategoria); //nuestro querymethod

    //queremos obtener la lista de productos escasos y que esten activos osea se vende actualmente
    Optional<List<Producto>> findBycantidadStockLessThanAndEstado(int cantidadStock, boolean estado);

    Optional<List<Producto>> findByprecioVentaLessThan(int precioVenta);
}
