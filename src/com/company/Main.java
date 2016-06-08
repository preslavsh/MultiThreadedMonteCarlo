package com.company;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final BigDecimal FOUR = new BigDecimal(4.0);

    public static void main(String[] args) {
        System.out.println(args);
        Main first = new Main();
        first.run();
    }

    public void run() {
        long start = System.currentTimeMillis();
        int nPoints = 1000000;
        int threadsCount = 4;
        List<MonteCarloTread> threadList = new ArrayList<>();
        for (int i = 0; i < threadsCount; i++) {
            threadList.add(new MonteCarloTread(nPoints / threadsCount, i + 1));
        }
        threadList.forEach(t -> t.start());
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        double circleCount = threadList.stream().map(t -> t.getInsideCircle()).reduce(0.0, (a, b) -> a + b);
        BigDecimal Pi = FOUR.multiply(new BigDecimal(circleCount / nPoints));
        System.out.println("Total execution time for current run (millis): <" + (System.currentTimeMillis() - start) + ">");
        System.out.println("Threads used in current run: <" + threadsCount + ">");
    }

}
