/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A {@code FileVisitor} that finds all files that match the specified pattern.
 */
public class FileMatcher extends SimpleFileVisitor<Path> {
    protected boolean done;
    protected Profile profile;
    private final PathMatcher matcher;
    

    public FileMatcher(Profile profile) {
        this.profile = profile;
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + profile.getBasePrototype().getExtensions());
        profile.getFileCentral().addFilePrototype(profile.getBasePrototype());
    }

    protected boolean checkFileName(Path file) {
        Path name = file.getFileName();
        return name != null && matcher.matches(name);
    }

    protected boolean checkContent(FilePrototype prototype, Path file) throws IOException {
        if (prototype.expressions == null || prototype.expressions.isEmpty()) {
            return true;
        }
        List<ConditionalPattern> expressions = new LinkedList<>(prototype.getExpressions());

        try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
            String line = reader.readLine();
            while (line != null && expressions.size() > 0) {
                Iterator<ConditionalPattern> iterator = expressions.iterator();
                while (iterator.hasNext()) {
                    ConditionalPattern p = iterator.next();
                    int res = p.matches(line);
                    if (res == -1) {
                        iterator.remove();
                    }
                    if (res == 1) {
                        iterator.remove();
                    }
                }
                // read next line
                line = reader.readLine();
            }
        }

        boolean failed = false;
        for (ConditionalPattern p : prototype.getExpressions()) {
            failed |= (p.finalState() == -1);
            p.restart();
        }

        return !failed;
    }

    public boolean checkPrototype(FilePrototype prototype, Path file) throws IOException {
        if (!prototype.getExtensions().isEmpty() && !checkFileName(file)) {
            return false;
        }
        if (!prototype.getExpressions().isEmpty() && !checkContent(prototype, file)) {
            return false;
        }

        return true;
    }

    // Invoke the pattern matching
    // method on each file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
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
