/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cbaez
 */
public class Profile {
    protected String name;
    protected String description;
    protected String lastWorkingDirectory;
    protected String workingDirectory;
    
    protected FilePrototype basePrototype;
    protected Map<String, FilePrototype> prototypesMap;
    
    protected List<Cleaner> cleaners;
    protected FileCentral fileCentral;
    
    protected FileMatcher fileMatcher;
    protected FileProcessor fileProcessor;

    public Profile(FilePrototype prototype, List<Cleaner> cleaners) {
        this.basePrototype = prototype;
        this.cleaners = cleaners;
        this.fileCentral = new FileCentral(this);
        this.prototypesMap = new HashMap<>();
    }
    
    public Profile(){
        this.fileCentral = new FileCentral(this);
    }

    public FilePrototype getBasePrototype() {
        return basePrototype;
    }

    public void setBasePrototype(FilePrototype prototype) {
        this.basePrototype = prototype;
    }

    public List<Cleaner> getCleaners() {
        return cleaners;
    }

    public void setCleaners(List<Cleaner> cleaners) {
        this.cleaners = cleaners;
    }

    public List<FilePrototype> getPrototypes(){
        return new LinkedList<>(getPrototypesMap().values());
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastWorkingDirectory() {
        return lastWorkingDirectory;
    }

    public void setLastWorkingDirectory(String lastWorkingDirectory) {
        this.lastWorkingDirectory = lastWorkingDirectory;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }
    
    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public Map<String, FilePrototype> getPrototypesMap() {
        return prototypesMap;
    }

    public void setPrototypesMap(Map<String, FilePrototype> prototypeMap) {
        this.prototypesMap = prototypeMap;
        this.basePrototype = prototypesMap.get("base");
    }

    public FileCentral getFileCentral() {
        return fileCentral;
    }    

    public FileMatcher getFileMatcher() {
        return fileMatcher;
    }

    public void setFileMatcher(FileMatcher fileMatcher) {
        this.fileMatcher = fileMatcher;
    }

    public FileProcessor getFileProcessor() {
        return fileProcessor;
    }

    public void setFileProcessor(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }
    
    
    public String toString(){
        return getName();
    }
   
}
