package com.umaxcode.spring_boot_essentials_with_crud.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class ProfileComponent {

    public void display() {
        System.out.println("Hello World");
    }
}
