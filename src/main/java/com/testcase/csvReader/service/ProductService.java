package com.testcase.csvReader.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.testcase.csvReader.csvmapper.ProductCsvMapper;
import com.testcase.csvReader.entity.Product;
import com.testcase.csvReader.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Service
public class ProductService {

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProductsToRepository() throws IOException {
        parseCsv();
        logger.info("Products has been saved to repository");
    }

    /**
     * The method <b>'parseCsv'</b> is used to accept a .csv file,
     * parse every line of it to mapped POJO object and saves it to repository one by one.
     * As mapper, we use previously created class with fields that have @CsvBinByName annotations.
     * Here we accept static file from resources.
     **/
    @CachePut(cacheNames = "savedProducts", key = "#id")
    private void parseCsv() throws IOException {
        try (Reader reader = new BufferedReader(
                new FileReader("src/main/resources/static/sampleProducts.csv"))) {
                HeaderColumnNameMappingStrategy<ProductCsvMapper> strategy =
                        new HeaderColumnNameMappingStrategy<>();

                strategy.setType(ProductCsvMapper.class);

                CsvToBean<ProductCsvMapper> csvToBean =
                        new CsvToBeanBuilder<ProductCsvMapper>(reader)
                                .withMappingStrategy(strategy)
                                .withIgnoreEmptyLine(true)
                                .withIgnoreLeadingWhiteSpace(true)
                                .build();

                csvToBean.parse().forEach(line -> {
                    Product product = new Product(
                            line.getProdId(),
                            line.getProdName()
                    );
                    productRepository.saveAndFlush(product);
                });
            }
        }
}
