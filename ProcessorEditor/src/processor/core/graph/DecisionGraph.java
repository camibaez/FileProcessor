/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph;

import java.util.Set;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 *
 * @author cbaez
 */
public class DecisionGraph extends DefaultDirectedGraph<GraphNode, DecisionEdge> {
    public static GraphNode S_NODE = new processor.core.graph.StartNode(), E_NODE = new processor.core.graph.EndNode(),
                            F_NODE = new processor.core.graph.FailNode();

    public DecisionGraph() {
        super(DecisionEdge.class);
        addVertex(S_NODE);
        addVertex(E_NODE);
        addVertex(F_NODE);
    }
    
    public boolean isFailNode(GraphNode node){
        return node == F_NODE;
    }
    
    public boolean isEndNode(GraphNode node){
        return node == E_NODE;
    }
    
    public boolean isStartNode(GraphNode node){
        return node == S_NODE;
    }
      
    public GraphNode getInitialNode() {
        Set<DecisionEdge> outgoingEdgesOf = this.outgoingEdgesOf(S_NODE);
        if(outgoingEdgesOf.size() < 1)
            return null;
        DecisionEdge edge = outgoingEdgesOf.stream().findFirst().get();
        return this.getEdgeTarget(edge);
    }
}
