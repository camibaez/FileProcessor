/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.actions;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import processor.core.graph.conditions.Condition;
import processor.core.graph.conditions.ExecutableCondition;
import processor.core.graph.conditions.FileContent;
import processor.core.graph.conditions.FilePattern;
import processor.core.graph.conditions.TextContent;




/**
 *
 * @author cbaez
 */
public class TypeTranslator {

    public static Object translateFor(Condition condition, Object o) throws Exception{
        if(condition instanceof FilePattern){
            return (File) o;
        }
        if(condition instanceof FileContent)
            return (File) o;
        if(condition instanceof TextContent || condition instanceof ExecutableCondition){
            if(o instanceof String)
                return (String)o;
            if(o instanceof File){
                return new String(Files.readAllBytes(((File)o).toPath()), StandardCharsets.ISO_8859_1);
            }
        }
        
        throw new Exception();
    }
    
    public static Object translateFor(Action action, Object o) throws Exception{
        if(action instanceof TextTransformer){
            if(o instanceof String)
                return (String)o;
            if(o instanceof File){
                return new String(Files.readAllBytes(((File)o).toPath()));
            }
        }
        throw new Exception();
    }
    
    public static <T> T transalteFor(Transformer<T> caller, Object o) {
        if(caller instanceof TextTransformer){
            return (T) translateForType(String.class, o);
        }
        return null;
    }

    public static <T> T translateForType(Class<T> type, Object content) {
        if (type == String.class) {
            if (content instanceof String) {
                return (T) content;
            }
        }
        return null;
    }
}
