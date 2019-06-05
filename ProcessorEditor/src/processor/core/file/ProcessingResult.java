/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.util.LinkedList;
import java.util.List;
import processor.core.graph.actions.Action;

/**
 *
 * @author cbaez
 */
public class ProcessingResult {
    protected boolean passed;
    protected List<Action> actions;
    protected Object result;

    public ProcessingResult(){
        actions = new LinkedList<>();
        passed = false;
    }
    
    
    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public List<Action> getActions() {
        return actions;
    }
    
    
}
