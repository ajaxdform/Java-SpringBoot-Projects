package com.devinfo.quickstart.services.imp;

import org.springframework.stereotype.Component;

import com.devinfo.quickstart.services.BluePrinter;
import com.devinfo.quickstart.services.ColorPrinter;
import com.devinfo.quickstart.services.GreenPrinter;
import com.devinfo.quickstart.services.RedPrinter;

@Component
public class ColorPrinterImpl implements ColorPrinter {
    private BluePrinter bluePrinter;
    private RedPrinter redPrinter;
    private GreenPrinter greenPrinter;

    public ColorPrinterImpl(RedPrinter redPrinter, BluePrinter bluePrinter, GreenPrinter greenPrinter) {
        this.redPrinter = redPrinter;
        this.bluePrinter = bluePrinter;
        this.greenPrinter = greenPrinter;
    }

    @Override
    public String print() {
        return String.join(", ", redPrinter.print(), bluePrinter.print(), greenPrinter.print());
    }

}
