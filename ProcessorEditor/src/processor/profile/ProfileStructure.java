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
import processor.core.file.Profile;
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

    public ProfileStructure(Profile profile){
        name = profile.getName();
        description = profile.getDescription();
        lastWorkingDirectory = profile.getLastWorkingDirectory();
        treeLocation = "";
        
        conditions = profile.getConditions();
        actions = profile.getActions();
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
