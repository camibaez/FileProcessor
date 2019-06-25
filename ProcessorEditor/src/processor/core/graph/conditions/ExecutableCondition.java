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

import processor.core.file.FileProcessor;
import processor.core.file.VariableHolder;

/**
 *
 * @author cbaez
 */
public class ExecutableCondition extends Condition<String>{
    protected String code;
    @Override
    public boolean test(String target) {
        Object res = executeCode(target);
        if(res != null && res instanceof Boolean){
            return (boolean) res;
        }
        return false;
    }
    
    protected Object executeCode(Object target){
        VariableHolder variableHolder = FileProcessor.getVariableHolder();
        
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("nashorn");
        try {
            String code = "var testFunction = function(target, vars){\n";
                   code += this.code;
                   code += "\n}";
                   
            engine.eval(code);
            Invocable invocable = (Invocable) engine;
            Object result = invocable.invokeFunction("testFunction", target, variableHolder.export());
            return result;
        } catch (ScriptException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String toString() {
        return "<" + getId() + ">:ExecCondition";
    }
    
}
