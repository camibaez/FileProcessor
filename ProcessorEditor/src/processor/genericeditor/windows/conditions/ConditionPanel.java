/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.windows.conditions;

import java.awt.BorderLayout;
import com.datapro.nfp.core.graph.conditions.Condition;

/**
 *
 * @author cbaez
 */
public abstract class ConditionPanel extends javax.swing.JPanel {

    Condition condition;

    /**
     * Creates new form ConditionPanel
     */
    public ConditionPanel(Condition c) {
        condition = c;

        initComponents();

    }
    public abstract void loadConditionData();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
