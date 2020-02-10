/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.profile;

import java.io.File;
import com.datapro.nfp.core.file.Profile;
import com.datapro.nfp.core.file.ProjectCentral;
import com.datapro.nfp.core.file.VariableHolder;

/**
 *
 * @author cbaez
 */
public class DIEmulator {
    protected static ProjectCentral projectCentral;
    protected static VariableHolder variableHolder;
    
    public static ProjectCentral getProjectCentral(){
        if(projectCentral == null){
            projectCentral = new ProjectCentral();
        }
        return projectCentral;
    }
    
    public static Profile getProfile(){
        return getProjectCentral().getProfile();
    }
    
    public static void setProfile(Profile profile){
        getProjectCentral().setProfile(profile);
    }
    
    public static File getProfileFile(){
        return getProjectCentral().getProfileFile();
    }
    
    public static void setProfileFile(File profileFile){
        getProjectCentral().setProfileFile(profileFile);
    }
    
    
    public static VariableHolder getVariableHolder(){
        if(variableHolder == null){
            variableHolder = new VariableHolder();
        }
        return variableHolder;
    }
   
    public static void setVariableHolder(VariableHolder holder){
        variableHolder = holder;
    }
}
