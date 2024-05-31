package com.felipe.market.web.controller;

import com.felipe.market.domain.Product;
import com.felipe.market.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //indica a spring que esta clase va ser un controlador de una API rest
@RequestMapping("/products")  //lleva la peticion en el path que le indicamos
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    @Operation(summary = "Get all supermarket prdocuts")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity <List<Product>> getAll(){
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Search a product with an ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
    })
    public ResponseEntity<Product> getProduct(@Parameter(description = "The id of the product", required = true, example = "7")
            @PathVariable("id") int productId){
        return productService.getProduct(productId).map(product ->  new ResponseEntity<>(product,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Search for a category with a categoryID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
    })
    public ResponseEntity<List<Product>> getByCategory(@Parameter(description = "The id of the category", required = true, example = "3")
            @PathVariable("categoryId") int categoryId){
        return productService.getByCategory(categoryId).map(products -> new ResponseEntity<>(products,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    @Operation(summary = "Save a product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    public ResponseEntity<Product> save(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a product with a ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
    })
    public ResponseEntity delete(@Parameter(description = "The id of the product to DELETE", required = true, example = "51")
            @PathVariable("id") int productId){
        if (productService.delete(productId)){
            return new ResponseEntity(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
