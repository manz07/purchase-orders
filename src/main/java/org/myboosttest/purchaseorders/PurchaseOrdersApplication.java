package org.myboosttest.purchaseorders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PurchaseOrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurchaseOrdersApplication.class, args);
    }

}
