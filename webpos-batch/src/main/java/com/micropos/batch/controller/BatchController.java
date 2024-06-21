package com.micropos.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class BatchController {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job ProductJob;

    @Autowired
    Job ReviewJob;

    @RequestMapping("/job/product")
    public void handleProductJob() throws Exception{
        System.out.println("in handle product");
        jobLauncher.run(ProductJob, new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters());
    }
    @RequestMapping("/job/review")
    public void handleReviewJob() throws Exception{
        System.out.println("in handle review");
        jobLauncher.run(ReviewJob, new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters());
    }
}
