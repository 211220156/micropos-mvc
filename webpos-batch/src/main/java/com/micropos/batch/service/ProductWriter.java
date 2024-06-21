package com.micropos.batch.service;

import com.micropos.batch.config.BatchConfig;
import com.micropos.batch.model.AmazonProduct;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.data.RepositoryItemWriter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ProductWriter extends RepositoryItemWriter<AmazonProduct> {
    @Override
    public void write(Chunk<? extends AmazonProduct> list) throws Exception {
        super.write(list);
        System.out.println("in ProductWriter write");
        for (AmazonProduct p : list.getItems()) {
            System.out.println(p);
        }
    }
}
