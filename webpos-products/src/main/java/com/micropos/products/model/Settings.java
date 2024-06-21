package com.micropos.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Settings {
    private int _id;
    private Setting settings;
    @Getter
    @AllArgsConstructor
    public static class Setting {
        private String app;
        private String store;
        private String address_one;
        private String address_two;
        private String contact;
        private String tax;
        private String symbol;
        private String percentage;
        private String footer;
        private String img;
    }
    private String id;
}
