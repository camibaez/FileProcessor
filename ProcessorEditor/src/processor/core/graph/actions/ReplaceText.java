/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.actions;

import java.util.regex.Pattern;

/**
 *
 * @author cbaez
 */
public class ReplaceText extends TextTransformer {

    protected Pattern pattern;
    protected String replace;



    public ReplaceText(String pattern, String replace) {
        this.pattern = Pattern.compile(pattern);
        this.replace = replace;

    }

    public ReplaceText(String pattern, String replace, int flags) {
        this.pattern = Pattern.compile(pattern, flags);
        this.replace = replace;
        
    }

    @Override
    public Object transform(String target) {
        return pattern.matcher(target).replaceAll(replace);
    }

    public Pattern getPattern(){
        return pattern;
    }
    
    public void setPattern(Pattern pattern){
        this.pattern = pattern;
    }
    
    public String getPatternText() {
        return pattern.pattern();
    }

    public String getReplace() {
        return replace;
    }

    public int getFlags() {
        return pattern.flags();
    }

    public void setReplace(String replace) {
        this.replace = replace;
    }
    
    public String toString(){
        return pattern.toString();
    }

}
