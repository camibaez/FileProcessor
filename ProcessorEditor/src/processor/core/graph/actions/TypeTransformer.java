/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.actions;

import processor.core.graph.actions.TextTransformer;
import processor.core.graph.actions.Transformer;




/**
 *
 * @author cbaez
 */
public class TypeTransformer {

    public static <T> T transformFor(Transformer<T> caller, Object o) {
        if(caller instanceof TextTransformer){
            return (T) transformForType(String.class, o);
        }
        return null;
    }

    public static <T> T transformForType(Class<T> type, Object content) {
        if (type == String.class) {
            if (content instanceof String) {
                return (T) content;
            }
        }
        return null;
    }
}
