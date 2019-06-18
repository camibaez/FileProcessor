/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph;

import java.util.Optional;
import java.util.Set;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 *
 * @author cbaez
 */
public class DecisionGraph extends DefaultDirectedGraph<GraphNode, DecisionEdge> {
    public static GraphNode START_NODE = new processor.core.graph.StartNode(), END_NODE = new processor.core.graph.EndNode(),
                            FAIL_NODE = new processor.core.graph.FailNode();

    public DecisionGraph() {
        super(DecisionEdge.class);
        addVertex(START_NODE);
        addVertex(END_NODE);
        addVertex(FAIL_NODE);
    }
    
    public void reload(){
        
    }
    
    public void masrkAsInitial(GraphNode node){
        this.removeVertex(START_NODE);
        this.addVertex(START_NODE);
        this.addEdge(START_NODE, node, new DecisionEdge(true));
        
    }
    
    public GraphNode getInitialNode() {
        Set<DecisionEdge> outgoingEdgesOf = this.outgoingEdgesOf(START_NODE);
        if(outgoingEdgesOf.size() < 1)
            return null;
        DecisionEdge edge = outgoingEdgesOf.stream().findFirst().get();
        return this.getEdgeTarget(edge);
    }
    
    public DecisionEdge getDecisionEdgeOf(GraphNode node, boolean sign){
        Optional<DecisionEdge> optional = outgoingEdgesOf(node).stream().filter(e -> e.getSign() == sign).findFirst();
        if(optional.isPresent())
            return optional.get();
        return null;
        
    }
    
    public GraphNode getDecisionTargetOf(GraphNode node, boolean sign){
        DecisionEdge edge = getDecisionEdgeOf(node, sign);
        if(edge != null){
            GraphNode targetNode = getEdgeTarget(edge);
            return targetNode;
        }
        return null;
        
    }
    
    
    public void changeEdgeOf(GraphNode node, boolean sign, GraphNode target){
         if(target == null)
            return;
        Optional edgeOption = outgoingEdgesOf(node).stream().filter(e -> ((DecisionEdge) e).getSign() == sign).findFirst();
        if (edgeOption.isPresent()) {
            DecisionEdge edge = (DecisionEdge) edgeOption.get();
            removeEdge(edge);  
        }
        addEdge(node, target, new DecisionEdge(sign));
    }
    
    
    public boolean isFailNode(GraphNode node){
        return node == FAIL_NODE;
    }
    
    public boolean isEndNode(GraphNode node){
        return node == END_NODE;
    }
    
    public boolean isStartNode(GraphNode node){
        return node == START_NODE;
    }
      
    
}
