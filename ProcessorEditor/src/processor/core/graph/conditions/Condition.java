/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.conditions;

import processor.core.graph.GraphNode;

/**
 *
 * @author cbaez
 */
public abstract class Condition<T> extends GraphNode {

    public abstract boolean test(T o);

    @Override
    public String generateRandomId() {
        return "c" + this.hashCode();
    }
}
