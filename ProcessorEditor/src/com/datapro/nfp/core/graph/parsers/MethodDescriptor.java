/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph.parsers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cbaez
 */
public class MethodDescriptor {
    private String name;
    private String returnType;
    private List<VariableDescriptor> parameters;
    private String visibility;
    private boolean isStatic;
    private boolean isFinal;
    
    private CodeScope codeScope;

    
    public MethodDescriptor(CodeScope codeScope){
        int signatureEnd = codeScope.code.indexOf("{");
        if(signatureEnd >= 0){
            String signature = codeScope.code.substring(0, signatureEnd);
            String token = "";
            for(int i = 0; i < signature.length(); i++){
                char c = signature.charAt(i);
                token+= c;
                
                
            }
        }
        
        
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the returnType
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * @param returnType the returnType to set
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    /**
     * @return the parameters
     */
    public List<VariableDescriptor> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(List<VariableDescriptor> parameters) {
        this.parameters = parameters;
    }

    /**
     * @return the visibility
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * @param visibility the visibility to set
     */
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    /**
     * @return the isStatic
     */
    public boolean isIsStatic() {
        return isStatic;
    }

    /**
     * @param isStatic the isStatic to set
     */
    public void setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    /**
     * @return the isFinal
     */
    public boolean isIsFinal() {
        return isFinal;
    }

    /**
     * @param isFinal the isFinal to set
     */
    public void setIsFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    /**
     * @return the codeScope
     */
    public CodeScope getCodeScope() {
        return codeScope;
    }

    /**
     * @param codeScope the codeScope to set
     */
    public void setCodeScope(CodeScope codeScope) {
        this.codeScope = codeScope;
    }
    
    
    
}
