/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core;

import java.io.StringWriter;
import java.io.Writer;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import processor.core.conditions.GenericContent;
import processor.core.conditions.FileContent;
import processor.core.conditions.FileType;
import processor.core.rules.RuleCluster;
import processor.core.file.Profile;

/**
 *
 * @author cbaez
 */
public class GraphBuilder {

    public DefaultDirectedGraph<ProcessingNode, DecisionEdge> build(Profile p) {
        DefaultDirectedGraph<ProcessingNode, DecisionEdge> graph = new DefaultDirectedGraph<>(DecisionEdge.class);

        ProcessingNode end = new EndNode();
        graph.addVertex(end);

        FileType fileTypeNode = new FileType(p.getBasePrototype().getExtensions());
        graph.addVertex(fileTypeNode);
        graph.addEdge(fileTypeNode, end, new DecisionEdge(false));

        FileContent fileContentNode = new FileContent(p.getBasePrototype().getExpressions());
        graph.addVertex(fileContentNode);
        graph.addEdge(fileContentNode, end, new DecisionEdge(false));

        graph.addEdge(fileTypeNode, fileContentNode, new DecisionEdge(true));

        RuleCluster eventChanger = null;
        RuleCluster appendOptMenu = null;
        RuleCluster addContextMenu = null;

        for (RuleCluster c : p.getCleaners()) {
            if (c.getId().contains("eventChanger")) {
                eventChanger = c;
            }
            if (c.getId().contains("appendOptMenu")) {
                appendOptMenu = c;
            }
            if (c.getId().contains("addContextMenuCss")) {
                addContextMenu = c;
            }
        }

        graph.addVertex(eventChanger);
        graph.addEdge(fileContentNode, eventChanger, new DecisionEdge(true));

        GenericContent coditionOptMenu = new GenericContent(appendOptMenu.getPrototype().getExpressions().get(0));
        graph.addVertex(coditionOptMenu);
        graph.addEdge(eventChanger, coditionOptMenu, new DecisionEdge(true));

        graph.addVertex(appendOptMenu);
        graph.addEdge(coditionOptMenu, appendOptMenu, new DecisionEdge(true));

        GenericContent conditionCss = new GenericContent(addContextMenu.getPrototype().getExpressions().get(0));
        graph.addVertex(conditionCss);
        graph.addEdge(coditionOptMenu, conditionCss, new DecisionEdge(false));
        graph.addEdge(appendOptMenu, conditionCss, new DecisionEdge(true));

        graph.addEdge(conditionCss, end, new DecisionEdge(false));

        graph.addVertex(addContextMenu);
        graph.addEdge(conditionCss, addContextMenu, new DecisionEdge(true));

        return graph;
    }
    
    public String export(Graph graph){
        ComponentNameProvider<ProcessingNode> idProvider = new ComponentNameProvider<ProcessingNode>() {
            @Override
            public String getName(ProcessingNode t) {
                String res = t.toString();
                if (t instanceof RuleCluster) {
                    res = ((RuleCluster) t).getId();
                }
                if (t instanceof FileType) {
                    res = t.toString().replace("*", "_").replace(".", "_");
                }
                if (t instanceof FileContent) {
                    res = FileContent.class.getSimpleName().replace("*", "_").replace(".", "_");
                }
                if (t instanceof GenericContent) {
                    res = "condition_" + t.hashCode();
                }

                return res;
            }

        };
        ComponentNameProvider<ProcessingNode> labelProvider = new ComponentNameProvider<ProcessingNode>() {
            @Override
            public String getName(ProcessingNode t) {
                String res = t.getClass().getSimpleName() + " : " + t.toString();
                return res;
            }

        };
        
        ComponentNameProvider<DecisionEdge> edgeId = new ComponentNameProvider<DecisionEdge>(){
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
