/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor;

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
import processor.editor.actions.LoadMatcher;

import processor.project.ProjectAdministration;

/**
 *
 * @author cbaez
 */
public class DummySuplier {

    public static void loadDummyProject() {
        File file = new File("C:\\Users\\cbaez\\Documents\\NetBeansProjects\\HTMLFixer\\conf\\test-files\\BigChangeTest\\proj1.json");
        ProjectCentral.instance().setProjectFile(file);
        ProjectCentral.instance().setProject(ProjectAdministration.loadProject(file.getAbsolutePath()));
        ProjectCentral.instance().getProject().setLastWorkingDirectory(getDummyFileFolder().toString());
        ProjectCentral.instance().getProject().setWorkingDirectory(getDummyFileFolder().toString());
    }

    public static Path getDummyFileFolder() {
        Path path = Paths.get("C:\\Users\\cbaez\\Documents\\NetBeansProjects\\HTMLFixer\\conf\\test-files\\BigChangeTest\\files");
        return path;
    }

    public static void loadMatchingFiles() {
        Profile profile = ProjectCentral.instance().getProject();
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
            Logger.getLogger(LoadMatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
