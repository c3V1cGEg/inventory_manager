package com.inventorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = { InventorerApplication.class })
public class InventorerApplication {
  public static void main(String[] args) {
    SpringApplication.run(InventorerApplication.class, args);
  }
}