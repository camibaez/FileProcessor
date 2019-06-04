/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.conditions;

import processor.core.ProcessingNode;

/**
 *
 * @author cbaez
 */
public abstract class Condition<T> extends ProcessingNode{
    protected String id;
    public abstract boolean test(T o);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
