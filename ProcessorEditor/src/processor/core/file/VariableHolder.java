/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.util.HashMap;

/**
 *
 * @author cbaez
 */
public class VariableHolder extends HashMap<String, String>{
    public String[][] export(){
        int rows = keySet().size();
        String[][] data = new String[rows][2];
        int i = 0;
        for(Entry<String, String> e: this.entrySet()){
            data[i][0] = e.getKey();
            data[i][1] = e.getValue();
        };
        
        return data;
    }
}
