/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph.serialization;

import org.jgrapht.io.ComponentNameProvider;
import com.datapro.nfp.core.graph.DecisionEdge;

/**
 *
 * @author cbaez
 */
public class EdgeIdProvider implements ComponentNameProvider<DecisionEdge> {

    @Override
    public String getName(DecisionEdge t) {
        return "e"+ (t.getSign()? "t" : "f")  + t.hashCode();
    }
}
