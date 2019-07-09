/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.jslinker;

import processor.core.file.FileProcessor;

/**
 *
 * @author cbaez
 */
public class VariableHolder {
    public static Object get(String key){
        return FileProcessor.variableHolder.get(key);
        
    }
}
