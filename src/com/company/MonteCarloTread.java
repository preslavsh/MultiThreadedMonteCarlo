package com.company;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Presko on 08-Jun-16.
 */
public class MonteCarloTread extends Thread {


    private static final BigDecimal CENTER = new BigDecimal(.5);
    private static final BigDecimal CENTER_POW2 = CENTER.pow(2);

    private double cycles;
    private double insideCircle;
    private int number;

    public MonteCarloTread(int cycles, int number) {
        super();
        this.cycles = cycles;
        this.insideCircle = 0;
        this.number = number;
    }

    public void run() {
        System.out.println("Thread-<" + number + "> started.");
        long start = System.currentTimeMillis();
        BigDecimal xCoordinate, yCoordinate;
        for (int j = 1; j <= this.cycles; j++) {
            xCoordinate = new BigDecimal(ThreadLocalRandom.current().nextDouble());
            yCoordinate = new BigDecimal(ThreadLocalRandom.current().nextDouble());
            if (isInsideCircle(xCoordinate, yCoordinate)) insideCircle++;
        }
        System.out.println("Thread-<" + number + "> stopped.");
        System.out.println("Thread-<" + number + "> execution time was (millis): <" + (System.currentTimeMillis() - start) + ">“");
    }

    private static boolean isInsideCircle(BigDecimal x, BigDecimal y) {
        BigDecimal xMinusCenter = x.subtract(CENTER);
        BigDecimal yMinusCenter = y.subtract(CENTER);
        BigDecimal xMinusCenterPow2 = xMinusCenter.pow(2);
        BigDecimal yMinusCenterPow2 = yMinusCenter.pow(2);
        BigDecimal xPlusYMinusCenterPow2 = xMinusCenterPow2.add(yMinusCenterPow2);
        return xPlusYMinusCenterPow2.compareTo(CENTER_POW2) == -1;
    }

    public double getInsideCircle() {
        return insideCircle;
    }

}
