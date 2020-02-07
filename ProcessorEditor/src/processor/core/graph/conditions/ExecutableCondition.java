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

import processor.core.file.VariableHolder;
import processor.core.graph.ExecutableNode;
import processor.profile.DIEmulator;

/**
 *
 * @author cbaez
 */
public class ExecutableCondition extends Condition<String> implements ExecutableNode{
    VariableHolder variableHolder = DIEmulator.getVariableHolder();
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

    public Object executeCode(Object target) {
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

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "<" + getId() + ">:";
    }

    @Override
    public int compile() {
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
            return 1;
        }
        return 0;
    }

}
