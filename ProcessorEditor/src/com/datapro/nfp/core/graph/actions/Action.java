/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph.actions;

import com.datapro.nfp.core.graph.GraphNode;

/**
 *
 * @author cbaez
 */
public abstract class Action extends GraphNode{
    
    public abstract Object process(Object o);
    @Override
    public String generateRandomId(){
        return "a" + this.hashCode();
    }
}
