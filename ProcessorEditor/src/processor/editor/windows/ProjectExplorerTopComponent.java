/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.windows;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import processor.core.file.Profile;
import processor.editor.ProjectCentral;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//processor.editor.windows//ProjectExplorer//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ProjectExplorerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@ActionID(category = "Window", id = "processor.editor.windows.ProjectExplorerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ProjectExplorerAction",
        preferredID = "ProjectExplorerTopComponent"
)
@Messages({
    "CTL_ProjectExplorerAction=Project Explorer",
    "CTL_ProjectExplorerTopComponent=Project Explorer",
    "HINT_ProjectExplorerTopComponent=This is a ProjectExplorer window"
})
public final class ProjectExplorerTopComponent extends TopComponent {
    
    
    public ProjectExplorerTopComponent() {
        initComponents();
        setName(Bundle.CTL_ProjectExplorerTopComponent());
        setToolTipText(Bundle.HINT_ProjectExplorerTopComponent());
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);
        
        projectsTree.setRootVisible(false);
        
        Profile profile = ProjectCentral.instance().getProfile();
        loadNodesData(profile);
        
    }
    
    
    public void loadNodesData(Profile p){
        DefaultTreeModel model = (DefaultTreeModel) projectsTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        
        DefaultMutableTreeNode profileChild = new DefaultMutableTreeNode(p);
        model.insertNodeInto(profileChild, root, 0);
        
        DefaultMutableTreeNode prototypesChildren =  new DefaultMutableTreeNode("Prototypes");
        p.getPrototypes().forEach(proto -> {
            model.insertNodeInto(new DefaultMutableTreeNode(proto), prototypesChildren, prototypesChildren.getChildCount());
        });
        model.insertNodeInto(prototypesChildren, profileChild, 0);
        
        DefaultMutableTreeNode cleanersChildren = new DefaultMutableTreeNode("Cleaners");
        p.getCleaners().forEach(c -> {
            model.insertNodeInto(new DefaultMutableTreeNode(c), cleanersChildren, cleanersChildren.getChildCount());
        });
        model.insertNodeInto(cleanersChildren, profileChild, 1);
        projectsTree.expandPath(new TreePath(prototypesChildren.getPath()));
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        projectsTree = new javax.swing.JTree();

        jPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        projectsTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(projectsTree);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree projectsTree;
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