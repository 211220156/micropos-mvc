package com.micropos.batch.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.micropos.batch.jpa.ProductRepository;
import com.micropos.batch.jpa.ReviewRepository;
import com.micropos.batch.model.AmazonProduct;
import com.micropos.batch.model.Review;
import com.micropos.batch.service.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.net.HttpURLConnection;
import java.net.URL;


@Configuration
//@EnableBatchProcessing //不注释的话springboot应用启动时不会自动运行job
public class BatchConfig {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Bean
    public ItemReader<JsonNode> ProductItemReader() {
        System.out.println("ProductItemReader");
//        return new JsonFileReader("E:/meta_Digital_Music.jsonl");
        return new JsonFileReader("E:/meta_Gift_Cards.jsonl");
    }
    @Bean
    public ItemReader<JsonNode> ReviewItemReader() {
        System.out.println("ReviewItemReader");
        return new JsonFileReader("E:/Gift_Cards.jsonl");
//        return new JsonFileReader("E:/Digital_Music.jsonl");
    }
    @Bean
    public ItemProcessor<JsonNode, Review> ReviewItemProcessor() {
        System.out.println("ReviewItemProcessor");
        return new ReviewProcessor();
    }
    @Bean
    public ItemProcessor<JsonNode, AmazonProduct> ProductItemProcessor() {
        System.out.println("ProductItemProcessor");
        return new ProductProcessor();
    }
    @Bean
    public RepositoryItemWriter<Review> ReviewItemWriter() {
        System.out.println("ReviewItemWriter");
        RepositoryItemWriter<Review> writer = new ReviewWriter();
        writer.setRepository(reviewRepository);
        return writer;
    }
    @Bean
    public RepositoryItemWriter<AmazonProduct> ProductItemWriter() {
        System.out.println("ProductItemWriter");
        RepositoryItemWriter<AmazonProduct> writer = new ProductWriter();
        writer.setRepository(productRepository);
        return writer;
    }
    @Bean
    public Job ReviewJob(JobRepository jobRepository, Step parseReview, JobCompletionNotificationListener listener) {
        System.out.println("review job");
        return new JobBuilder("parseReviewJob", jobRepository)
                .listener(listener)
                .start(parseReview)
                .build();
    }
    @Bean
    public Job ProductJob(JobRepository jobRepository, Step parseProduct, JobCompletionNotificationListener listener) {
        System.out.println("product Job");
        return new JobBuilder("parseProductJob", jobRepository)
                .listener(listener)
                .start(parseProduct)
                .build();
    }
    @Bean
    public Step parseReview(JobRepository jobRepository,
                            ItemReader<JsonNode> ReviewItemReader, ItemProcessor<JsonNode, Review> ReviewItemProcessor, RepositoryItemWriter<Review> ReviewItemWriter) {
        System.out.println("parseReview Step");
        return new StepBuilder("parseReview", jobRepository)
                .<JsonNode, Review>chunk(1000, new ResourcelessTransactionManager())
                .reader(ReviewItemReader)
                .processor(ReviewItemProcessor)
                .writer(ReviewItemWriter)
                .taskExecutor(taskExecutor())
                .build();
    }
    @Bean
    public Step parseProduct(JobRepository jobRepository,
                             ItemReader<JsonNode> ProductItemReader, ItemProcessor<JsonNode, AmazonProduct> ProductProcessor, RepositoryItemWriter<AmazonProduct> writer) {
        System.out.println("parseProduct Step");
        return new StepBuilder("parseProduct", jobRepository)
                .<JsonNode, AmazonProduct>chunk(1000, new ResourcelessTransactionManager())
                .reader(ProductItemReader)
                .processor(ProductProcessor)
                .writer(writer)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(20);
        return executor;
    }
    public static Boolean isImagesTrue(String imgUrl) {
        if (imgUrl == null) return false;

        int RESPONSE_CODE = 0;
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            RESPONSE_CODE = urlcon.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (RESPONSE_CODE == HttpURLConnection.HTTP_OK) {
            System.out.println("posted ok!");
            return true;
        } else {
            System.out.println("Bad post...");
            return false;
        }
    }
}
