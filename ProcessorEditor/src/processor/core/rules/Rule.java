/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.rules;

/**
 *
 * @author cbaez
 * @param <T>
 */
public abstract class Rule<T> {
    protected String id ="";
    protected String description="";

    public Object process(Object target) {
        T result = TypeTransformer.transformFor(this, target);
        return transform(result);
    }

    public abstract Object transform(T target);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
