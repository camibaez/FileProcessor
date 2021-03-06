/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 *
 * @author cbaez
 */
public class DecisionGraph extends DefaultDirectedGraph<GraphNode, DecisionEdge> {
    public static GraphNode START_NODE = new com.datapro.nfp.core.graph.StartNode(), 
                            END_NODE = new com.datapro.nfp.core.graph.EndNode(),
                            FAIL_NODE = new com.datapro.nfp.core.graph.FailNode();

    public DecisionGraph() {
        super(DecisionEdge.class);
        addVertex(START_NODE);
        addVertex(END_NODE);
        addVertex(FAIL_NODE);
    }
    
    public void reload(){
        
    }
    
    public List<GraphNode> getNodesList(){
        return vertexSet().stream().filter(v -> {
            return !(isFailNode(v) || isStartNode(v) || isEndNode(v));
        }).collect(Collectors.toList());
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
      
    
    
    public void includeGraph(DecisionGraph graph){
        graph.vertexSet().forEach(v -> {
            if(v instanceof StartNode || v instanceof EndNode || v instanceof FailNode)
                return;
            if(addVertex(v))
                 System.out.println("Vertex added to graph.");
        });
        
        graph.edgeSet().forEach(e -> {
            if(!(graph.getEdgeSource(e) instanceof StartNode)){
                boolean resp = this.addEdge(graph.getEdgeSource(e), graph.getEdgeTarget(e), e);
                if(resp)
                    System.out.println("Edge added to graph.");
            }
        });
    }
    
    public int compile(){
        int returnCode = 0;
        for(GraphNode n : getNodesList()){
            if(n instanceof ExecutableNode){
                returnCode = ((ExecutableNode) n).compile();
                if(returnCode != 0)
                    break;    
            }
        }
        
        return returnCode;
    }
    
}
