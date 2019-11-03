package com.lny.nearby.context;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TestResourcePropertySourceResolverIntegrationTest {

    @Value("${example.firstProperty}")
    private String firstProperty;

    @Test
    public void shouldTestResourceFile_overridePropertyValues() {
        assertEquals("file", firstProperty);
    }
}
