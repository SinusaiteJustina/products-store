package lt.bit.products.store.service;

import lt.bit.products.store.model.Item;
import lt.bit.products.store.model.Product;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository repository;
    private final ItemRepository itemRepository;

    public ProductService(ProductRepository repository, ItemRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }


    public List<Product> findProducts() {
        return repository.findAll();
    }
    public List<Item> findItems() {return itemRepository.findAll();}

    public Product findProduct(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Item findItem(Integer productId) {return itemRepository.findById(productId).orElse(null); }

    public void deleteProduct(Integer id) {
        itemRepository.deleteAllByProductId(id);
        repository.deleteById(id);

    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public long countProducts() {

        return repository.count();
    }
}
