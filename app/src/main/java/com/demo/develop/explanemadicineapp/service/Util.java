package com.demo.develop.explanemadicineapp.service;


public class Util {

    public static String nullToEmpty(String text) {
        if (text == null) {
            return "";
        } else {
            return text.trim();
        }
    }
}
