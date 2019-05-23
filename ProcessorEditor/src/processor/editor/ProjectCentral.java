/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import processor.core.file.Profile;
import processor.project.ProjectAdministration;

/**
 *
 * @author cbaez
 */
public class ProjectCentral {

    protected static ProjectCentral instance;
    protected static Profile currentProfile;

    public static ProjectCentral instance() {
        if (instance == null) {
            instance = new ProjectCentral();
        }
        return instance;
    }

    public void setProfile(Profile p) {
        currentProfile = p;

    }

    public Profile getProfile() {
        return currentProfile;
    }

    
}
