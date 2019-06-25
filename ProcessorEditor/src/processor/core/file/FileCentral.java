/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author cbaez
 */
public class FileCentral {

    protected Profile project;
    protected Set<Path> processedFiles;
    protected Map<String, ProcessingResult> resultMap;

    public FileCentral(Profile project) {
        this.project = project;
        processedFiles = new HashSet<>();
        resultMap = new HashMap<>();
    }
    
    public void cleanData(){
        processedFiles = new HashSet<>();
        resultMap = new HashMap<>();
    }

    public Set<Path> getProcessedFiles() {
        return processedFiles;
    }

   

    public Map<String, ProcessingResult> getResultMap() {
        return resultMap;
    }
    
    
    public void registerProcessResult(String p, ProcessingResult res){
        resultMap.put(p, res);
    }
    
   
}
