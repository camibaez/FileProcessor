/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.project;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import processor.core.file.FileMatcher;
import processor.core.file.FileProcessor;
import processor.core.file.Profile;

/**
 *
 * @author cbaez
 */
public class Runtime {

    public static void run(String projectPath, String workspace) throws Exception {
        Profile profile = ProjectAdministration.loadProject(projectPath);

        FileMatcher finder = new FileMatcher(profile);
        Files.walkFileTree(Paths.get(workspace), finder);
        profile.getFileCentral().getMatchedFiles().forEach(f -> {
            System.out.println(f.toFile().getAbsolutePath());
        });

        FileProcessor processor = new FileProcessor(profile, profile.getCleaners());
        processor.processAll();
    }
}
