/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.profile;

import java.util.LinkedList;
import java.util.List;
import javax.sound.sampled.Clip;
import org.jgrapht.graph.DefaultDirectedGraph;
import processor.core.DecisionEdge;
import processor.core.ProcessingNode;
import processor.core.conditions.Condition;
import processor.core.rules.Action;
import processor.core.rules.RuleCluster;

/**
 *
 * @author cbaez
 */
public class ProfileStructure {
    protected String name;
    protected String description;
    protected String lastWorkingDirectory;
    protected String treeLocation;
    
    protected List<Condition> conditions;
    protected List<Action> actions;

    public ProfileStructure(DefaultDirectedGraph<ProcessingNode, DecisionEdge> graph){
        conditions = new LinkedList();
        actions = new LinkedList<>();
        
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

    public String getTreeLocation() {
        return treeLocation;
    }

    public void setTreeLocation(String treeLocation) {
        this.treeLocation = treeLocation;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    
    
}
