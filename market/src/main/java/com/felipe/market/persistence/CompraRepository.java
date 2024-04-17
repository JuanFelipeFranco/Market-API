package com.felipe.market.persistence;

import com.felipe.market.domain.Purchase;
import com.felipe.market.domain.repository.PurchaseRepository;
import com.felipe.market.persistence.crud.CompraCrudRepository;
import com.felipe.market.persistence.entity.Compra;
import com.felipe.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {
    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper mapper;

    //como compraCrudRepository devulve una lista de Compra y queremos obtener una Lista de purchase para eso usamos mapper para que lo convierta.
    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    //.map nos sirve para operar con lo que viene dentro del optional; funcion lambda donde las compras las convierte en purchases.
    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId).map(compras -> mapper.toPurchases(compras));
    }

    //en este caso recibe un purchase; lo convertimos a compra gracias al mapper.
    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra(purchase);
        //tenemos que garantizar que la info se va guardar en cascada. compra conoce los productos y los productos conocen a que compra pertenece
        compra.getProductos().forEach(producto -> producto.setCompra(compra));
        return mapper.toPurchase(compraCrudRepository.save(compra));
    }
}
