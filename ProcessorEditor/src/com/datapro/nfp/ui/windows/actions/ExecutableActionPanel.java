/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.ui.windows.actions;

import javax.swing.text.EditorKit;
import org.openide.text.CloneableEditorSupport;
import com.datapro.nfp.core.graph.actions.ExecutableAction;

/**
 *
 * @author cbaez
 */
public class ExecutableActionPanel extends javax.swing.JPanel {


    protected ExecutableAction action;
    /**
     * Creates new form ExecutableAction
     */
    public ExecutableActionPanel(ExecutableAction action) {
        this.action =  action;
        initComponents();
        
        //EditorKit kit = CloneableEditorSupport.getEditorKit("text/javascript");
        //jEditorPane1.setEditorKit(kit);
        jEditorPane1.setText(this.action.getCode());
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Executable Action"));
        setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel4.setText("function (exchange){");
        jPanel3.add(jLabel4, java.awt.BorderLayout.NORTH);

        jLabel5.setText("}");

        jButton1.setText("Compile");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(0, 328, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(0, 68, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, java.awt.BorderLayout.SOUTH);

        jEditorPane1.setContentType("text/javascript"); // NOI18N
        jEditorPane1.setFont(new java.awt.Font("Consolas", 0, 11)); // NOI18N
        jEditorPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jEditorPane1KeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jEditorPane1);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jEditorPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jEditorPane1KeyReleased
         action.setCode(jEditorPane1.getText());
         jButton1.setEnabled(true);
    }//GEN-LAST:event_jEditorPane1KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       int result = action.compile();
       if(result == 0)
        jButton1.setEnabled(false);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
