/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph.serialization;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jgrapht.io.Attribute;
import org.jgrapht.io.VertexProvider;
import com.datapro.nfp.core.graph.GraphNode;

/**
 *
 * @author cbaez
 */
public class NodeProvider implements VertexProvider<GraphNode>{
    protected List<GraphNode> nodes;
    
    public NodeProvider(List<GraphNode> nodes){
        this.nodes = nodes;
    }
    
    
    @Override
    public GraphNode buildVertex(String id, Map<String, Attribute> map) {
        Optional<GraphNode> firstOption = nodes.stream().filter(n -> n.getId().equals(id)).findFirst();
        if(firstOption.isPresent()){
            return firstOption.get();
        }
        
        return null;
    }
    
    
}
