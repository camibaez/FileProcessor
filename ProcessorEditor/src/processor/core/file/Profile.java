/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.jgrapht.graph.DefaultDirectedGraph;
import processor.core.graph.DecisionEdge;
import processor.core.graph.DecisionGraph;
import processor.core.graph.GraphNode;
import processor.core.graph.serialization.GraphBuilder;

import processor.core.graph.conditions.Condition;
import processor.core.graph.actions.Action;
import processor.core.lineal.ComplexNode;

/**
 *
 * @author cbaez
 */
public class Profile {

    protected String name;
    protected String description;
    protected String lastWorkingDirectory;
    protected String workingDirectory;

    protected FileCentral fileCentral;

    protected FileWalker fileMatcher;
    protected FileProcessor fileProcessor;

    protected DecisionGraph graph;
    protected GraphBuilder graphBuilder;
    protected List<GraphNode> nodes;
   

    public Profile() {
        this.fileCentral = new FileCentral(this);
        this.nodes = new LinkedList<>();
        this.graphBuilder = new GraphBuilder();
        this.graph = new DecisionGraph();
        this.fileProcessor = new FileProcessor(this);
    }

    public void reloadGraph() {
        this.graph.reload();
    }
    
    public List<GraphNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphNode> nodes) {
        this.nodes = nodes;
    }

    public void addNode(GraphNode node) {
        this.nodes.add(node);
        this.reloadGraph();
    }

    public void removeNode(GraphNode node) {
        this.nodes.remove(node);
        reloadGraph();
    }
    
    public void moveNode(GraphNode node, int pos){
        int i = this.nodes.indexOf(node);
        if((pos < 0 && i > 0) || 
           (pos > 0 && i > -1 && i < this.nodes.size() - 1)){
            Collections.swap(nodes, i, i + pos);
            this.reloadGraph();
        }
        
    }

    public DecisionGraph getGraph() {
        return graph;
    }

    public void setGraph(DecisionGraph graph) {
        this.graph = graph;

    }

    public GraphBuilder getGraphBuilder() {
        return graphBuilder;
    }

    public List<Action> getActions() {
        List<Action> l = new LinkedList<>();
        nodes.forEach(n -> {
            if(n instanceof Action)
                l.add((Action) n);
        });
        
        return l;  
    }

    public List<Condition> getConditions() {
        List<Condition> l = new LinkedList<>();
        nodes.forEach(n -> {
            if(n instanceof Condition)
                l.add((Condition) n);
        });
        return l;  
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastWorkingDirectory() {
        return lastWorkingDirectory;
    }

    public void setLastWorkingDirectory(String lastWorkingDirectory) {
        this.lastWorkingDirectory = lastWorkingDirectory;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public FileCentral getFileCentral() {
        return fileCentral;
    }

    public FileWalker getFileMatcher() {
        return fileMatcher;
    }

    public void setFileMatcher(FileWalker fileMatcher) {
        this.fileMatcher = fileMatcher;
    }

    public FileProcessor getFileProcessor() {
        return fileProcessor;
    }

    public void setFileProcessor(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    public String toString() {
        return getName();
    }

}
