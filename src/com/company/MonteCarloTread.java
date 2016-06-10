
import java.math.BigDecimal;

/**
 * @author Presko
 */
public class MonteCarloTread extends Thread {

    private static final BigDecimal CENTER = new BigDecimal(.5);
    private static final BigDecimal CENTER_POW2 = CENTER.pow(2);

    private double cycles;
    private double pointsInsideCircle;
    private final int threadSerialNumber;
    private boolean isQuiet = false;
    private final RandomImpl generator;
    
    public MonteCarloTread(int cycles, int threadSerialNumber, boolean isQuiet,RandomImpl generator) {
        super();
        this.cycles = cycles;
        this.pointsInsideCircle = 0;
        this.threadSerialNumber = threadSerialNumber;
        this.isQuiet = isQuiet;
        this.generator = generator;
    }

    @Override
    public void run() {
        if(!isQuiet){
            System.out.println("Thread-<" + threadSerialNumber + "> started.");
        }
        long start = System.currentTimeMillis();
        BigDecimal xCoordinate, yCoordinate;
        for (int j = 1; j <= this.cycles; j++) {
            xCoordinate = new BigDecimal(generator.generate());
            yCoordinate = new BigDecimal(generator.generate());
            if (isInsideCircle(xCoordinate, yCoordinate)) {
                pointsInsideCircle++;
            }
        }
        if (!this.isQuiet) {
            System.out.println("Thread-<" + threadSerialNumber + "> stopped.");
            System.out.println("Thread-<" + threadSerialNumber + "> execution time was (millis): <"
                    + (System.currentTimeMillis() - start) + ">");
        }
    }

    private static boolean isInsideCircle(BigDecimal x, BigDecimal y) {
        BigDecimal xMinusCenter = x.subtract(CENTER);
        BigDecimal yMinusCenter = y.subtract(CENTER);
        BigDecimal xMinusCenterPow2 = xMinusCenter.pow(2);
        BigDecimal yMinusCenterPow2 = yMinusCenter.pow(2);
        BigDecimal xPlusYMinusCenterPow2 = xMinusCenterPow2.add(yMinusCenterPow2);
        return xPlusYMinusCenterPow2.compareTo(CENTER_POW2) == -1;
    }

    public double getPointsInsideCircle() {
        return pointsInsideCircle;
    }

    public void addRemainerToCycles(int remainer){
        this.cycles+=remainer;
    }
    
}
