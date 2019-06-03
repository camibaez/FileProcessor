/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core;

import org.jgrapht.graph.DefaultDirectedGraph;
import processor.core.rules.ReplaceText;

/**
 *
 * @author cbaez
 */
public class GraphBuilder {
    public void build(){
        DefaultDirectedGraph<ProcessingNode, MyEdge> graph = new DefaultDirectedGraph<>(MyEdge.class);
        
        
        graph.addVertex(new ReplaceText("a", "b"));
        
    }
}
