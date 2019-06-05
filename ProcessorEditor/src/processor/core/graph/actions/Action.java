/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.actions;

import processor.core.graph.GraphNode;

/**
 *
 * @author cbaez
 */
public abstract class Action extends GraphNode{
    public abstract String getId();
    public abstract void setId(String id);
    public abstract Object process(Object o);
}
