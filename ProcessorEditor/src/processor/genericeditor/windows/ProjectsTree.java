/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.windows;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import processor.core.file.Profile;

/**
 *
 * @author cbaez
 */
public class ProjectsTree extends JTree {
    public static String CONDITIONS_TEXT = "Condtitions";
    public static String ACTIONS_TEXT = "Actions";
    public static String NODES_TEXT = "Nodes";
    
    DefaultMutableTreeNode conditionsChildren;
    DefaultMutableTreeNode actionsChildren;
    DefaultMutableTreeNode nodesChildren;

    public ProjectsTree(Profile p) {
        loadData(p);
    }

    protected void loadData(Profile p) {
        setRootVisible(false);
        ((DefaultMutableTreeNode) getModel().getRoot()).removeAllChildren();
        DefaultTreeModel model = (DefaultTreeModel) getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        DefaultMutableTreeNode profileChild = new DefaultMutableTreeNode(p);
        model.insertNodeInto(profileChild, root, 0);

        
        //nodesChildren = new DefaultMutableTreeNode(NODES_TEXT);
        p.getNodes().forEach(c -> {
            DefaultMutableTreeNode complexNode = new DefaultMutableTreeNode(c);
            profileChild.add(complexNode);
        });
        
        /*
        //Filling conditions
        conditionsChildren = new DefaultMutableTreeNode(CONDITIONS_TEXT);
        p.getConditions().forEach(c -> {
            DefaultMutableTreeNode protoNode = new DefaultMutableTreeNode(c);
            conditionsChildren.add(protoNode);
        });
        model.insertNodeInto(conditionsChildren, profileChild, 0);

        //Filling actions
        actionsChildren = new DefaultMutableTreeNode(ACTIONS_TEXT);
        p.getActions().forEach(c -> {
            DefaultMutableTreeNode cleanerNode = new DefaultMutableTreeNode(c);
            actionsChildren.add(cleanerNode);
        });
        model.insertNodeInto(actionsChildren, profileChild, 1);
        */
        
        //model.insertNodeInto(nodesChildren, profileChild, 0);
        model.reload();
        expandPath(new TreePath(profileChild.getPath()));
    }

    public void reloadData(Profile p) {
        loadData(p);
    }
    
    public boolean isPrototypeRootSelected(){
        return rootSelected() != null && rootSelected().equals(CONDITIONS_TEXT);
    }
    
    public boolean isCleanerRootSelected(){
        return rootSelected() != null && rootSelected().equals(ACTIONS_TEXT);
    }
    
    public String rootSelected(){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();
        if (node == null) {
            return null;
        }
        Object obj = node.getUserObject();
        if(obj instanceof String){
            return (String) obj;
        }
        return null;
    }
    
}
