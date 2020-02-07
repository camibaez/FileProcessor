package processor.core.graph.actions;

import java.util.Map;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import processor.core.graph.ExecutableNode;
import processor.profile.DIEmulator;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cbaez
 */
public class ExecutableAction extends TextTransformer implements ExecutableNode{
    Map variableHolder = DIEmulator.getVariableHolder();
    protected String code;
    protected Invocable invocable;

    @Override
    public Object transform(String target) {
        Object res = executeCode(target);
        if (res != null) {
            return (String) res;
        }
        return target;
    }

    @Override
    public Object executeCode(Object target) {

        try {
            Object result = invocable.invokeFunction("cleanFunc", target, variableHolder);
            return result;
        } catch (ScriptException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "<" + getId() + ">";
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int compile() {
       ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("nashorn");

        String ecode = "var cleanFunc = function(target, data){\n";
        ecode += this.code;
        ecode += "\n}";

        try {
            engine.eval(ecode);
            invocable = (Invocable) engine;
        } catch (ScriptException ex) {
            ex.printStackTrace();
            return 1;
        }
        return 0;
    }

    

}
