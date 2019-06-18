/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.conditions;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;


public class FilePattern extends Condition<File>{
    protected String pattern;
    private transient PathMatcher matcher;
    
    public FilePattern(String namePattern){
        this.pattern = namePattern;
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }
    
    @Override
    public boolean test(File file) {
        Path name = file.toPath().getFileName();
        return name != null && matcher.matches(name);
    }
   
    public String toString(){
        return "<" + getId() + ">:" + pattern;
    }

    public void setPattern(String p){
        pattern = p;
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }
    
    public String getPattern() {
        return pattern;
    }

   
    
    
}
