/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.profile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.json.simple.JSONArray;
import processor.core.graph.DecisionEdge;
import processor.core.graph.DecisionGraph;
import processor.core.graph.GraphBuilder;
import processor.core.graph.GraphNode;
import processor.core.graph.actions.Action;
import processor.core.graph.conditions.Condition;
import processor.core.lineal.ComplexNode;
import processor.core.lineal.StartNode;

/**
 *
 * @author cbaez
 */
public class GraphSerialization {

    public static DefaultDirectedGraph<GraphNode, DecisionEdge> translateGraph(DefaultDirectedGraph<ComplexNode, DecisionEdge> graph) {
       /*
        DecisionGraph finalGraph = new DecisionGraph();

        ComplexNode start = graph.vertexSet().stream().filter(n -> n instanceof StartNode).findFirst().get();
        Optional<DecisionEdge> findFirst = graph.outgoingEdgesOf(start).stream().filter(e -> e.getSign()).findFirst();
        if (!findFirst.isPresent()) {
            return finalGraph;
        }
        DecisionEdge trueEdge = findFirst.get();
        ComplexNode node = graph.getEdgeTarget(trueEdge);

        GraphNode firstNode = node.getCondition() != null ? node.getCondition() : node.getAction();
        finalGraph.addEdge(DecisionGraph.S_NODE, firstNode, new DecisionEdge(true));
        
        if(graph.outgoingEdgesOf(node).size() > 1){
            DecisionEdge tEdge = null;
            DecisionEdge fEdge = null;
            
            for(DecisionEdge e : graph.outgoingEdgesOf(node)){
                if(e.getSign())
                    tEdge = e;
                else
                    fEdge = e;
            }
            
            Condition condition = node.getCondition();
            if(condition != null){
                
            }
        }

        */
        return null;
    }
    
    
    
    
    
    
    

    public static Map writeGraph(DefaultDirectedGraph<ComplexNode, DecisionEdge> graph) {
        Set<ComplexNode> vertexSet = graph.vertexSet();
        Map map = new LinkedHashMap();
        for (ComplexNode node : vertexSet) {
            Map edgesMap = new LinkedHashMap();
            Set<DecisionEdge> edges = graph.outgoingEdgesOf(node);
            edges.forEach(e -> {
                if (!e.getSign()) {

                }
            });

            //map.put(node.getId(), edgesMap);
        }

        return map;
    }
}