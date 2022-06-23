package lt.bit.products.store.jobs;

import lt.bit.products.store.model.Item;
import lt.bit.products.store.service.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
class Pricer {
    private final ItemRepository itemRepository;
    private final static Logger LOG = LoggerFactory.getLogger(Pricer.class);

    Pricer(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @Scheduled(fixedRateString = "125000")
    void recalculateProductPrice() {
        LOG.info("Pricer job started");
        LOG.info("Recalculating products prices... ");

       List<Item> listItems =  itemRepository.findAll();
       Random random = new Random();

        for (Item item : listItems) {
            BigDecimal oldPrice = item.getPrice();
//            double newPrice = random.nextDouble();
            BigDecimal randomVal = BigDecimal.valueOf(random.nextDouble() + 1);
            LocalDateTime now = LocalDateTime.now();
            BigDecimal newPrice = (now.getSecond() % 2 == 0)
                    ? oldPrice.multiply(randomVal)
                    : oldPrice.divide(randomVal, RoundingMode.UP);
            if (now.getHour() > 20) {
                newPrice = newPrice.multiply(BigDecimal.valueOf(0.8));
            }
            LOG.info(String.format("ID: %d, OLD PRICE %.2f, New Price: %.2f",
                    item.getProductId(),item.getPrice(), newPrice));
            item.setPrice(newPrice);
            itemRepository.save(item);

//
//            if (item.getPrice() > newPrice) {
//                newPrice = newPrice * 5.5;
//            } else {
//                newPrice = item.getPrice() - newPrice;
//            }
//            if (LocalDateTime.now().getHour() > 20) {
//                newPrice *= 0.2;
//            }
//            LOG.info("ID " + item.getProductId() + " OLD PRICE " + item.getPrice() + " New Price " + newPrice);

        }
        LOG.info("Product prices updated");

    }
}
