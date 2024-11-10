package com.devinfo.quickstart.services.imp;

import org.springframework.stereotype.Component;

import com.devinfo.quickstart.services.RedPrinter;

@Component
public class EnglishRedPrinter implements RedPrinter {

    @Override
    public String print() {
        return "red";
    }
}
