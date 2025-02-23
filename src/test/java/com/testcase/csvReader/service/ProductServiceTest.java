package com.testcase.csvReader.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class ProductServiceTest {


    @Test
    void saveProductsToRepository() throws IOException {
        ProductService service = Mockito.mock(ProductService.class);

        service.saveProductsToRepository();

        Mockito.verify(service).saveProductsToRepository();
    }
}