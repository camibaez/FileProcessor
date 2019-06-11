/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.windows;

import processor.genericeditor.windows.ProcessorEditorMainPanel;
import java.awt.BorderLayout;
import org.netbeans.api.diff.DiffView;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import processor.core.file.Profile;
import processor.core.file.ProjectCentral;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//processor.editor//EditorProcessor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "EditorProcessorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "processor.editor.EditorProcessorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_EditorProcessorAction",
        preferredID = "EditorProcessorTopComponent"
)
@Messages({
    "CTL_EditorProcessorAction=File Processor",
    "CTL_EditorProcessorTopComponent=File Processor",
    "HINT_EditorProcessorTopComponent=This is a EditorProcessor window"
})
public final class EditorProcessorTopComponent extends TopComponent {
    
    Profile profile;
    TopComponent diffTopComponent = null;
    DiffView openendDiff = null;
    
    public EditorProcessorTopComponent() {
        initComponents();
        setName(Bundle.CTL_EditorProcessorTopComponent());
        setToolTipText(Bundle.HINT_EditorProcessorTopComponent());
        
        profile = ProjectCentral.instance().getProfile();
        rootPanel.add(new ProcessorEditorMainPanel(profile), BorderLayout.CENTER);
    }
    
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootPanel = new javax.swing.JPanel();

        rootPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel rootPanel;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }
    
    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }
    
    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }
    
    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
