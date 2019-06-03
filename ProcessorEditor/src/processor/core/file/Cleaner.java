/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.util.List;
import processor.core.Action;
import processor.core.rules.Rule;

/**
 *
 * @author cbaez
 */
public class Cleaner extends Action{

    protected String id ="";
    protected String description ="";
    protected FilePrototype prototype;

    protected List<Rule> rules;
    
    protected boolean active = true;

    public Cleaner(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public Object clean(Object content) {
        for (Rule rule : rules) {
            content = rule.process(content);
        }
        return content;
    }

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

    public String toString(){
        return "<" + id + ">";
    }

    public FilePrototype getPrototype() {
        return prototype;
    }

    public void setPrototype(FilePrototype prototype) {
        this.prototype = prototype;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    
    
}
