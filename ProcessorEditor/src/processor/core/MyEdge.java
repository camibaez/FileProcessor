/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core;

/**
 *
 * @author cbaez
 */
public class MyEdge {
    protected boolean sign;

    public MyEdge() {
    }

    public MyEdge(boolean sign) {
        this.sign = sign;
    }
    
    public boolean isSign() {
        return sign;
    }
    
    
}
