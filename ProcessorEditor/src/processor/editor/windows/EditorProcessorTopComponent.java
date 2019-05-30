/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.windows;

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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
import processor.editor.windows.Bundle;
import processor.editor.ProjectCentral;

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
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int lastSize = 0;
                boolean done = false;
                while (!done) {
                    done = profile.getFileMatcher().isDone();
                    int size = profile.getFileCentral().getMatchedFiles().size();
                    if (size > lastSize) {
                        loadMatchedFiles();
                    }
                    if (!done) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                    }
                    
                }
                
            }
        });
        
    }
    
    public void loadMatchedFiles() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        profile.getFileCentral().getMatchedFiles().forEach(p -> {
            List<Cleaner> assignedCleaners = profile.getFileProcessor().getAssignedCleaners(p);
            tableModel.addRow(new Object[]{p, assignedCleaners});
        });
        jLabel1.setText(profile.getFileCentral().getMatchedFiles().size() + " files matched");
        
        
    }
    
    public void previewProcessing(Path path) {
        if (profile.getFileProcessor() == null) {
            System.out.println("You must init the FileProcessor first.");
            return;
        }
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
                            
                        } else {
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
        previewFilePanel = new javax.swing.JPanel();
        matchedFilesPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jButton1 = new javax.swing.JButton();

        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        previewFilePanel.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setTopComponent(previewFilePanel);

        matchedFilesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(EditorProcessorTopComponent.class, "EditorProcessorTopComponent.matchedFilesPanel.border.title"))); // NOI18N
        matchedFilesPanel.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "File", "Cleaners"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        matchedFilesPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(EditorProcessorTopComponent.class, "EditorProcessorTopComponent.jLabel1.text")); // NOI18N
        matchedFilesPanel.add(jLabel1, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setBottomComponent(matchedFilesPanel);

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadMatchedFiles();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        refreshProcessorView();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getSelectedRow();
        
        if (row > -1 && evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Path p = (Path) model.getValueAt(row, 0);
            try {
                String result = profile.getFileProcessor().processFile(p);
                profile.getFileProcessor().saveFile(result, p);
                model.removeRow(row);
                if (model.getRowCount() == 0) {
                    return;
                }
                if (row <= model.getRowCount() - 1) {
                    jTable1.getSelectionModel().setSelectionInterval(0, row);
                    
                } else {
                    jTable1.getSelectionModel().setSelectionInterval(0, model.getRowCount() - 1);
                }
                refreshProcessorView();
            } catch (IOException ex) {
                Logger.getLogger(EditorProcessorTopComponent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTable1KeyReleased
    
    private void refreshProcessorView() {
        int row = jTable1.getSelectedRow();
        if(row > -1){
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Path path = (Path) model.getValueAt(row, 0);
            previewProcessing(path);
        }
        
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel matchedFilesPanel;
    private javax.swing.JPanel previewFilePanel;
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
