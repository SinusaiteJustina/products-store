package lt.bit.products.store.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "store_items")
public class Item {
    @Id
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;

    public Item(Integer productId, Integer quantity, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
    public Item() {}
    public Integer getProductId() {
        return productId;
    }

    public void setId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}
