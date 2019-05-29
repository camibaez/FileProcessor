/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author cbaez
 */
public class FilePrototype {
    protected String description;
    protected String id;
    protected String extensions;
    //rc;expr
    protected List<ConditionalPattern> expressions;

    /**
     * 
     * @param extensions Using GLOB syntax *.{html,jsp}
     * @param expressions List of extender regex: &lt;condition$gt;rexgex/&lt;falgs&gt;. Example: +casa.+/1
     */
    public FilePrototype(String extensions, List<ConditionalPattern> expressions) {
        this.extensions = extensions;
        this.expressions = expressions;
       

    }

    protected void readPatterns(List<String> expressions){
        
    }
    
    
    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public List<ConditionalPattern> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<ConditionalPattern> expressions) {
        this.expressions = expressions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString(){
        return getId();
    }
    
}
