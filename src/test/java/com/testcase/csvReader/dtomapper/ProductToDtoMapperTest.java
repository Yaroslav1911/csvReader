package com.testcase.csvReader.dtomapper;

import com.testcase.csvReader.dto.ProductDto;
import com.testcase.csvReader.entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductToDtoMapperTest {

    Product product = new Product(1154357L, "testName");
    ProductDto DTO  = new ProductDto(1154357L, "testName");

    @Test
    void toDto() {
        ProductDto productDto = ProductToDtoMapper.toDto(product);

        assertEquals(productDto, DTO);
    }
}