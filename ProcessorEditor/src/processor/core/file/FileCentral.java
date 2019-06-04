/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author cbaez
 */
public class FileCentral{
    protected Profile project;
    protected Set<Path> processedFiles;
    

    
    public FileCentral(Profile project){
        this.project = project;
       
        processedFiles = new HashSet<>();
    }

    public Set<Path> getProcessedFiles() {
        return processedFiles;
    }
}
