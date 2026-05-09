package com.devworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductServiceApplication.class, args);
  }
}

// brew services restart postgresql@15
// createdb product_service
// brew services stop postgresql@15

// mvn spotless:apply - for code formatting
