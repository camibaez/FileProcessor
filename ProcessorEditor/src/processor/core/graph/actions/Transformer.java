/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.actions;

/**
 *
 * @author cbaez
 * @param <T>
 */
public abstract class Transformer<T> extends Action{
    protected String description="";
    
    protected boolean active;

    public Object process(Object target) {
        T result = TypeTranslator.transalteFor(this, target);
        return transform(result);
    }

    public abstract Object transform(T target);

    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
}
