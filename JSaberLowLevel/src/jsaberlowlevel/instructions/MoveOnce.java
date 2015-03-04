/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsaberlowlevel.instructions;
import jsaberlowlevel.LaserCutter;

/**
 *
 * @author rgi20802
 */
public class MoveOnce implements Instruction {

    @Override
    public void execute() {
        //pin.setValue(true);
        LaserCutter.hold(LaserCutter.PULSE_TIME);
        //pin.setValue(false);
        LaserCutter.hold(LaserCutter.getOffTime(50));
    }
}
