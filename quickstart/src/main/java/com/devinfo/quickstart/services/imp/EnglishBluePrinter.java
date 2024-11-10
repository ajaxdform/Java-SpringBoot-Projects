package com.devinfo.quickstart.services.imp;

import org.springframework.stereotype.Component;

import com.devinfo.quickstart.services.BluePrinter;

@Component
public class EnglishBluePrinter implements BluePrinter {

    @Override
    public String print() {
        return "blue";
    }

}
