/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.conditions;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.openide.util.Exceptions;

/**
 *
 * @author cbaez
 */
public class ExecutableCondition extends Condition<Object>{
    protected String code;
    @Override
    public boolean test(Object target) {
        Object res = executeCode(target);
        if(res != null && res instanceof Boolean){
            return (boolean) res;
        }
        return false;
    }
    
    protected Object executeCode(Object target){
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("nashorn");
        try {
            String code = "var testFunction = function(target){\n";
                   code += this.code;
                   code += "\n}";
                   
            engine.eval(code);
            Invocable invocable = (Invocable) engine;
            Object result = invocable.invokeFunction("cleanFunc", target);
            return result;
        } catch (ScriptException ex) {
            Exceptions.printStackTrace(ex);
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
    
}
