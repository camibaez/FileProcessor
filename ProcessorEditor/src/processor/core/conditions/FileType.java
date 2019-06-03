/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.conditions;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

/**
 *
 * @author cbaez
 */
public class FileType extends Condition<Path>{
    protected String pattern;
    private transient final PathMatcher matcher;
    
    public FileType(String namePattern){
        this.pattern = namePattern;
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }
    
    @Override
    public boolean test(Path file) {
        Path name = file.getFileName();
        return name != null && matcher.matches(name);
    }
    
    
    
    public String toString(){
        return pattern;
    }

    public String getPattern() {
        return pattern;
    }

   
    
    
}
