/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph.jslinker;

import java.util.Map;
import com.datapro.nfp.profile.DIEmulator;

/**
 *
 * @author cbaez
 */
public class VariableHolder {
    protected static Map variableHolder = DIEmulator.getVariableHolder();
    public static Object get(String key){
        return variableHolder.get(key);
        
    }
}