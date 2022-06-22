package lt.bit.products.store.controller;

import java.util.List;

import lt.bit.products.store.model.Item;
import lt.bit.products.store.model.Product;
import lt.bit.products.store.model.ProductRequest;
import lt.bit.products.store.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ProductController.ROOT_MAPPING)
class ProductController {

    public static final String ROOT_MAPPING = "/products";
    public static final String ID_MAPPING = "/{id}";

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Product createProduct(@RequestBody ProductRequest productRequest) {
        return service.saveProduct(Product.from(productRequest));
    }

    @PutMapping(ID_MAPPING)
    ResponseEntity<Product> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable("id") Integer productId) {
        Product product = service.findProduct(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.saveProduct(Product.from(productRequest, productId)));
    }

    @GetMapping
    List<Product> fetchProducts() {

        return service.findProducts();
    }

    @GetMapping(ID_MAPPING)
    ResponseEntity<Product> fetchProduct(@PathVariable Integer id) {
        Product product = service.findProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @DeleteMapping(ID_MAPPING)
    ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer productId) {
        Product product = service.findProduct(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteProduct(productId);
        return ResponseEntity.noContent().build();

    }
    @GetMapping( "/count")
    long  countProducts() {
        return service.countProducts();
    }
    @GetMapping(ID_MAPPING + "/items")
    ResponseEntity<Item> fetchItem(@PathVariable("id") Integer productId) {
        Item items = service.findItem(productId);
        return items == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(items);
    }

}
