/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph.jslinker;

import com.datapro.nfp.core.graph.parsers.CodeScope;

/**
 *
 * @author cbaez
 */
public class JavaParser {

    public static int[] getMethodScopeOf(String code, int index){
         CodeScope scope = new com.datapro.nfp.core.graph.parsers.JavaParserUtils(code).getMethodScopeOf(index);
        if(scope == null)
            return new int[]{};
        return new int[]{scope.getStart(), scope.getEnd()};
    }
    
  
}
