package com.testcase.csvReader.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.testcase.csvReader.csvmapper.TradeCsvMapper;
import com.testcase.csvReader.datevalidator.DateValidator;
import com.testcase.csvReader.dto.ProductDto;
import com.testcase.csvReader.dto.TradeDto;
import com.testcase.csvReader.dtomapper.ProductToDtoMapper;
import com.testcase.csvReader.dtomapper.TradeToDtoMapper;
import com.testcase.csvReader.entity.Trade;
import com.testcase.csvReader.repository.ProductRepository;
import com.testcase.csvReader.repository.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private final static String NO_PRODUCT_NAME = "Missing Product Name";

    Logger logger = LoggerFactory.getLogger(TradeService.class);

    private final TradeRepository tradeRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public TradeService(TradeRepository tradeRepository, ProductRepository productRepository, ProductService productService) {
        this.tradeRepository = tradeRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    /**
     * This method gets Optional productName of product by its id from repository.
     * If there is no name available its return <b>"Missing Product Name"</b>.
     * */
    @Cacheable(cacheNames = "products", key = "#id")
    public String getProductNameById(String id) {
        Optional<String> productName =
                productRepository
                        .findById(Long.valueOf(id))
                        .map(ProductToDtoMapper::toDto)
                        .map(ProductDto::productName);

        if (productName.isPresent()) {
            return productName
                    .map(s -> s.replaceAll("Optional\\[", "").replaceAll("]", ""))
                    .get();
        } else {
            logger.warn("missing name mapping of product with id {}", id);
            return NO_PRODUCT_NAME;
        }
    }


    /**
     * This method:<p>- runs the <b>"fillTheDatabase()"</b>, which is next under this method;</p>
     * <p>- collects all trades entities from repository and changes its id to product name;</p>
     * <p>- clear out the databases of Products and Trades,
     * to be ready to load new files without restarting the application;</p>
     * <p>- returns a list of trades DTO, after mapping it with stream API.</p>
     * */
    @Transactional
    public List<TradeDto> getTradesList(MultipartFile file) throws IOException {
        fillTheDatabase(file);

        List<Trade> tradeList = new ArrayList<>(tradeRepository.findAll());
        for (Trade trade : tradeList) {
            trade.setProdId(getProductNameById(trade.getProdId()));
        }

        tradeRepository.deleteAll();
        productRepository.deleteAll();

        return tradeList.stream().map(TradeToDtoMapper::toDto).toList();
    }

    /**
     * Uploading to ProductRepository data from static <b>'sampleProduct.csv'</b>. Also,
     * uploading to TradeRepository data from file of @RequestPart in TradeController.
     * */
    private void fillTheDatabase(MultipartFile file) throws IOException {
        parseCsv(file);
        productService.saveProductsToRepository();
        logger.info("Added to database all trades & products");
    }

    /**
     * Method <b>'enrich()'</b> collects a list of trades DTO by method <b>'getTradesList()'</b>,
     * and using StringBuilder creates a String that will look like .csv file in output.
     * */
    public String enrich(MultipartFile file) throws IOException {
        List<TradeDto> list = getTradesList(file);
        StringBuilder stringBuilder = buildTheString(list);

        return stringBuilder.toString();
    }

    private static StringBuilder buildTheString(List<TradeDto> list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("date,productName,currency,price\n");

        list.forEach(trade -> stringBuilder
                .append(trade.date()).append(",")
                .append(trade.prodId()).append(",")
                .append(trade.currency()).append(",")
                .append(trade.price()).append("\n"));
        return stringBuilder;
    }

    /**
     * The method <b>'parseCsv'</b> is used to accept a .csv file,
     * parse every line of it to mapped POJO object and saves it to repository one by one.
     * As mapper, we use previously created class with fields that have @CsvBinByName annotations.
     * Here we accept file from @RequestPart in controller class.
     * <p>Also, we're using <b>'filter'</b> in CsvToBeanBuilder to check is our <b>'date'</b> from line is valid.
     * If not, we log an error and skip the line.
     */
    @CachePut(cacheNames = "savedTrades", key = "#id")
    private void parseCsv(MultipartFile file) throws IOException {
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<TradeCsvMapper> strategy =
                    new HeaderColumnNameMappingStrategy<>();

            strategy.setType(TradeCsvMapper.class);

            DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
            DateValidator validator =  new DateValidator(formatter);

            CsvToBean<TradeCsvMapper> csvToBean =
                    new CsvToBeanBuilder<TradeCsvMapper>(reader)
                            .withMappingStrategy(strategy)
                            .withIgnoreEmptyLine(true)
                            .withIgnoreLeadingWhiteSpace(true)
                            .withFilter(line -> {
                                if (!validator.isValid(line[0])) logger.error("Date format is invalid");
                                return validator.isValid(line[0]);
                            })
                            .build();

            csvToBean.parse().forEach(line -> {
                Trade trade = new Trade(
                        line.getmDate(),
                        line.getmProdId(),
                        line.getmCurrency(),
                        line.getmPrice()
                );
                tradeRepository.saveAndFlush(trade);
            });
        }
    }
}
