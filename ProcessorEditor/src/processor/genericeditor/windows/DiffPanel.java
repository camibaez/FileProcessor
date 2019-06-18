/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.windows;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import javax.swing.SwingUtilities;
import org.netbeans.api.diff.Diff;
import org.netbeans.api.diff.DiffView;
import org.netbeans.api.diff.StreamSource;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import processor.core.file.Profile;

/**
 *
 * @author cbaez
 */
public class DiffPanel extends javax.swing.JPanel {
    Profile profile;
    TopComponent diffTopComponent = null;
    DiffView openendDiff = null;

    /**
     * Creates new form DiffPanel
     */
    public DiffPanel(Profile profile) {
        this.profile = profile;

        initComponents();

    }

    public void loadDiffComponent(Path p) {
        try {
            StreamSource original = StreamSource.createSource("Original", "Original", "text/html", p.toFile());
            String processResult = (String) profile.getFileProcessor().processFile(p.toFile()).getResult();
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
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        previewFilePanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        previewFilePanel.setLayout(new java.awt.BorderLayout());
        add(previewFilePanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel previewFilePanel;
    // End of variables declaration//GEN-END:variables
}
