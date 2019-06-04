/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.lineal;

import processor.core.graph.actions.Action;
import processor.core.graph.conditions.Condition;

/**
 *
 * @author cbaez
 */
public class ProcessingNode {

    public static int OMISSBLE = 1, UNOMISSIBLE = 0;
    protected int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
