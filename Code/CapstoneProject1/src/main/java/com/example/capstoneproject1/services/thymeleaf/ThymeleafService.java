package com.example.capstoneproject1.services.thymeleaf;

import java.util.Map;

public interface ThymeleafService {
    String createContent(String template, Map<String, Object> variables);
}
