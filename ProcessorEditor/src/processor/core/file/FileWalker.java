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
        
    }

    protected boolean checkFileName(Path file) {
       return true;
    }

   

    

    // Invoke the pattern matching
    // method on each file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        /*
        try {
            FilePrototype basicPrototype = profile.getBasePrototype();
            if(!checkPrototype(basicPrototype, file))
                return CONTINUE;
            profile.getFileCentral().linkFileToPrototype(basicPrototype, file);
            
            
            Collection<FilePrototype> prototypesList = profile.getPrototypesMap().values();
            //prototypesList.remove(basicPrototype);
            for (FilePrototype p : prototypesList) {
                if(p == basicPrototype)
                    continue;
                if(checkPrototype(p, file)){
                    profile.getFileCentral().addFilePrototype(p);
                    profile.getFileCentral().linkFileToPrototype(p, file);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FileMatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    */
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
