/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.conditions;

/**
 *
 * @author cbaez
 */
public class GenericContent extends Condition<String>{
    protected ConditionalPattern pattern;

    public GenericContent() {
    }

    public GenericContent(ConditionalPattern pattern) {
        this.pattern = pattern;
    }
    
    @Override
    public boolean test(String o) {
        pattern.matches(o);
        int finalState = pattern.finalState();
        pattern.restart();
        return finalState == 1;
        
    }
    
     public String toString(){
        return pattern.toString();
    }

    public ConditionalPattern getPattern() {
        return pattern;
    }

    public void setPattern(ConditionalPattern pattern) {
        this.pattern = pattern;
    }
     
     
    
}
