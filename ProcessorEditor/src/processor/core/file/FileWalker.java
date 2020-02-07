/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import processor.profile.DIEmulator;

/**
 * A {@code FileVisitor} that finds all files that match the specified pattern.
 */
public class FileWalker extends SimpleFileVisitor<Path> {
    Map variableHolder=  DIEmulator.getVariableHolder();
    protected boolean done;
    protected Profile profile;
   
    

    public FileWalker(Profile profile) {
        this.profile = profile;
        this.profile.getFileCentral().cleanData();
        variableHolder.clear();
        
    }
    

    // Invoke the pattern matching
    // method on each file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        ProcessingResult processingResult = profile.getFileProcessor().processFile(file.toFile(), false);
        if(processingResult.isPassed()){
            //profile.getFileCentral().getMatchedFiles().add(file);
            profile.getFileCentral().registerProcessResult(file.toAbsolutePath().toString(), processingResult);
        }
        
        return CONTINUE;
    }

    // Invoke the pattern matching
    // method on each directory.
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if(dir.getFileName().toString().equalsIgnoreCase("CVS") || dir.getFileName().toString().equalsIgnoreCase(".git") )
            return FileVisitResult.SKIP_SUBTREE;
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    
    
    
}
