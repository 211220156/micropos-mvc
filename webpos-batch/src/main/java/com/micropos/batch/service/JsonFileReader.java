package com.micropos.batch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonFileReader implements ItemReader<JsonNode> {
    private BufferedReader reader;

    private ObjectMapper objectMapper;

    private String fileName;

    public JsonFileReader(String file) {
        System.out.println("in JsonFileReader");
        if (file.matches("^file:(.*)"))
            file = file.substring(file.indexOf(":") + 1);
        this.fileName = file;
        this.objectMapper = new ObjectMapper();
        try {
            initReader();
        } catch (FileNotFoundException e) {
            System.out.println(file + " not found! Please check the file path before starting the job.");
        }
    }

    private void initReader() throws FileNotFoundException {
        System.out.println("initReader");
        File file = new File(fileName);
        reader = new BufferedReader(new FileReader(file));
    }

    @Override
    public JsonNode read() throws Exception {
        System.out.println("reading...");
        if (reader == null) {
            throw new Exception("file not found!");
        }
        String line = reader.readLine();
        System.out.println("line:" + line);

        if (line != null)
            return objectMapper.readTree(line);
        else
            return null;
    }
}
