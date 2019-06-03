/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import processor.core.rules.RuleCluster;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author cbaez
 */
public class LogCentral {  
    int visitedFiles;
    int matchedFiles;
    int processedFiles;
    
    protected Map<Path, Set<RuleCluster>> fileProcessorRecords;

    public LogCentral(){
        fileProcessorRecords = new TreeMap<>();
    }
    
    public void addRecord(Path p, RuleCluster c){
        if(fileProcessorRecords.containsKey(p)){
            fileProcessorRecords.get(p).add(c);
        }else{
            Set<RuleCluster> set = new HashSet<>();
            set.add(c);
            fileProcessorRecords.put(p, set);
        }
    }

    public Map<Path, Set<RuleCluster>> getFileProcessorRecords() {
        return fileProcessorRecords;
    }

    public void setFileProcessorRecords(Map<Path, Set<RuleCluster>> fileProcessorRecords) {
        this.fileProcessorRecords = fileProcessorRecords;
    } 
    
    
    public int getProcessedFiles(){
        return getFileProcessorRecords().keySet().size();
    }
}
