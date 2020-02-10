/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph;

/**
 *
 * @author cbaez
 */
public abstract class GraphNode{ 
    protected String id;
    protected boolean active = true;
    

    public String getId() {
        if(id == null)
            id = generateRandomId();
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public abstract String generateRandomId();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
    
}
