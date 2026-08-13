package com.placement.management;

import com.placement.management.console.PlacementConsoleApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Standalone Console Application Runner.
 * Right-click and Run this class in IntelliJ IDEA to run the Placement Management System
 * purely as a Core Java Terminal CLI application without starting an HTTP Web Server!
 */
public class StandaloneConsoleRunner {

    public static void main(String[] args) {
        System.setProperty("spring.main.banner-mode", "off");

        // Start Spring Application context without starting Tomcat Web Server
        SpringApplication app = new SpringApplication(PlacementManagementSystemApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);

        ConfigurableApplicationContext context = app.run(args);

        // Fetch Console App Bean and launch interactive menu
        PlacementConsoleApp consoleApp = context.getBean(PlacementConsoleApp.class);
        consoleApp.startInteractiveConsole();

        context.close();
    }
}
