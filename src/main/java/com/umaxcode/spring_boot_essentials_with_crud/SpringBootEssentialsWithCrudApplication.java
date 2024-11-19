package com.umaxcode.spring_boot_essentials_with_crud;

import com.umaxcode.spring_boot_essentials_with_crud.config.ConfigurationProp;
import com.umaxcode.spring_boot_essentials_with_crud.config.ProfileComponent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootEssentialsWithCrudApplication {

    public static void main(String[] args) {

        /**
         * This is another way to select a profile
         */
        // SpringApplication application = new SpringApplication(SpringBootEssentialsWithCrudApplication.class);
        // application.setAdditionalProfiles("prod");
        // application.run(args);

        SpringApplication.run(SpringBootEssentialsWithCrudApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            ConfigurationProp configurationProp = ctx.getBean(ConfigurationProp.class);
            System.out.println(configurationProp.getGreeting());
            System.out.println(configurationProp.getRand());
            System.out.println(configurationProp.getSecurity().getUsername());
            System.out.println(configurationProp.getSecurity().getPassword());
            System.out.println(configurationProp.getSecurity().getRoles());
            System.out.println(configurationProp.getSecurity().getPermissions());

            ProfileComponent profileComponent = ctx.getBean(ProfileComponent.class);

            profileComponent.display();
        };
    }

}
