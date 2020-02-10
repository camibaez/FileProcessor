/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.windows.conditions;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JPanel;
import org.openide.util.Exceptions;
import com.datapro.nfp.core.graph.GraphNode;
import com.datapro.nfp.core.graph.actions.Action;
import com.datapro.nfp.core.graph.actions.ExecutableAction;
import com.datapro.nfp.core.graph.actions.ReplaceText;
import com.datapro.nfp.core.graph.conditions.Condition;
import com.datapro.nfp.core.graph.conditions.ExecutableCondition;
import com.datapro.nfp.core.graph.conditions.FileContent;
import com.datapro.nfp.core.graph.conditions.FilePattern;
import com.datapro.nfp.core.graph.conditions.TextContent;
import processor.genericeditor.windows.ProjectEditorActionPanel;
import processor.genericeditor.windows.actions.ExecutableActionPanel;

/**
 *
 * @author cbaez
 */
public class PanelFactory {
    protected static Class[][] nodesMap = {
        {FilePattern.class, FilePatternPanel.class},
        {FileContent.class, FileContentPanel.class},
        {TextContent.class, TextContentPanel.class},
        {ExecutableCondition.class, ExecConditionPanel.class},
        
        {ReplaceText.class, ProjectEditorActionPanel.class},
        {ExecutableAction.class, ExecutableActionPanel.class}
    };
    
    
    public static JPanel generatePanel(GraphNode node){
        JPanel panel = null;
        for(Class[] cls : nodesMap){
            if(cls[0].isInstance(node)){
                try {
                    Class<? extends GraphNode> aClass = node.getClass();
                    panel = (JPanel) cls[1].getConstructor(aClass).newInstance(node);
                    break;
                } catch (NoSuchMethodException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (SecurityException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (InstantiationException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IllegalAccessException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IllegalArgumentException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (InvocationTargetException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        return panel;
    }
    


  
}
