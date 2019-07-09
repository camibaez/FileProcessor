/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.windows;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import processor.core.file.Profile;
import processor.core.file.ProjectCentral;
import processor.core.graph.DecisionEdge;
import processor.core.graph.DecisionGraph;
import processor.core.graph.GraphNode;
import processor.core.graph.actions.Action;
import processor.core.graph.actions.ExecutableAction;
import processor.core.graph.actions.ReplaceText;
import processor.core.graph.conditions.Condition;
import processor.core.graph.conditions.ExecutableCondition;
import processor.core.graph.conditions.FileContent;
import processor.core.graph.conditions.FilePattern;
import processor.core.graph.conditions.TextContent;
import processor.core.graph.serialization.GraphSerializer;
import processor.editor.windows.CodeEditorOpener;
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
        //createTree();
        reloadTable();
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
                if (!(treeNode.getUserObject() instanceof GraphNode)) {
                    return;
                }
                GraphNode node = (GraphNode) treeNode.getUserObject();

                if (node instanceof Condition) {
                    jSplitPane3.setLeftComponent(PanelFactory.generatePanel((Condition) node));
                }

                if (node instanceof Action) {
                    jSplitPane3.setLeftComponent(PanelFactory.generatePanel((Action) node));
                }

                jTextArea1.setText(new GraphSerializer().exportGraph(profile.getGraph()));
                profileTree.repaint();
            }
        });

        jPanel4.add(profileTree, BorderLayout.CENTER);
    }

    protected void reloadTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        profile.getNodes().forEach(n -> {
            Set<DecisionEdge> incomingEdgesOf = profile.getGraph().incomingEdgesOf(n);
            List<GraphNode> pointingGraphs = new LinkedList<>();
            incomingEdgesOf.forEach(e -> {
                pointingGraphs.add((GraphNode) profile.getGraph().getEdgeSource(e));
            });
            GraphNode trueTarget = profile.getGraph().getDecisionTargetOf(n, true);
            GraphNode falseTarget = profile.getGraph().getDecisionTargetOf(n, false);

            String type = n.getClass().getSimpleName();
            model.addRow(new Object[]{n.isActive(), n, type, pointingGraphs, trueTarget, falseTarget});
        });

    }

    public void loadGraphEditor() {
        DecisionGraph graph = profile.getGraph();

        mxGraph vGraph = new mxGraph();
        Object parent = vGraph.getDefaultParent();
        vGraph.getModel().beginUpdate();
        try {
            HashMap<GraphNode, Object> nodesMap = new HashMap<>();
            graph.vertexSet().forEach(v -> {
                //mxCell cell = new mxCell(v);
                Object insertVertex = vGraph.insertVertex(parent, v.getId(), v.toString(), 20, 20, 80, 30);
                nodesMap.put(v, insertVertex);
            });

            graph.edgeSet().forEach(e -> {
                GraphNode edgeSource = graph.getEdgeSource(e);
                GraphNode edgeTarget = graph.getEdgeTarget(e);
                vGraph.insertEdge(parent, null, e.getSign() + "", nodesMap.get(edgeSource), nodesMap.get(edgeTarget));
            });

            /*
			Object v1 = vGraph.insertVertex(parent, null, "Hello", 20, 20, 80,
					30);
			Object v2 = vGraph.insertVertex(parent, null, "World!", 240, 150,
					80, 30);
			vGraph.insertEdge(parent, null, "Edge", v1, v2);
             */
        } finally {
            vGraph.getModel().endUpdate();
        }
        vGraph.orderCells(true);
        mxGraphLayout layout = new mxHierarchicalLayout(vGraph);
        layout.execute(parent);
        mxGraphComponent graphComponent = new mxGraphComponent(vGraph);

        //jSplitPane3.remove(2);
        //jSplitPane3.setRightComponent(graphComponent);
        int dividerLocation = jSplitPane2.getDividerLocation();
        jSplitPane2.setBottomComponent(graphComponent);
        jSplitPane2.setDividerLocation(dividerLocation);
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
        jButton1 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jButton5 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel4.setMinimumSize(new java.awt.Dimension(300, 0));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar2.setRollover(true);

        jButton1.setText("MF");
        jButton1.setToolTipText("Mark first");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton1);

        jButton13.setText("+EC");
        jButton13.setFocusable(false);
        jButton13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton13);

        jButton6.setText("+FPT"); // NOI18N
        jButton6.setToolTipText("+FPT"); // NOI18N
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
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
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton8);

        jButton12.setText("+EA");
        jButton12.setFocusable(false);
        jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton12);

        jButton7.setText("-"); // NOI18N
        jButton7.setToolTipText("-"); // NOI18N
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
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton4);
        jToolBar2.add(jSeparator1);

        jButton2.setText("R"); // NOI18N
        jButton2.setToolTipText("R"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton2);

        jPanel4.add(jToolBar2, java.awt.BorderLayout.WEST);

        jSplitPane2.setDividerLocation(300);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new NodesTableModel());
        jTable1.setColumnSelectionAllowed(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jSplitPane2.setLeftComponent(jPanel2);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane2.setRightComponent(jPanel3);

        jPanel4.add(jSplitPane2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel4);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane3.setDividerLocation(300);

        jPanel7.setLayout(new java.awt.BorderLayout());
        jSplitPane3.setLeftComponent(jPanel7);

        jPanel8.setLayout(new java.awt.BorderLayout());
        jSplitPane3.setRightComponent(jPanel8);

        jPanel1.add(jSplitPane3, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.add(filler1);

        jButton5.setText("View Code"); // NOI18N
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
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

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            int row = jTable1.getSelectedRow();
            if (row == -1) {
                return;
            }
            GraphNode node = (GraphNode) model.getValueAt(row, 1);
            jSplitPane3.setLeftComponent(new JPanel());

            profile.removeNode(node);
            //profileTree.reloadData(profile);
            reloadTable();
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        reloadTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        FilePattern filePattern = new FilePattern("");
        profile.addNode(filePattern);
        //profileTree.reloadData(profile);
        reloadTable();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

        profile.addNode(new FileContent());
        //profileTree.reloadData(profile);
        reloadTable();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        profile.addNode(new TextContent());
        //profileTree.reloadData(profile);
        reloadTable();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        profile.addNode(new ReplaceText("", "", 0));
        //profileTree.reloadData(profile);
        reloadTable();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        DecisionGraph graph = profile.getGraph();
        System.out.println(new GraphSerializer().exportGraph(graph));
        //System.out.println(profile.getGraphBuilder().export(profile.getGraph()));
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            return;
        }
        GraphNode node = (GraphNode) model.getValueAt(row, 1);
        profile.moveNode(node, -1);
        //profileTree.reloadData(profile);
        reloadTable();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            return;
        }
        GraphNode node = (GraphNode) model.getValueAt(row, 1);
        profile.moveNode(node, 1);
        //profileTree.reloadData(profile);
        reloadTable();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            return;
        }
        GraphNode node = (GraphNode) model.getValueAt(row, 1);
        profile.getGraph().masrkAsInitial(node);
        reloadTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        profile.addNode(new ExecutableAction());
        //profileTree.reloadData(profile);
        reloadTable();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        profile.addNode(new ExecutableCondition());
        //profileTree.reloadData(profile);
        reloadTable();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            return;
        }
        GraphNode node = (GraphNode) model.getValueAt(row, 1);

        if (node instanceof Condition) {
            jSplitPane3.setLeftComponent(PanelFactory.generatePanel((Condition) node));
        }

        if (node instanceof Action) {
            jSplitPane3.setLeftComponent(PanelFactory.generatePanel((Action) node));
        }

        jTextArea1.setText(new GraphSerializer().exportGraph(profile.getGraph()));
        loadGraphEditor();

    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
}
