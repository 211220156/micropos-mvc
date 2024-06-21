package com.micropos.batch.service;

import com.micropos.batch.model.Review;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.data.RepositoryItemWriter;

public class ReviewWriter extends RepositoryItemWriter<Review> {
    @Override
    public void write(Chunk<? extends Review> list) throws Exception {
        super.write(list);
        System.out.println("in ReviewWriter write");
//        for (Review r : list.getItems()) {
//            reviewRepository.persistAndFlush(r);
//            System.out.println(r);
//        }
    }
}