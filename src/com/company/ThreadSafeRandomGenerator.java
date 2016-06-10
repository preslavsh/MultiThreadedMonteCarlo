/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Presko
 */
public class ThreadSafeRandomGenerator implements RandomImpl{

    @Override
    public double generate() {
        return ThreadLocalRandom.current().nextDouble();
    }
    
}
