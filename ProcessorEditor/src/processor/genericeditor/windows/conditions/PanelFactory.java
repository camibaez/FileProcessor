/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.genericeditor.windows.conditions;

import processor.core.conditions.Condition;
import processor.core.conditions.FileContent;
import processor.core.conditions.FilePattern;
import processor.core.conditions.TextContent;

/**
 *
 * @author cbaez
 */
public class PanelFactory {
    public static  ConditionPanel generatePanel(Condition c){
        ConditionPanel panel = null;
        if(c instanceof FilePattern)
            panel = new FilePatternPanel((FilePattern) c);
        if(c instanceof FileContent)
            panel = new FileContentPanel((FileContent) c);
        if(c instanceof TextContent)
            panel = new TextContentPanel((TextContent) c);
        
        return panel;
    }
}
