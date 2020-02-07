/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.windows;

import java.awt.BorderLayout;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import processor.core.file.Profile;
import processor.core.file.ProjectCentral;
import processor.genericeditor.windows.ProjectEditorMainPanel;
import processor.profile.DIEmulator;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//processor.editor.windows//ProjectEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ProjectEditorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)

@Messages({
    "CTL_ProjectEditorAction=ProjectEditor",
    "CTL_ProjectEditorTopComponent=Project Editor",
    "HINT_ProjectEditorTopComponent=This is a ProjectEditor window"
})
public final class ProjectEditorTopComponent extends TopComponent {
    ProjectCentral projectCentral = DIEmulator.getProjectCentral();
    Profile project = null;

    public ProjectEditorTopComponent() {
        initComponents();
        setName(Bundle.CTL_ProjectEditorTopComponent());
        setToolTipText(Bundle.HINT_ProjectEditorTopComponent());
        //putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);

        //FOR TEST
        //DummySuplier.loadDummyProject();
        
        if (projectCentral.getProfile() == null) {
            this.setEnabled(false);
        } else {
            project = projectCentral.getProfile();
            jPanel1.add(new ProjectEditorMainPanel(project), BorderLayout.CENTER);

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        jPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1051, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
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
