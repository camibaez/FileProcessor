/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph;

/**
 *
 * @author cbaez
 */
public class StartNode extends GraphNode{
    public String toString(){
        return "START_NODE";
    }
    
    
    public String getId(){
        return toString();
    }
}
