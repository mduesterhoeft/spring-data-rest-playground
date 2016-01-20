package com.example;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "productExcerpt", types = Product.class)
public interface ProductProjection {
    String getName();
}
