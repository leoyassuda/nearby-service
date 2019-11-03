package com.lny.nearby.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EncryptService {

    @Value("${test.enc}")
    private String property;

    public String getProperty() {
        return property;
    }

}
