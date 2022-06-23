package lt.bit.products.store.service;

import lt.bit.products.store.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    void deleteAllByProductId(Integer productId);
}
