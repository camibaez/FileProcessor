/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import org.openide.windows.TopComponent;
import processor.core.graph.conditions.TextContent;
import processor.core.graph.conditions.FileContent;
import processor.core.graph.conditions.FilePattern;

import processor.core.file.Profile;

/**
 *
 * @author cbaez
 */
public class GraphBuilder {

    class NodeIdProvider implements ComponentNameProvider<ProcessingNode> {

        @Override
        public String getName(ProcessingNode t) {
            String res = t.toString();

            if (t instanceof FilePattern) {
                res = t.toString().replace("*", "_").replace(".", "_");
            }
            if (t instanceof FileContent) {
                res = FileContent.class.getSimpleName().replace("*", "_").replace(".", "_");
            }
            if (t instanceof TextContent) {
                res = "condition_" + t.hashCode();
            }

            return res;
        }
    }

    class NodeLabelProvider implements ComponentNameProvider<ProcessingNode> {
        @Override
        public String getName(ProcessingNode t) {
            String res = t.getClass().getSimpleName() + " : " + t.toString();
            return res;
        }
    }

    public DefaultDirectedGraph<ProcessingNode, DecisionEdge> buildEmpty() {
        DefaultDirectedGraph<ProcessingNode, DecisionEdge> graph = new DefaultDirectedGraph<>(DecisionEdge.class);

        ProcessingNode end = new EndNode();
        graph.addVertex(end);
        return graph;

    }
    
    public void buildNodes(List<ProcessingNode> nodes){
        for(ProcessingNode node: nodes){
            
        }
    }

    public String export(Graph graph) {
        ComponentNameProvider<ProcessingNode> idProvider = new NodeIdProvider();
        ComponentNameProvider<ProcessingNode> labelProvider = new NodeLabelProvider();

        ComponentNameProvider<DecisionEdge> edgeId = new ComponentNameProvider<DecisionEdge>() {
            @Override
            public String getName(DecisionEdge t) {
                return "e" + t.hashCode();
            }

        };

        ComponentNameProvider<DecisionEdge> edgeLabel = new ComponentNameProvider<DecisionEdge>() {
            @Override
            public String getName(DecisionEdge t) {
                return t.sign + "";
            }

        };

        GraphExporter<ProcessingNode, DecisionEdge> exporter = new DOTExporter<>(idProvider, labelProvider, edgeLabel);
        Writer writer = new StringWriter();
        try {
            exporter.exportGraph(graph, writer);
        } catch (ExportException ex) {
            ex.printStackTrace();
        }
        return writer.toString();
    }
}
