package com.fullcycle.admin.catalog.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {
    @Test
    public void testNewCategory() {
        Assertions.assertNotNull(new Category());
    }
}
