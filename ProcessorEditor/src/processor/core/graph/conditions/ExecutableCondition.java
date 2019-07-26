/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph.conditions;

import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ExecutableCondition extends Condition<String> {

    protected String code;
    Invocable invocable;

    @Override
    public boolean test(String target) {
        Object res = executeCode(target);
        if (res != null && res instanceof Boolean) {
            return (boolean) res;
        }
        return false;
    }

    protected Object executeCode(Object target) {
        VariableHolder variableHolder = FileProcessor.getVariableHolder();
        try {
            Object result = invocable.invokeFunction("testFunction", target, variableHolder);
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

        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("nashorn");
        String functionCode = "var testFunction = function(target, data){\n";
            functionCode += this.code;
        functionCode += "\n}";

        try {
            engine.eval(functionCode);
            this.invocable = (Invocable) engine;
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }

    }

    public String toString() {
        return "<" + getId() + ">:ExecCondition";
    }

}
