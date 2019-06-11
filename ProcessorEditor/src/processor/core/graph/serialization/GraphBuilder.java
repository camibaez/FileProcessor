/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.serialization;

import java.io.Reader;
import java.io.StringReader;
import processor.core.lineal.FailNode;
import processor.core.lineal.EndNode;
import processor.core.lineal.StartNode;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.DOTImporter;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import org.jgrapht.io.ImportException;
import org.jgrapht.io.VertexProvider;
import org.openide.util.Exceptions;
import processor.core.graph.DecisionEdge;
import processor.core.graph.DecisionGraph;
import processor.core.graph.GraphNode;
import processor.core.graph.actions.ReplaceText;
import processor.core.graph.conditions.FilePattern;
import processor.core.graph.serialization.EdgeIdProvider;
import processor.core.graph.serialization.NodeIdProvider;
import processor.core.graph.serialization.NodeLabelProvider;
import processor.core.lineal.ComplexNode;

/**
 *
 * @author cbaez
 */
public class GraphBuilder {

    public static ComplexNode START_NODE = new StartNode(), END_NODE = new EndNode(), FAIL_NODE = new FailNode();
    
    public DecisionGraph buildDummyGraph(){
        DecisionGraph graph = new DecisionGraph();
        FilePattern filePattern = new FilePattern("*.jsp");
        filePattern.setId("c1");
        graph.addVertex(filePattern);
        final ReplaceText replaceText = new ReplaceText("a", "b");
        replaceText.setId("a1");
        graph.addVertex(replaceText);
        
        graph.addEdge(DecisionGraph.S_NODE, filePattern, new DecisionEdge());
        graph.addEdge(filePattern, DecisionGraph.F_NODE, new DecisionEdge(false));
        graph.addEdge(filePattern, replaceText, new DecisionEdge());
        graph.addEdge(replaceText, DecisionGraph.E_NODE);
        
        return graph;
    }
    
    
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
        try {
            dotExporter.exportGraph(graph, System.out);
        } catch (ExportException ex) {
            ex.printStackTrace();
        }
        return writer.toString();
    }
    
    
    public DefaultDirectedGraph<ComplexNode, DecisionEdge> buildEmpty() {
        DefaultDirectedGraph<ComplexNode, DecisionEdge> graph = new DefaultDirectedGraph<>(DecisionEdge.class);
        graph.addVertex(START_NODE);
        graph.addVertex(END_NODE);
        graph.addVertex(FAIL_NODE);
        return graph;

    }
    
    
    public DefaultDirectedGraph<ComplexNode, DecisionEdge> buildNodes(List<ComplexNode> nodes) {
        DefaultDirectedGraph<ComplexNode, DecisionEdge> g = buildEmpty();
        ComplexNode first = null;
        ComplexNode last = null;
        for (ComplexNode node : nodes) {
            g.addVertex(node);
            if (first == null) {
                first = node;
                
                g.addEdge(START_NODE, node, new DecisionEdge(true));
            }
            if(node.getType() == ComplexNode.UNOMISSIBLE)
                g.addEdge(node, FAIL_NODE, new DecisionEdge(false));
            if (last != null) {
                g.addEdge(last, node, new DecisionEdge(true));
                if(last.getType() == ComplexNode.OMISSBLE)
                    g.addEdge(last, node,  new DecisionEdge(false));
            }

            last = node;
        }
        if (last != null) {
            g.addEdge(last, END_NODE, new DecisionEdge(true));
           
        }

        return g;
    }

    
    
    
    
    
    
    
    public String export(Graph graph) {
        ComponentNameProvider<ComplexNode> idProvider = new ComponentNameProvider<ComplexNode>() {
            @Override
            public String getName(ComplexNode t) {
                return "node_" + t.hashCode();
            }
        };

        ComponentNameProvider<ComplexNode> labelProvider = new ComponentNameProvider<ComplexNode>() {
            @Override
            public String getName(ComplexNode t) {
                String label = "";
                if (t instanceof StartNode) {
                    label = ((StartNode) t).toString();
                } else if (t instanceof EndNode) {
                    label = ((EndNode) t).toString();
                }else if (t instanceof FailNode){
                    label = ((FailNode) t).toString();
                } 
                else {
                    label = "" + t.getCondition() + " / " + t.getAction();
                }
                return label;
            }
        };

        ComponentNameProvider<DecisionEdge> edgeLabel = new ComponentNameProvider<DecisionEdge>() {
            @Override
            public String getName(DecisionEdge t) {
                return t.getSign() + "";
            }
        };
        GraphExporter<ComplexNode, DecisionEdge> exporter = new DOTExporter<>(idProvider, labelProvider, edgeLabel);
        Writer writer = new StringWriter();
        try {
            exporter.exportGraph(graph, writer);
        } catch (ExportException ex) {
            ex.printStackTrace();
        }
        return writer.toString();
    }
   
}
