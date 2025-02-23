package com.testcase.csvReader.dtomapper;

import com.testcase.csvReader.dto.ProductDto;
import com.testcase.csvReader.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductToDtoMapper {
    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getProductName()
        );
    }
}
