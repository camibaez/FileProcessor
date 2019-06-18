/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.serialization;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.DOTImporter;
import org.jgrapht.io.ImportException;
import processor.core.graph.DecisionEdge;
import processor.core.graph.DecisionGraph;
import processor.core.graph.EndNode;
import processor.core.graph.FailNode;
import processor.core.graph.GraphNode;
import processor.core.graph.StartNode;
import processor.core.graph.actions.ReplaceText;
import processor.core.graph.conditions.FilePattern;


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
