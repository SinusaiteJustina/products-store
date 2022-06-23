package lt.bit.products.store.jobs;

import lt.bit.products.store.model.Product;
import lt.bit.products.store.service.ItemRepository;
import lt.bit.products.store.service.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Component
class Production {
    private final static Logger LOG = LoggerFactory.getLogger(Production.class);
    @Value("${jobs.production.number_of_new_products}")
    private int numberOfNewProducts;
    private String[] ipAddressNumber;

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    public Production(ProductRepository productRepository, ItemRepository itemRepository) {
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;

        try {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            ipAddressNumber = ipAddress.split("\\.");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRateString = "15000")
    void addNewProducts() {
        LOG.info("Production job started");
        LOG.info("Adding products.. ");

        LOG.info("IP: " + Arrays.toString(ipAddressNumber));

        LOG.info("numberOfNewProducts: " + numberOfNewProducts);
        for (int i = 0; i < numberOfNewProducts; ++i ) {
            LOG.info("i=" + i + "->"+ createProduct(i) + " - SAVED !");
            LOG.info("Generated quantity for i={} -> {} ",i, getQuantity(i));
            productRepository.save(createProduct(i));
        }
        LOG.info("Generated price: {}",generatePrice());


    }

    private Product createProduct(int i) {
        Product product = new Product();
        product.setName(generateName(i));
        product.setDescription(generateDescription());
        product.setCreated(LocalDate.now());
        return product;
    }
    private String generateName(int i) {
        LocalDate date = LocalDate.now();
        String text = date.format(DateTimeFormatter.ofPattern("E"));
        String name = "";
            name = "" + text + "-" + (i+1);
        return name;
    }

    private String generateDescription() {
        LocalDate date = LocalDate.now();
        String text = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return "Desc " + String.format("%s", text);
    }

    private BigDecimal generatePrice() {
        LocalDateTime now = LocalDateTime.now();
        int min = now.getMinute();
        int sec = now.getSecond();
        return new BigDecimal(String.format("%d.%d", min, sec));
        // 2 variantas
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("m.s");
        // return new BigDecimal(now.format(formatter));
    }
    private int getQuantity(int index) {
        int ipNumberIndex = index >= ipAddressNumber.length ? index - ipAddressNumber.length : index;
        return Integer.parseInt(ipAddressNumber[ipNumberIndex]);

    }
}
