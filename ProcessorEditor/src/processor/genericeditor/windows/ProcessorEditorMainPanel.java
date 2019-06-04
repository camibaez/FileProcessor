/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.windows;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


import processor.core.file.Profile;

public final class ProcessorEditorMainPanel extends JPanel {

    Profile profile;
    DiffPanel diffPanel;

    public ProcessorEditorMainPanel(Profile profile) {
        initComponents();
        this.profile = profile;
        diffPanel = new DiffPanel(profile);
        diffContainerPanel.add(diffPanel, BorderLayout.CENTER);
        startFillingThread();
    }

    private void startFillingThread() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if(profile.getFileMatcher().isDone()){
                            fillFilesTable();
                            break;
                        }else{
                           Thread.sleep(1000); 
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void fillFilesTable() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        profile.getFileCentral().getMatchedFiles().forEach(p -> {
            //List<Cleaner> assignedCleaners = profile.getFileProcessor().getAssignedCleaners(p);
            List<ActionCluster> affectingCleaners = profile.getFileProcessor().getAffectingCleaners(p);
            if(!affectingCleaners.isEmpty()){
                tableModel.addRow(new Object[]{p, affectingCleaners});
            }
        });
        jLabel1.setText(tableModel.getRowCount() + " files matched");
    }

    private void refreshProcessorView() {
        int row = jTable1.getSelectedRow();
        if (row > -1) {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Path path = (Path) model.getValueAt(row, 0);
            if (profile.getFileProcessor() == null) {
                System.out.println("You must init the FileProcessor first.");
                return;
            }
            diffPanel.loadDiffComponent(path);

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
        diffContainerPanel = new javax.swing.JPanel();
        matchedFilesPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jButton2 = new javax.swing.JButton();

        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        diffContainerPanel.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setTopComponent(diffContainerPanel);

        matchedFilesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Files Result"));
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

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, "Files processed:"); // NOI18N
        matchedFilesPanel.add(jLabel1, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setBottomComponent(matchedFilesPanel);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.add(filler1);

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(ProcessorEditorMainPanel.class, "ProcessorEditorMainPanel.jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

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
                Logger.getLogger(ProcessorEditorMainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTable1KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         fillFilesTable();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel diffContainerPanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel matchedFilesPanel;
    // End of variables declaration//GEN-END:variables

}
