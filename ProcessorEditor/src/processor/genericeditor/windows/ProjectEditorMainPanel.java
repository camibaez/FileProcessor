/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.windows;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.model.mxCell;
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
import com.datapro.nfp.core.file.Profile;
import com.datapro.nfp.core.file.ProjectCentral;
import com.datapro.nfp.core.graph.DecisionEdge;
import com.datapro.nfp.core.graph.DecisionGraph;
import com.datapro.nfp.core.graph.EndNode;
import com.datapro.nfp.core.graph.FailNode;
import com.datapro.nfp.core.graph.GraphNode;
import com.datapro.nfp.core.graph.StartNode;
import com.datapro.nfp.core.graph.actions.Action;
import com.datapro.nfp.core.graph.actions.ExecutableAction;
import com.datapro.nfp.core.graph.actions.ReplaceText;
import com.datapro.nfp.core.graph.conditions.Condition;
import com.datapro.nfp.core.graph.conditions.ExecutableCondition;
import com.datapro.nfp.core.graph.conditions.FileContent;
import com.datapro.nfp.core.graph.conditions.FilePattern;
import com.datapro.nfp.core.graph.conditions.TextContent;
import com.datapro.nfp.core.graph.serialization.GraphSerializer;
import processor.editor.windows.CodeEditorOpener;
import processor.genericeditor.windows.conditions.PanelFactory;
import com.datapro.nfp.profile.DIEmulator;

/**
 *
 * @author cbaez
 */
public class ProjectEditorMainPanel extends javax.swing.JPanel {
    ProjectCentral projectCentral = DIEmulator.getProjectCentral();
    Profile profile = null;
    ProjectsTree profileTree;
    HashMap<GraphNode, Object> nodesMap = new HashMap<>();
    mxGraph vGraph;

    NodePropertiesPanel nodePropertiesPanel;

    /**
     * Creates new form ProjectEditorMainPanel
     */
    public ProjectEditorMainPanel(Profile profile) {
        this.profile = profile;
        this.nodePropertiesPanel = new NodePropertiesPanel(profile, this);

        initComponents();
        //createTree();
        loadGraphEditor();
        jSplitPane3.setRightComponent(nodePropertiesPanel);
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

                profileTree.repaint();
            }
        });

        jPanel4.add(profileTree, BorderLayout.CENTER);
    }

    public void loadGraphEditor() {
        DecisionGraph graph = profile.getGraph();

        mxGraph vGraph = new mxGraph();
        Object parent = vGraph.getDefaultParent();
        vGraph.getModel().beginUpdate();
        try {
            nodesMap.clear();
            graph.vertexSet().forEach(v -> {
                //mxCell cell = new mxCell(v);
                String color = "";
                if (v.isActive()) {
                    color = v instanceof Condition ? "#a0a0ff" : "#a0ffa4";
                } else {
                    color = "#eeeeee";
                }

                Object insertVertex = vGraph.insertVertex(parent, v.getId(), v, 20, 20, 80, 30, "defaultVertex;fillColor=" + color);

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
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
                if (cell != null && cell.getValue() instanceof GraphNode) {
                    GraphNode node = (GraphNode) cell.getValue();
                    if (node instanceof EndNode || node instanceof FailNode) {
                        return;
                    }
                    nodePropertiesPanel.loadFields(profile.getGraph(), node);

                    int location = jSplitPane3.getDividerLocation();
                    if (node instanceof Condition) {
                        jSplitPane3.setLeftComponent(PanelFactory.generatePanel((Condition) node));
                       
                    }

                    if (node instanceof Action) {
                        jSplitPane3.setLeftComponent(PanelFactory.generatePanel((Action) node));
                    }
                    
                    jSplitPane3.setDividerLocation(location);
                }
            }
        });

        vGraph.refresh();
        
        jPanel5.removeAll();
        jPanel5.add(graphComponent, BorderLayout.CENTER);
        jPanel5.validate();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton13 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton8 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jButton5 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setLayout(new java.awt.BorderLayout());

        jPanel4.setMinimumSize(new java.awt.Dimension(300, 0));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar2.setRollover(true);

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
        jToolBar2.add(jSeparator2);

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
        jToolBar2.add(jSeparator4);

        jButton2.setText("R"); // NOI18N
        jButton2.setToolTipText("R"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton2);

        jPanel4.add(jToolBar2, java.awt.BorderLayout.WEST);

        jPanel5.setLayout(new java.awt.BorderLayout());
        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel4);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane3.setDividerLocation(300);

        jPanel7.setLayout(new java.awt.BorderLayout());
        jSplitPane3.setLeftComponent(jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 204, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 558, Short.MAX_VALUE)
        );

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
        new CodeEditorOpener().openEditor(projectCentral.getProfileFile());
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        loadGraphEditor();


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        FilePattern filePattern = new FilePattern("");
        profile.addNode(filePattern);
        //profileTree.reloadData(profile);
        loadGraphEditor();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

        profile.addNode(new FileContent());
        //profileTree.reloadData(profile);
        loadGraphEditor();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        profile.addNode(new TextContent());
        //profileTree.reloadData(profile);
        loadGraphEditor();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        profile.addNode(new ReplaceText("", "", 0));
        //profileTree.reloadData(profile);
        loadGraphEditor();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        DecisionGraph graph = profile.getGraph();
        System.out.println(new GraphSerializer().exportGraph(graph));
        //System.out.println(profile.getGraphBuilder().export(profile.getGraph()));
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        profile.addNode(new ExecutableAction());
        //profileTree.reloadData(profile);
        loadGraphEditor();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        profile.addNode(new ExecutableCondition());
        //profileTree.reloadData(profile);
        loadGraphEditor();
    }//GEN-LAST:event_jButton13ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
}
