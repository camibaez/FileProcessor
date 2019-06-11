/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.windows;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import processor.core.graph.conditions.Condition;
import processor.core.file.Profile;
import processor.core.graph.DecisionGraph;
import processor.core.graph.actions.Action;
import processor.core.graph.actions.ReplaceText;
import processor.core.graph.conditions.FileContent;
import processor.core.graph.conditions.FilePattern;
import processor.core.graph.conditions.TextContent;
import processor.core.lineal.ComplexNode;
import processor.editor.windows.CodeEditorOpener;
import processor.core.file.ProjectCentral;
import processor.core.graph.GraphNode;
import processor.genericeditor.windows.conditions.PanelFactory;

/**
 *
 * @author cbaez
 */
public class ProjectEditorMainPanel extends javax.swing.JPanel {

    Profile profile = null;
    ProjectsTree profileTree;

    /**
     * Creates new form ProjectEditorMainPanel
     */
    public ProjectEditorMainPanel(Profile profile) {
        this.profile = profile;
        initComponents();
        createTree();

    }

    protected void createTree() {
        profileTree = new ProjectsTree(profile);
        profileTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) profileTree.getLastSelectedPathComponent();
                if (treeNode == null) {
                    return;
                }
                if (!(treeNode.getUserObject() instanceof ComplexNode)) {
                    return;
                }
                ComplexNode node = (ComplexNode) treeNode.getUserObject();
                jSplitPane3.setLeftComponent(new JPanel());
                jSplitPane3.setRightComponent(new JPanel());

                if (node.getCondition() != null) {
                    jSplitPane3.setLeftComponent(PanelFactory.generatePanel(node.getCondition()));
                }

                if (node.getAction() != null) {
                    jSplitPane3.setRightComponent(new ProjectEditorActionPanel(profile, node.getAction()));
                }

                jComboBox1.setSelectedIndex(node.getType());
            }
        });

        jPanel4.add(profileTree, BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton6 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jToolBar1 = new javax.swing.JToolBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jButton5 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jPanel4.setMinimumSize(new java.awt.Dimension(300, 0));
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new java.awt.BorderLayout());

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar2.setRollover(true);
        jToolBar2.setName("jToolBar2"); // NOI18N

        jButton6.setText("+FPT"); // NOI18N
        jButton6.setToolTipText("+FPT"); // NOI18N
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setName("jButton6"); // NOI18N
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton6);

        jButton9.setText("+FCT"); // NOI18N
        jButton9.setToolTipText("+FCT"); // NOI18N
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setName("jButton9"); // NOI18N
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton9);

        jButton10.setText("+TCC"); // NOI18N
        jButton10.setToolTipText("+TCC"); // NOI18N
        jButton10.setFocusable(false);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setName("jButton10"); // NOI18N
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton10);

        jButton8.setText("+A"); // NOI18N
        jButton8.setToolTipText("+A"); // NOI18N
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setName("jButton8"); // NOI18N
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton8);

        jButton7.setText("-"); // NOI18N
        jButton7.setToolTipText("-"); // NOI18N
        jButton7.setName("jButton7"); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton7);

        jButton3.setText("Up"); // NOI18N
        jButton3.setToolTipText("Up"); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton3);

        jButton4.setText("Down"); // NOI18N
        jButton4.setToolTipText("Down"); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton4);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jToolBar2.add(jSeparator1);

        jButton2.setText("R"); // NOI18N
        jButton2.setToolTipText("R"); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton2);

        jPanel4.add(jToolBar2, java.awt.BorderLayout.WEST);

        jSplitPane1.setLeftComponent(jPanel4);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane3.setDividerLocation(300);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setName("jSplitPane3"); // NOI18N

        jPanel7.setName("jPanel7"); // NOI18N
        jPanel7.setLayout(new java.awt.BorderLayout());
        jSplitPane3.setLeftComponent(jPanel7);

        jPanel8.setName("jPanel8"); // NOI18N
        jPanel8.setLayout(new java.awt.BorderLayout());
        jSplitPane3.setRightComponent(jPanel8);

        jPanel1.add(jSplitPane3, java.awt.BorderLayout.CENTER);

        jPanel2.setName("jPanel2"); // NOI18N

        jLabel1.setText("Type"); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel2.add(jLabel1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0 - Omissible", "1 - Not Omissible" }));
        jComboBox1.setName("jComboBox1"); // NOI18N
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jPanel2.add(jComboBox1);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jSplitPane1.setRightComponent(jPanel1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        filler1.setName("filler1"); // NOI18N
        jToolBar1.add(filler1);

        jButton5.setText("View Code"); // NOI18N
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setName("jButton5"); // NOI18N
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton11.setText("View Graph"); // NOI18N
        jButton11.setFocusable(false);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.setName("jButton11"); // NOI18N
        jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton11);

        add(jToolBar1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new CodeEditorOpener().openEditor(ProjectCentral.instance().getProfileFile());
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int showConfirmDialog = JOptionPane.showConfirmDialog(this, "Are you shure you want to delete this prototype?");
        if (showConfirmDialog == JOptionPane.YES_OPTION) {

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) profileTree.getLastSelectedPathComponent();
            GraphNode obj = (GraphNode) node.getUserObject();
            jSplitPane3.setLeftComponent(new JPanel());
            jSplitPane3.setRightComponent(new JPanel());

            profile.removeNode(obj);
            profileTree.reloadData(profile);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        profileTree.reloadData(profile);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) profileTree.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        ComplexNode obj = (ComplexNode) node.getUserObject();
        obj.setCondition(new FilePattern(""));
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) profileTree.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        ComplexNode obj = (ComplexNode) node.getUserObject();
        obj.setCondition(new FileContent());
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) profileTree.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        ComplexNode obj = (ComplexNode) node.getUserObject();
        obj.setCondition(new TextContent());
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) profileTree.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        ComplexNode obj = (ComplexNode) node.getUserObject();
        obj.setAction(new ReplaceText("", "", 0));
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked


    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        String item = (String) jComboBox1.getSelectedItem();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) profileTree.getLastSelectedPathComponent();
        if (node == null || item == null) {
            return;
        }
        if (node.getUserObject() instanceof ComplexNode) {
            ComplexNode complexNode = (ComplexNode) node.getUserObject();
            if (item.startsWith("0")) {
                complexNode.setType(0);
            } else {
                complexNode.setType(1);
            }
            profile.reloadGraph();
        }

    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        //DecisionGraph dummyGraph = profile.getGraphBuilder().buildDummyGraph();
        //profile.getGraphBuilder().export(dummyGraph);
        
        //System.out.println(profile.getGraphBuilder().export(profile.getGraph()));
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) profileTree.getLastSelectedPathComponent();
        GraphNode obj = (GraphNode) node.getUserObject();
        profile.moveNode(obj, -1);
        profileTree.reloadData(profile);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       DefaultMutableTreeNode node = (DefaultMutableTreeNode) profileTree.getLastSelectedPathComponent();
        GraphNode obj = (GraphNode) node.getUserObject();
        profile.moveNode(obj, 1);
        profileTree.reloadData(profile);
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
}
