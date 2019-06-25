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

/**
 * A {@code FileVisitor} that finds all files that match the specified pattern.
 */
public class FileWalker extends SimpleFileVisitor<Path> {
    protected boolean done;
    protected Profile profile;
   
    

    public FileWalker(Profile profile) {
        this.profile = profile;
        this.profile.getFileCentral().cleanData();
        
    }
    

    // Invoke the pattern matching
    // method on each file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        ProcessingResult processingResult = profile.getFileProcessor().processFile(file.toFile());
        if(processingResult.isPassed()){
            //profile.getFileCentral().getMatchedFiles().add(file);
            profile.getFileCentral().registerProcessResult(file, processingResult);
        }
        
        return CONTINUE;
    }

    // Invoke the pattern matching
    // method on each directory.
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        //find(dir);
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
