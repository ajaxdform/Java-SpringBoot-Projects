package com.devinfo.quickstart;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devinfo.quickstart.services.ColorPrinter;

@SpringBootApplication
public class QuickstartApplication implements CommandLineRunner {

	private ColorPrinter colorPrinter;

	public QuickstartApplication(ColorPrinter colorPrinter) {
		this.colorPrinter = colorPrinter;
	}

	public static void main(String[] args) {
		SpringApplication.run(QuickstartApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("||||||||||||||||||||||||||||||||||||||||||||||   THIS IS YOUR OUTPUT   |||||||||||||||||||||||||||||||||||");
		System.err.print(colorPrinter.print());
	}

}
