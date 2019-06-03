/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.conditions;

import processor.core.file.ConditionalPattern;

/**
 *
 * @author cbaez
 */
public class Content extends Condition<String>{
    protected ConditionalPattern pattern;

    public Content() {
    }

    public Content(ConditionalPattern pattern) {
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
    
}
