/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph.serialization;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.DOTImporter;
import org.jgrapht.io.ImportException;
import com.datapro.nfp.core.graph.DecisionEdge;
import com.datapro.nfp.core.graph.DecisionGraph;
import com.datapro.nfp.core.graph.GraphNode;


/**
 *
 * @author cbaez
 */
public class GraphSerializer {

    
    
    public void importGraph(DecisionGraph graph, List<GraphNode> nodes, String dotData){
        NodeProvider nodeProvider = new NodeProvider(nodes);
        DecisionEdgeProvider decisionEdgeProvider = new DecisionEdgeProvider();
        
        DOTImporter<GraphNode, DecisionEdge> dotImporter  = new DOTImporter<>(nodeProvider, decisionEdgeProvider);
        try {
            dotImporter.importGraph(graph, new StringReader(dotData));
        } catch (ImportException ex) {
            ex.printStackTrace();
        }
        
        
    }
     
    public String exportGraph(DecisionGraph graph){
        NodeIdProvider idProvider = new NodeIdProvider();
        NodeLabelProvider nodeLabelProvider = new NodeLabelProvider();
        EdgeIdProvider edgeIdProvider = new EdgeIdProvider();
        
        DOTExporter<GraphNode, DecisionEdge> dotExporter = new DOTExporter<>(idProvider, nodeLabelProvider, edgeIdProvider);
        Writer writer = new StringWriter();
        dotExporter.exportGraph(graph, writer);
        return writer.toString();
    }
       
}
