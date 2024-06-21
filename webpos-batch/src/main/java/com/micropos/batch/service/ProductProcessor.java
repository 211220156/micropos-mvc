package com.micropos.batch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.batch.config.BatchConfig;
import com.micropos.batch.model.AmazonProduct;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class ProductProcessor implements ItemProcessor<JsonNode, AmazonProduct>, StepExecutionListener {
    private static final Random random = new Random();
    private ObjectMapper objectMapper;
    @Override
    public void beforeStep(StepExecution stepExecution) {
        objectMapper = new ObjectMapper();
    }
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
    @Override
    public AmazonProduct process(JsonNode jsonNode) throws Exception {
        System.out.println("product processing...");
//        return objectMapper.treeToValue(jsonNode, AmazonProduct.class);
        AmazonProduct product = objectMapper.treeToValue(jsonNode, AmazonProduct.class);
        product.setQuantity(random.nextInt(30));
        //测试图片url是否有效
//        if (!BatchConfig.isImagesTrue(product.getImages())) {
//            product.setImages(null);
//        }
        return product;
    }

}
