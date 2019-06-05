/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.lineal;

import processor.core.graph.GraphNode;
import processor.core.graph.actions.Action;
import processor.core.graph.conditions.Condition;

/**
 *
 * @author cbaez
 */
public class ComplexNode{
    public static int OMISSBLE = 0, UNOMISSIBLE = 1;
    protected int type;
    
    protected Action action;
    protected Condition condition;

    public ComplexNode(){
        
    }
    
    public ComplexNode(Condition condition, Action action, int type) {
        this.type = type;
        this.action = action;
        this.condition = condition;
    }
    
    
    
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    
    
}
