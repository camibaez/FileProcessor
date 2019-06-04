/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import processor.core.conditions.FilePrototype;
import processor.core.rules.RuleCluster;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import processor.core.DecisionEdge;
import processor.core.ProcessingNode;
import processor.core.conditions.Condition;
import processor.core.rules.Action;

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
    
    protected List<RuleCluster> cleaners;
    protected FileCentral fileCentral;
    
    protected FileMatcher fileMatcher;
    protected FileProcessor fileProcessor;
    
    protected DefaultDirectedGraph<ProcessingNode, DecisionEdge> graph;
    protected List<Action> actions;
    protected List<Condition> conditions;

    public Profile(FilePrototype prototype, List<RuleCluster> cleaners) {
        this.basePrototype = prototype;
        this.cleaners = cleaners;
        this.fileCentral = new FileCentral(this);
        this.prototypesMap = new HashMap<>();
        
        this.actions = new LinkedList<>();
        this.conditions = new LinkedList<>();
    }
    
    public Profile(){
        this.fileCentral = new FileCentral(this);
    }

    public FilePrototype getBasePrototype() {
        return basePrototype;
    }

    public void setBasePrototype(FilePrototype prototype) {
        this.basePrototype = prototype;
    }

    public List<RuleCluster> getCleaners() {
        return cleaners;
    }

    public void setCleaners(List<RuleCluster> cleaners) {
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
        loadGraphData(graph);
    }
    
    protected void loadGraphData(Graph graph){
        this.conditions = new LinkedList<>();
        this.actions = new LinkedList<>();
        graph.vertexSet().forEach(v -> {
            if(v instanceof Condition)
                conditions.add((Condition) v);
            if(v instanceof Action){
                if(v instanceof RuleCluster){
                    ((RuleCluster) v).getRules().forEach(r -> {
                        actions.add(r);
                    });
                }else{
                   actions.add((Action) v); 
                }
            }
        });
    }

    public List<Action> getActions() {
        return actions;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
    
    
    
    
    public String toString(){
        return getName();
    }
   
}
