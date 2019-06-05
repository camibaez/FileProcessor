/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.conditions;

/**
 *
 * @author cbaez
 */
public class TextContent extends Condition<String>{
    protected ConditionalPattern pattern;

    public TextContent() {
        this.pattern = ConditionalPattern.compile("", 0, "+");
    }

    public TextContent(ConditionalPattern pattern) {
        this.pattern = pattern;
    }
    
    @Override
    public boolean test(String o) {
        pattern.matches((String)o);
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
