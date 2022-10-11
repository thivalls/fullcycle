package com.fullcycle.admin.catalog.infrastructure;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaEntity;
import com.fullcycle.admin.catalog.infrastructure.category.repository.CategoryJpaRepository;
import com.fullcycle.admin.catalog.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
//        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "development");
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class, args);
    }

    // @Bean
    // descomentar para testar
    public ApplicationRunner runner(CategoryJpaRepository repository) {
        System.out.println("Funcionando");
        return args -> {
            List<CategoryJpaEntity> all = repository.findAll();

            Category filmes = Category.newCategory("filmes", "descroption", true);

            repository.saveAndFlush(CategoryJpaEntity.from(filmes));

            System.out.println("stop here");

            repository.deleteAll();
        };
    }
}