/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author cbaez
 */
public class FileCentral{
    protected Profile project;
    protected Map<FilePrototype, Set<Path>> prototypeFileMap;
    protected Set<Path> processedFiles;

    
    public FileCentral(Profile project){
        this.project = project;
        prototypeFileMap = new HashMap<>();
        processedFiles = new HashSet<>();
    }
    
    public void addFilePrototype(FilePrototype p){
        if(!prototypeFileMap.containsKey(p))
            prototypeFileMap.put(p, new TreeSet<>());
         
    }
    
    public void linkFileToPrototype(FilePrototype p, Path f){
       prototypeFileMap.get(p).add(f);
    }

    
    public Set<Path> getMatchedFiles() {
        return prototypeFileMap.get(project.getBasePrototype());
    }
    
    public boolean belongsTo(FilePrototype prototype, Path f){
        return prototypeFileMap.get(prototype).contains(f);
    }
    
    public Map<FilePrototype, Set<Path>> getPrototypeFileMap() {
        return prototypeFileMap;
    }

    public Set<Path> getProcessedFiles() {
        return processedFiles;
    }
}
