/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import processor.core.graph.DecisionEdge;
import processor.core.graph.GraphBuilder;

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

    protected DefaultDirectedGraph<ComplexNode, DecisionEdge> graph;
    protected GraphBuilder graphBuilder;
    protected List<ComplexNode> nodes;
    protected List<Action> actions;
    protected List<Condition> conditions;

    public Profile() {
        this.fileCentral = new FileCentral(this);
        this.actions = new LinkedList<>();
        this.conditions = new LinkedList<>();
        this.nodes = new LinkedList<>();
        this.graphBuilder = new GraphBuilder();
        this.graph = graphBuilder.buildEmpty();
        this.fileProcessor = new FileProcessor(this);
    }

    public void reloadGraph() {
        this.graph = graphBuilder.buildNodes(nodes);
    }
    
    public List<ComplexNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<ComplexNode> nodes) {
        this.nodes = nodes;
        this.reloadGraph();
    }

    public void addNode(ComplexNode node) {
        this.nodes.add(node);
        this.reloadGraph();
    }

    public void removeNode(ComplexNode node) {
        this.nodes.remove(node);
        reloadGraph();
    }
    
    public void moveNode(ComplexNode node, int pos){
        int i = this.nodes.indexOf(node);
        if((pos < 0 && i > 0) || 
           (pos > 0 && i > -1 && i < this.nodes.size() - 1)){
            Collections.swap(nodes, i, i + pos);
            this.reloadGraph();
        }
        
    }

    public DefaultDirectedGraph<ComplexNode, DecisionEdge> getGraph() {
        return graph;
    }

    public void setGraph(DefaultDirectedGraph<ComplexNode, DecisionEdge> graph) {
        this.graph = graph;

    }

    public GraphBuilder getGraphBuilder() {
        return graphBuilder;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public void addAction(Action a) {
        actions.add(a);
    }

    public void removeAction(Action a) {
        actions.remove(a);
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(Condition c) {
        this.conditions.add(c);
    }

    public void removeCondition(Condition c) {
        this.conditions.remove(c);
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
