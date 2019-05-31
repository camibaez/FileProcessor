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
import processor.core.file.FilePrototype;
import processor.core.file.Profile;

/**
 *
 * @author cbaez
 */
public class ProjectsTree extends JTree {
    public static String PROTOTYPES_TEXT = "Prototypes";
    public static String CLEANERS_TEXT = "Cleaners";
    
    
    DefaultMutableTreeNode prototypesChildren;
    DefaultMutableTreeNode cleanersChildren;

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

        //Filling prototypes
        prototypesChildren = new DefaultMutableTreeNode(PROTOTYPES_TEXT);

        p.getPrototypes().forEach(proto -> {
            DefaultMutableTreeNode protoNode = new DefaultMutableTreeNode(proto);
            proto.getExpressions().forEach(e -> {
                protoNode.add(new DefaultMutableTreeNode(e));
            });
            prototypesChildren.add(new DefaultMutableTreeNode(proto));
            //model.insertNodeInto(new DefaultMutableTreeNode(proto), prototypesChildren, prototypesChildren.getChildCount());
        });
        model.insertNodeInto(prototypesChildren, profileChild, 0);

        //Filling Cleaners
        cleanersChildren = new DefaultMutableTreeNode(CLEANERS_TEXT);
        p.getCleaners().forEach(c -> {
            DefaultMutableTreeNode cleanerNode = new DefaultMutableTreeNode(c);
            if (c.getPrototype() != null) {
                DefaultMutableTreeNode prototypeNodeParent = new DefaultMutableTreeNode("Prototype");
                prototypeNodeParent.add(new DefaultMutableTreeNode(c.getPrototype()));
                cleanerNode.add(prototypeNodeParent);
            }

            c.getRules().forEach(r -> {
                DefaultMutableTreeNode ruleNode = new DefaultMutableTreeNode(r);
                cleanerNode.add(ruleNode);
            });

            cleanersChildren.add(cleanerNode);
        });
        model.insertNodeInto(cleanersChildren, profileChild, 1);
        model.reload();
        expandPath(new TreePath(prototypesChildren.getPath()));
    }

    public void reloadData(Profile p) {
        loadData(p);
    }
    
    public boolean isPrototypeRootSelected(){
        return rootSelected() != null && rootSelected().equals(PROTOTYPES_TEXT);
    }
    
    public boolean isCleanerRootSelected(){
        return rootSelected() != null && rootSelected().equals(CLEANERS_TEXT);
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
