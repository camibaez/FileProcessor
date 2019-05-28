/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import org.netbeans.api.diff.Diff;
import org.netbeans.api.diff.DiffView;
import org.netbeans.api.diff.StreamSource;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import processor.core.file.Cleaner;
import processor.core.file.Profile;

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

        profile = ProjectCentral.instance().getProject();

        //FOR TEST
        DummySuplier.loadMatchingFiles();
        loadMatchedFiles();
    }

    public void loadMatchedFiles() {
        DefaultListModel<Path> filesDataModel = new DefaultListModel<>();
        profile.getFileCentral().getMatchedFiles().forEach(p -> filesDataModel.addElement(p));
        jLabel1.setText(profile.getFileCentral().getMatchedFiles().size() + " files matched");
        matchedFilesList.setModel(filesDataModel);

    }

    public void previewProcessing(Path path) {
        if (profile.getFileProcessor() == null) {
            System.out.println("You must init the FileProcessor first.");
            return;
        }

        DefaultListModel<Cleaner> listModel = new DefaultListModel<>();
        List<Cleaner> assignedCleaners = profile.getFileProcessor().getAssignedCleaners(path);
        assignedCleaners.forEach(c -> {
            listModel.addElement(c);

        });
        processedFilesList.setModel(listModel);
        loadDiffComponent(path);

    }

    public void loadDiffComponent(Path p) {
        try {
            StreamSource original = StreamSource.createSource("Original", "Original", "text/html", p.toFile());
            String processResult = profile.getFileProcessor().processFile(p);
            StreamSource processed = StreamSource.createSource("Processed", "Processed", "text/html", new StringReader(processResult));
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (diffTopComponent == null) {
                            diffTopComponent = new TopComponent();
                            diffTopComponent.setLayout(new BorderLayout());
                            
                            
                        }else{
                            diffTopComponent.remove(openendDiff.getComponent());
                        }
                        
                        openendDiff = Diff.getDefault().createDiff(original, processed);
                        diffTopComponent.add(openendDiff.getComponent(), BorderLayout.CENTER);
                        diffTopComponent.requestActive();
                        
                        
                        previewFilePanel.remove(diffTopComponent);
                        previewFilePanel.add(diffTopComponent, BorderLayout.CENTER);
                        previewFilePanel.validate();
                        previewFilePanel.repaint();
                        
                        
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            });
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
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
        jSplitPane1 = new javax.swing.JSplitPane();
        matchedFilesPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        matchedFilesList = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        processedFilesPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        processedFilesList = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        previewFilePanel = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jButton1 = new javax.swing.JButton();

        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        matchedFilesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(EditorProcessorTopComponent.class, "EditorProcessorTopComponent.matchedFilesPanel.border.title"))); // NOI18N
        matchedFilesPanel.setLayout(new java.awt.BorderLayout());

        matchedFilesList.setCellRenderer(new DefaultListCellRenderer(){

            public Component getListCellRendererComponent(JList<? extends Path> list, Path p, int index,
                boolean isSelected, boolean cellHasFocus) {
                this.setText(p.getFileName().toString());
                return this;
            }
        }
    );
    matchedFilesList.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            matchedFilesListMouseClicked(evt);
        }
    });
    matchedFilesList.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            matchedFilesListKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            matchedFilesListKeyTyped(evt);
        }
    });
    matchedFilesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
        public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
            matchedFilesListValueChanged(evt);
        }
    });
    jScrollPane2.setViewportView(matchedFilesList);

    matchedFilesPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

    jPanel3.setLayout(new java.awt.BorderLayout());

    org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(EditorProcessorTopComponent.class, "EditorProcessorTopComponent.jLabel1.text")); // NOI18N
    jPanel3.add(jLabel1, java.awt.BorderLayout.CENTER);

    matchedFilesPanel.add(jPanel3, java.awt.BorderLayout.PAGE_END);

    jSplitPane1.setTopComponent(matchedFilesPanel);

    processedFilesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(EditorProcessorTopComponent.class, "EditorProcessorTopComponent.processedFilesPanel.border.title"))); // NOI18N
    processedFilesPanel.setLayout(new java.awt.BorderLayout());

    processedFilesList.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            processedFilesListMouseClicked(evt);
        }
    });
    processedFilesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
        public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
            processedFilesListValueChanged(evt);
        }
    });
    jScrollPane3.setViewportView(processedFilesList);

    processedFilesPanel.add(jScrollPane3, java.awt.BorderLayout.CENTER);

    jPanel4.setLayout(new java.awt.BorderLayout());

    org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(EditorProcessorTopComponent.class, "EditorProcessorTopComponent.jLabel2.text")); // NOI18N
    jPanel4.add(jLabel2, java.awt.BorderLayout.CENTER);

    processedFilesPanel.add(jPanel4, java.awt.BorderLayout.PAGE_END);

    jSplitPane2.setLeftComponent(processedFilesPanel);

    previewFilePanel.setLayout(new java.awt.BorderLayout());
    jSplitPane2.setRightComponent(previewFilePanel);

    jSplitPane1.setRightComponent(jSplitPane2);

    jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

    jToolBar1.setFloatable(false);
    jToolBar1.setRollover(true);
    jToolBar1.add(filler1);

    org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(EditorProcessorTopComponent.class, "EditorProcessorTopComponent.jButton1.text")); // NOI18N
    jButton1.setFocusable(false);
    jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton1);

    jPanel1.add(jToolBar1, java.awt.BorderLayout.SOUTH);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
    );
    }// </editor-fold>//GEN-END:initComponents

    private void matchedFilesListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_matchedFilesListKeyReleased
        if (matchedFilesList.getModel().getSize() < 1) {
            return;
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Path p = (Path) matchedFilesList.getSelectedValue();
            try {
                String result = profile.getFileProcessor().processFile(p);
                profile.getFileProcessor().saveFile(result, p);
                int selectedIndex = matchedFilesList.getSelectedIndex();
                DefaultListModel model = (DefaultListModel) matchedFilesList.getModel();
                model.remove(selectedIndex);
                if (model.isEmpty()) {
                    return;
                }
                if (selectedIndex <= model.size() - 1) {
                    matchedFilesList.setSelectedIndex(selectedIndex);
                } else {
                    matchedFilesList.setSelectedIndex(model.getSize() - 1);
                }
                
                refreshProcessorView();
            } catch (IOException ex) {
                Logger.getLogger(EditorProcessorTopComponent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_matchedFilesListKeyReleased


    private void matchedFilesListKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_matchedFilesListKeyTyped

    }//GEN-LAST:event_matchedFilesListKeyTyped

    private void matchedFilesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_matchedFilesListValueChanged


    }//GEN-LAST:event_matchedFilesListValueChanged

    private void processedFilesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_processedFilesListMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_processedFilesListMouseClicked

    private void processedFilesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_processedFilesListValueChanged
        Cleaner cleaner = (Cleaner) processedFilesList.getSelectedValue();
        processedFilesList.setToolTipText(cleaner.getDescription());
    }//GEN-LAST:event_processedFilesListValueChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadMatchedFiles();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void matchedFilesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_matchedFilesListMouseClicked
        refreshProcessorView();
    }//GEN-LAST:event_matchedFilesListMouseClicked
    
    private void refreshProcessorView(){
        Path path = (Path) matchedFilesList.getSelectedValue();
        if (path != null) {
            previewProcessing(path);
        }
        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JList matchedFilesList;
    private javax.swing.JPanel matchedFilesPanel;
    private javax.swing.JPanel previewFilePanel;
    private javax.swing.JList processedFilesList;
    private javax.swing.JPanel processedFilesPanel;
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
