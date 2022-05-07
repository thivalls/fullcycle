package com.fullcycle.admin.catalog.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    @DisplayName("Show return not null")
    void test1 () {
        Assertions.assertNotNull(new Main());
        Main.main(new String[]{});
    }
}