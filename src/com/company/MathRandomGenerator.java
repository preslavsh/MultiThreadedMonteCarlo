/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Presko
 */
public class MathRandomGenerator implements RandomImpl{

    @Override
    public double generate() {
        return Math.random();
    }
    
}
