package com.placement.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlacementManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlacementManagementSystemApplication.class, args);
        System.out.println("\n=======================================================");
        System.out.println(" Placement Management System Backend Started Successfully!");
        System.out.println(" REST API Base URL: http://localhost:8080/api");
        System.out.println(" H2 Database Console: http://localhost:8080/h2-console");
        System.out.println("=======================================================\n");
    }
}
