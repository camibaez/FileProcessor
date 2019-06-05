/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.providers;

import org.jgrapht.io.ComponentNameProvider;
import processor.core.graph.DecisionEdge;

/**
 *
 * @author cbaez
 */
public class EdgeIdProvider implements ComponentNameProvider<DecisionEdge> {

    @Override
    public String getName(DecisionEdge t) {
        return "e" + t.hashCode();
    }
}
