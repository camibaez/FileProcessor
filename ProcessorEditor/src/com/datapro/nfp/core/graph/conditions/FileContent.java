/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph.conditions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author cbaez
 */
public class FileContent extends Condition<File>{

    protected List<ConditionalPattern> expressions;

    public FileContent() {
        expressions = new LinkedList<>();
    }
    
    
    public FileContent(List<ConditionalPattern> conditionalPattern) {
        this.expressions = conditionalPattern;
    }
    
    
    @Override
    public boolean test(File file) {
        List<ConditionalPattern> expressions = new LinkedList<>(this.expressions);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        boolean failed = false;
        for (ConditionalPattern p : this.expressions) {
            failed |= (p.finalState() == -1);
            p.restart();
        }

        return !failed;
    }

    
    
    @Override
    public String toString(){
        return "<" + getId() + ">";
    }

    public List<ConditionalPattern> getExpressions() {
        return expressions;
    }
    
    
    
}
