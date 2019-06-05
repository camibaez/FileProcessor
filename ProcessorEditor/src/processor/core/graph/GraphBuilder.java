/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import processor.core.graph.providers.EdgeIdProvider;
import processor.core.graph.providers.NodeIdProvider;
import processor.core.graph.providers.NodeLabelProvider;
import processor.core.lineal.ComplexNode;

/**
 *
 * @author cbaez
 */
public class GraphBuilder {

    public static ComplexNode START_NODE = new StartNode(), END_NODE = new EndNode();

    public DefaultDirectedGraph<ComplexNode, DecisionEdge> buildEmpty() {
        DefaultDirectedGraph<ComplexNode, DecisionEdge> graph = new DefaultDirectedGraph<>(DecisionEdge.class);
        graph.addVertex(START_NODE);
        graph.addVertex(END_NODE);
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
                g.addEdge(node, END_NODE, new DecisionEdge(false));
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
                } else {
                    label = "" + t.getCondition() + " / " + t.getAction();
                }
                return label;
            }
        };

        ComponentNameProvider<DecisionEdge> edgeLabel = new ComponentNameProvider<DecisionEdge>() {
            @Override
            public String getName(DecisionEdge t) {
                return t.sign + "";
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
