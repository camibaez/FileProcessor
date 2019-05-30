/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import processor.core.file.FileMatcher;
import processor.core.file.FileProcessor;
import processor.core.file.Profile;


import processor.profile.ProfileAdministration;

/**
 *
 * @author cbaez
 */
public class DummySuplier {

    public static void loadDummyProject() {
        File file = new File("C:\\Users\\cbaez\\Documents\\NetBeansProjects\\HTMLFixer\\conf\\test-files\\BigChangeTest\\proj1.json");
        ProjectCentral.instance().setProfileFile(file);
        ProjectCentral.instance().setProfile(ProfileAdministration.loadProject(file.getAbsolutePath()));
        ProjectCentral.instance().getProfile().setLastWorkingDirectory(getDummyFileFolder().toString());
        ProjectCentral.instance().getProfile().setWorkingDirectory(getDummyFileFolder().toString());
    }

    public static Path getDummyFileFolder() {
        Path path = Paths.get("C:\\Users\\cbaez\\Documents\\NetBeansProjects\\HTMLFixer\\conf\\test-files\\BigChangeTest\\files");
        return path;
    }

    public static void loadMatchingFiles() {
        Profile profile = ProjectCentral.instance().getProfile();
        FileMatcher fileMatcher = new FileMatcher(profile);
        profile.setFileMatcher(fileMatcher);
        try {
            long time = System.currentTimeMillis();
            Files.walkFileTree(Paths.get(profile.getWorkingDirectory()), fileMatcher);

            System.out.println("Serching ended. Matched = "
                    + profile.getFileCentral().getMatchedFiles().size()
                    + " Time = " + (System.currentTimeMillis() - time)
            );

            FileProcessor fileProcessor = new FileProcessor(profile, profile.getCleaners());
            profile.setFileProcessor(fileProcessor);
        } catch (IOException ex) {
            Logger.getLogger(DummySuplier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}