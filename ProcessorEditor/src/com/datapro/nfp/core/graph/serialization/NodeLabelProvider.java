/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph.serialization;

import org.jgrapht.io.ComponentNameProvider;
import com.datapro.nfp.core.graph.GraphNode;

/**
 *
 * @author cbaez
 */
public class NodeLabelProvider implements ComponentNameProvider<GraphNode>{
     @Override
        public String getName(GraphNode t) {
            return t.getId();
        }
}