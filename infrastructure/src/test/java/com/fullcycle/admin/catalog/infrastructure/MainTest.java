package com.fullcycle.admin.catalog.infrastructure;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaEntity;
import com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.AbstractEnvironment;

import java.util.List;

public class MainTest {

    @Test
    public void testMain(){
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"test");
        Assertions.assertNotNull(new Main());
        Main.main(new String[]{});
    }


}
