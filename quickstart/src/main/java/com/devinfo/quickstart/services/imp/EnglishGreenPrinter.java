package com.devinfo.quickstart.services.imp;

import org.springframework.stereotype.Component;

import com.devinfo.quickstart.services.GreenPrinter;

@Component
public class EnglishGreenPrinter implements GreenPrinter {

    @Override
    public String print() {
        return "green";
    }

}
