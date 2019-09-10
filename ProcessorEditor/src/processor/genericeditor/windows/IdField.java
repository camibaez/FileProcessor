/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.windows;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import processor.core.graph.GraphNode;

/**
 *
 * @author cbaez
 */
public class IdField extends JTextField{
    public IdField(GraphNode node){
        super();
        linkToNode(node);
    }
    
    private void linkToNode(GraphNode node){
        this.setText(node.getId());
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
                node.setId(IdField.this.getText());
            }
        });
    }
}
