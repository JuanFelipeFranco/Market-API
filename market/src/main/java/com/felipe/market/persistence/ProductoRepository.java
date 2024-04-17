package com.felipe.market.persistence;

import com.felipe.market.domain.Product;
import com.felipe.market.domain.repository.ProductRepository;
import com.felipe.market.persistence.crud.ProductoCrudRepository;
import com.felipe.market.persistence.entity.Producto;
import com.felipe.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//nuestro productoRepository quedo orientado al dominio. con esto evitamos que nuestro proyecto se acople a cualquier tipo de BD
@Repository //le indicamos a spring que esta clase interactua con la DB
public class ProductoRepository implements ProductRepository {
    @Autowired // contenedor de inversion de control, nos ayuda spring a crear esas instancias y nos ahorramos el tiempo
    private ProductoCrudRepository productoCrudRepository; //estan declarados pero no han sido instanciados ni inicializando por ende ambos serian nulos y habria una null pointer intersection.
    @Autowired //se debe tener en cuenta que el objeto inyectado es un componente de spring;
    private ProductMapper mapper;//es un componente que es de mapstruct pero le decimos que es un componentemodelo de spring y se puede usar.

    //una lista que va recuperar todos los productos de nuestra DB
    @Override
    public List<Product> getAll(){
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return mapper.toProducts(productos); //toproducts realiza el proceso.
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List <Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(mapper.toProducts(productos)); //utilizamos of ya que espera una lista oprionl y nosotros tenemos una lista normal le enviamos of.
    } //mapper se encarga de convertir los productos en products y de alli al optional


    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findBycantidadStockLessThanAndEstado(quantity,true);
        return productos.map(prods -> mapper.toProducts(prods)); //le hacemos un map a los productos y la operacion de todos ellos es al mapper enviarle toproducts que me mande los prods,
    }//la lambda recibe los productos y los convierte a product y los retorna.

    @Override
    public Optional<List<Product>> getLowPrices(int price) {
        Optional<List<Producto>> productos = productoCrudRepository.findByprecioVentaLessThan(price);
        return productos.map(prods->mapper.toProducts(prods));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId).map(producto -> mapper.toProduct(producto));//se retorna directo debido a que findbyid retorna un optional
    } //le hacemos map al producto y en ese caso no lo convertimos a una lista si no que devuelva un producto.

    @Override
    public Product save(Product product) {
        Producto producto = mapper.toProducto(product); //como save espera un producto le hacemos la conversion inversa convertimos de product a producto.
        return mapper.toProduct(productoCrudRepository.save(producto));
    }

    @Override
    public void delete(int productId){
        productoCrudRepository.deleteById(productId);
    }

}
