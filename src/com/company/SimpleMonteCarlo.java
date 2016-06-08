package com.company;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Presko on 08-Jun-16.
 */
public class SimpleMonteCarlo {

    private static final BigDecimal CENTER = new BigDecimal(.5);
    private static final BigDecimal CENTER_POW2 = CENTER.pow(2);
    private static final BigDecimal FOUR = new BigDecimal(4.0);

    public BigDecimal execute(){
        double nPoints = 2000000;
        double circleCount = 0;
        BigDecimal Pi,xCoordinate,yCoordinate;

        for (int j = 1; j <= nPoints; j++) {
            xCoordinate = new BigDecimal(ThreadLocalRandom.current().nextDouble());
            yCoordinate = new BigDecimal(ThreadLocalRandom.current().nextDouble());
            if (isInsideCircle(xCoordinate,yCoordinate))circleCount++;
        }
        Pi = FOUR.multiply(new BigDecimal(circleCount/nPoints));
        return Pi;
    }


    private static boolean isInsideCircle(BigDecimal x, BigDecimal y){
        BigDecimal xMinusCenter = x.subtract(CENTER);
        BigDecimal yMinusCenter = y.subtract(CENTER);
        BigDecimal xMinusCenterPow2 = xMinusCenter.pow(2);
        BigDecimal yMinusCenterPow2 = yMinusCenter.pow(2);
        BigDecimal xPlusYMinusCenterPow2  = xMinusCenterPow2.add(yMinusCenterPow2);
        return xPlusYMinusCenterPow2.compareTo(CENTER_POW2)==-1;
    }
}
