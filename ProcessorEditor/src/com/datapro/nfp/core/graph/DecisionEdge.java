/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph;

/**
 *
 * @author cbaez
 */
public class DecisionEdge {
    protected boolean sign = true;

    public DecisionEdge() {
    }

    public DecisionEdge(boolean sign) {
        this.sign = sign;
    }
    
    public boolean getSign() {
        return sign;
    }
    
    public String toString(){
        return sign + "";
    }
    
    
}
