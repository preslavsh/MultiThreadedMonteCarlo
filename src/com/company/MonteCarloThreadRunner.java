/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Presko
 */
public class MonteCarloThreadRunner {

    private static final BigDecimal FOUR = new BigDecimal(4.0);
    private final Map<String, Integer> params;
    
    public MonteCarloThreadRunner(String[] args) {
        this.params = parseArguments(args);
    }
    
    public MonteCarloThreadRunner(Map<String, Integer> params) {
        this.params = params;
    }

    private static Map<String, Integer> parseArguments(String[] args) {
        Map<String, Integer> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-t":
                    params.put("threads", new Integer(args[i + 1]));
                    break;
                case "-s":
                    params.put("points", new Integer(args[i + 1]));
                    break;
                case "-q":
                    params.put("quiet", 1);
                    break;
                case "-mr":
                    params.put("math.random",1);
            }
        }
        if (!params.containsKey("quiet")) {
            params.put("quiet", 0);
        }
        if(!params.containsKey("math.random")){
            params.put("math.random",0);
        }
        return params;
    }

    public long run() {
        int threadsCount = params.get("threads");
        int nPoints = params.get("points");
        boolean isQuiet = params.get("quiet") == 1;
        boolean wantsMathRandomGenerator = params.get("math.random")==1;
        RandomImpl generator = wantsMathRandomGenerator?new MathRandomGenerator():new ThreadSafeRandomGenerator();
        long start = System.currentTimeMillis();
        List<MonteCarloTread> threadList = new ArrayList<>();
        for (int i = 0; i < threadsCount; i++) {
            threadList.add(new MonteCarloTread(nPoints / threadsCount, i + 1, isQuiet, generator));
        }
        threadList.get(0).addRemainerToCycles(nPoints%threadsCount);
        threadList.forEach(t -> t.start());
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        });
        double circleCount = threadList.stream().map(t -> t.getPointsInsideCircle()).reduce(0.0, (a, b) -> a + b);
        BigDecimal Pi = FOUR.multiply(new BigDecimal(circleCount / nPoints));
        System.out.println("The execution resulted in aproximation of Pi equal to: <" + Pi + ">");
        System.out.println("Total execution time for current run (millis): <" + (System.currentTimeMillis() - start) + ">");
        if (!isQuiet) {
            System.out.println("Threads used in current run: <" + threadsCount + ">");
        }
        return (System.currentTimeMillis() - start);
    }
}
