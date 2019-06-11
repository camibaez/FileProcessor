/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.serialization;

import java.util.Map;
import org.jgrapht.io.Attribute;
import org.jgrapht.io.EdgeProvider;
import processor.core.graph.DecisionEdge;
import processor.core.graph.GraphNode;

/**
 *
 * @author cbaez
 */
public class DecisionEdgeProvider implements EdgeProvider<GraphNode, DecisionEdge>{

    @Override
    public DecisionEdge buildEdge(GraphNode v, GraphNode v1, String string, Map<String, Attribute> map) {
        boolean type = string.charAt(1) == 't';
        return new DecisionEdge(type);
    }


}
