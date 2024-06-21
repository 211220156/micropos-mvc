package com.micropos.batch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.batch.jpa.UserRepository;
import com.micropos.batch.model.Review;
import com.micropos.batch.model.User;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewProcessor implements ItemProcessor<JsonNode, Review>, StepExecutionListener {
    @Autowired
    private UserRepository userRepository;
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
    public Review process(JsonNode jsonNode) throws Exception {
        System.out.println("review processing...");
        Review review = objectMapper.treeToValue(jsonNode, Review.class);
        userRepository.InsertIgnore(review.getUser_id());
//        userRepository.saveAndFlush(new User(review.getUser_id()));//实现了persistable接口必须用saveAndFlush，否则会与之前生成的相同User重复，导致报错
        //一开始遍历寻找，效率低
        return review;
    }
}
