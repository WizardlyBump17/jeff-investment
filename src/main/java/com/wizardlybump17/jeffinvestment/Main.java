package com.wizardlybump17.jeffinvestment;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    private static final NumberFormat FORMAT = new DecimalFormat("###,###.##");
    private static final AtomicLong CURRENT_FILE = new AtomicLong();

    public static void main(String[] args) {
        double target = 10_000_000d;
        write(
                "Hello, Jeff Media, AKA mfnalex. I would like to ask you an investment of ${price} dollars for my company, so I can create a way to make the human blood storable for a long time!",
                target
        );
    }

    public static void write(String string, double target) {
        write(string, target, target / 20, 1);
    }

    private static void write(String string, double current, double divisor, int currentFile) {
        if (current <= 0)
            return;

        new Thread(() -> {
            try {
                File targetFile = new File(System.getProperty("user.dir"), "output-" + currentFile + ".txt");
                if (targetFile.exists())
                    targetFile.delete();
                targetFile.createNewFile();

                try (PrintWriter writer = new PrintWriter(targetFile)) {
                    for (double i = current; i > current - divisor && i > 0; i--)
                        writer.println(string.replace("{price}", FORMAT.format(i)));
                }

                CURRENT_FILE.incrementAndGet();

                write(string, current - divisor, divisor, currentFile + 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
