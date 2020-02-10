/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.file;

import java.io.File;
import com.datapro.nfp.profile.ProfileSerializer;

/**
 *
 * @author cbaez
 */
public class ProjectCentral {
 
    private Profile profile;
    private File profileFile;

    

    
    public void reloadProject(){
        setProfile(ProfileSerializer.loadProject(getProfileFile()));
    }

    
    /**
     * @return the profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * @param profile the profile to set
     */
    
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    

    /**
     * @return the profileFile
     */
    public File getProfileFile() {
        return profileFile;
    }

    /**
     * @param profileFile the profileFile to set
     */
    public void setProfileFile(File profileFile) {
        this.profileFile = profileFile;
    }
    
    
    
     
}
