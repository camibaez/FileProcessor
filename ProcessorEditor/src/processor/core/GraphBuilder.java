/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core;

import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import org.openide.util.Exceptions;
import processor.core.conditions.Condition;
import processor.core.conditions.Content;
import processor.core.conditions.FileContent;
import processor.core.conditions.FileType;
import processor.core.file.Cleaner;
import processor.core.file.ConditionalPattern;
import processor.core.file.FilePrototype;
import processor.core.file.Profile;
import processor.core.rules.ReplaceText;
import processor.core.rules.Rule;
import processor.core.rules.TextRule;

/**
 *
 * @author cbaez
 */
public class GraphBuilder {

    public void build(Profile p) {
        DefaultDirectedGraph<ProcessingNode, MyEdge> graph = new DefaultDirectedGraph<>(MyEdge.class);

        ProcessingNode end = new EndNode();
        graph.addVertex(end);

        FileType fileTypeNode = new FileType(p.getBasePrototype().getExtensions());
        graph.addVertex(fileTypeNode);
        graph.addEdge(fileTypeNode, end, new MyEdge(false));

        FileContent fileContentNode = new FileContent(p.getBasePrototype().getExpressions());
        graph.addVertex(fileContentNode);
        graph.addEdge(fileContentNode, end, new MyEdge(false));

        graph.addEdge(fileTypeNode, fileContentNode, new MyEdge(true));

        Cleaner eventChanger = null;
        Cleaner appendOptMenu = null;
        Cleaner addContextMenu = null;

        for (Cleaner c : p.getCleaners()) {
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
        graph.addEdge(fileContentNode, eventChanger, new MyEdge(true));

        Content coditionOptMenu = new Content(appendOptMenu.getPrototype().getExpressions().get(0));
        graph.addVertex(coditionOptMenu);
        graph.addEdge(eventChanger, coditionOptMenu, new MyEdge(true));

        graph.addVertex(appendOptMenu);
        graph.addEdge(coditionOptMenu, appendOptMenu, new MyEdge(true));

        Content conditionCss = new Content(addContextMenu.getPrototype().getExpressions().get(0));
        graph.addVertex(conditionCss);
        graph.addEdge(coditionOptMenu, conditionCss, new MyEdge(false));
        graph.addEdge(appendOptMenu, conditionCss, new MyEdge(true));

        graph.addEdge(conditionCss, end, new MyEdge(false));

        graph.addVertex(addContextMenu);
        graph.addEdge(conditionCss, addContextMenu, new MyEdge(true));

        ComponentNameProvider<ProcessingNode> idProvider = new ComponentNameProvider<ProcessingNode>() {
            @Override
            public String getName(ProcessingNode t) {
                String res = t.toString();
                if (t instanceof Cleaner) {
                    res = ((Cleaner) t).getId();
                }
                if (t instanceof FileType) {
                    res = t.toString().replace("*", "_").replace(".", "_");
                }
                if (t instanceof FileContent) {
                    res = FileContent.class.getSimpleName().replace("*", "_").replace(".", "_");
                }
                if (t instanceof Content) {
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

        ComponentNameProvider<MyEdge> edgeLabel = new ComponentNameProvider<MyEdge>() {
            @Override
            public String getName(MyEdge t) {
                return t.sign + "";
            }

        };

        GraphExporter<ProcessingNode, MyEdge> exporter = new DOTExporter<>(idProvider, labelProvider, edgeLabel);
        Writer writer = new StringWriter();
        try {
            exporter.exportGraph(graph, writer);
        } catch (ExportException ex) {
            ex.printStackTrace();
        }
        System.out.println(writer.toString());
    }
}
