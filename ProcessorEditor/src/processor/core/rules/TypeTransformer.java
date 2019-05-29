/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.rules;




/**
 *
 * @author cbaez
 */
public class TypeTransformer {

    public static <T> T transformFor(Rule<T> caller, Object o) {
        if(caller instanceof TextRule){
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
