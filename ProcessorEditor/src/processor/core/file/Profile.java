/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import processor.core.conditions.FilePrototype;
import processor.core.rules.ActionCluster;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import processor.core.DecisionEdge;
import processor.core.GraphBuilder;
import processor.core.ProcessingNode;
import processor.core.graph.conditions.Condition;
import processor.core.graph.actions.Action;

/**
 *
 * @author cbaez
 */
public class Profile {
    protected String name;
    protected String description;
    protected String lastWorkingDirectory;
    protected String workingDirectory;
    
    protected FilePrototype basePrototype;
    protected Map<String, FilePrototype> prototypesMap;
    
    protected List<ActionCluster> cleaners;
    protected FileCentral fileCentral;
    
    protected FileMatcher fileMatcher;
    protected FileProcessor fileProcessor;
    
    protected DefaultDirectedGraph<ProcessingNode, DecisionEdge> graph;
    protected GraphBuilder graphBuilder;
    protected List<Action> actions;
    protected List<Condition> conditions;

    public Profile(FilePrototype prototype, List<ActionCluster> cleaners) {
        this.basePrototype = prototype;
        this.cleaners = cleaners;
        this.fileCentral = new FileCentral(this);
        this.prototypesMap = new HashMap<>();
        
        this.actions = new LinkedList<>();
        this.conditions = new LinkedList<>();
        this.graph = new GraphBuilder().build();
    }
    
    public Profile(){
        this.fileCentral = new FileCentral(this);
        this.actions = new LinkedList<>();
        this.conditions = new LinkedList<>();
        this.graphBuilder =  new GraphBuilder();
        this.graph = graphBuilder.build();
    }

    public FilePrototype getBasePrototype() {
        return basePrototype;
    }

    public void setBasePrototype(FilePrototype prototype) {
        this.basePrototype = prototype;
    }

    public List<ActionCluster> getCleaners() {
        return cleaners;
    }

    public void setCleaners(List<ActionCluster> cleaners) {
        this.cleaners = cleaners;
    }

    public List<FilePrototype> getPrototypes(){
        return new LinkedList<>(getPrototypesMap().values());
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

    public Map<String, FilePrototype> getPrototypesMap() {
        return prototypesMap;
    }

    public void setPrototypesMap(Map<String, FilePrototype> prototypeMap) {
        this.prototypesMap = prototypeMap;
        this.basePrototype = prototypesMap.get("base");
    }

    public FileCentral getFileCentral() {
        return fileCentral;
    }    

    public FileMatcher getFileMatcher() {
        return fileMatcher;
    }

    public void setFileMatcher(FileMatcher fileMatcher) {
        this.fileMatcher = fileMatcher;
    }

    public FileProcessor getFileProcessor() {
        return fileProcessor;
    }

    public void setFileProcessor(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    public DefaultDirectedGraph<ProcessingNode, DecisionEdge> getGraph() {
        return graph;
    }

    public void setGraph(DefaultDirectedGraph<ProcessingNode, DecisionEdge> graph) {
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
    
    public void addAction(Action a){
        actions.add(a);
        graph.addVertex(a);
    }

    public void removeAction(Action a){
        actions.remove(a);
        graph.removeVertex(a);
    }
    
    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
    
    public void addCondition(Condition c){
        this.conditions.add(c);
        graph.addVertex(c);
    }
    
    public void removeCondition(Condition c){
        this.conditions.remove(c);
        this.graph.removeVertex(c);
    }
    
    public String toString(){
        return getName();
    }
   
}
