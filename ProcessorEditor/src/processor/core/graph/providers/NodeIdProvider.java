/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.providers;

import org.jgrapht.io.ComponentNameProvider;
import processor.core.graph.GraphNode;
import processor.core.graph.conditions.FileContent;
import processor.core.graph.conditions.FilePattern;
import processor.core.graph.conditions.TextContent;

/**
 *
 * @author cbaez
 */
public class NodeIdProvider implements ComponentNameProvider<GraphNode>{
    @Override
        public String getName(GraphNode t) {
            return t.getId();
        }
}
