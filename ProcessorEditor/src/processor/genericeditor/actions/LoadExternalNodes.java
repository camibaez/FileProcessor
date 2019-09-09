/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import processor.core.file.Profile;
import processor.core.file.ProjectCentral;
import processor.core.graph.DecisionGraph;
import processor.profile.ProfileSerializer;

/**
 *
 * @author cbaez
 */
public class LoadExternalNodes implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        //fc.setCurrentDirectory(new java.io.File("C:\\Users\\cbaez\\Desktop\\ProcessorWorkspace"));
        fc.setDialogTitle("Import Project as Metanode.");
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            DecisionGraph graph = ProfileSerializer.loadProject(file).getGraph();
            Profile project = ProjectCentral.instance().getProfile();
            project.includeGraph(graph);
        } else {
            Logger.getLogger(OpenProjectAction.class.getName()).log(Level.SEVERE, "Open command cancelled by user.");
        }
    }
    
}
