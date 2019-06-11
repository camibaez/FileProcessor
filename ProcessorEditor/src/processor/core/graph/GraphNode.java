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
public abstract class GraphNode{ 
    protected String id;

    public String getId() {
        if(id == null)
            id = generateRandomId();
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public abstract String generateRandomId();
    
    
}
