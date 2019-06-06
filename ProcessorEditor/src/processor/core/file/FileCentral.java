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
import java.util.TreeSet;

/**
 *
 * @author cbaez
 */
public class FileCentral {

    protected Profile project;
    protected Set<Path> processedFiles;
    protected List<Path> matchedFiles;
    protected Map<Path, ProcessingResult> resultMap;

    public FileCentral(Profile project) {
        this.project = project;
        matchedFiles = new LinkedList<>();
        processedFiles = new HashSet<>();
        resultMap = new HashMap<>();
    }
    
    public void cleanData(){
        matchedFiles = new LinkedList<>();
        processedFiles = new HashSet<>();
        resultMap = new HashMap<>();
    }

    public Set<Path> getProcessedFiles() {
        return processedFiles;
    }

    public List<Path> getMatchedFiles() {
        return matchedFiles;
    }

    public Map<Path, ProcessingResult> getResultMap() {
        return resultMap;
    }
    
    
    public void registerProcessResult(Path p, ProcessingResult res){
        resultMap.put(p, res);
    }
}
