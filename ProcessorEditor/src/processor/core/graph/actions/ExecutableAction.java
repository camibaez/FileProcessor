package processor.core.graph.actions;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import processor.core.file.FileProcessor;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cbaez
 */
public class ExecutableAction extends TextTransformer {

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

    protected Object executeCode(String target) {

        try {
            Object result = invocable.invokeFunction("cleanFunc", target, FileProcessor.variableHolder);
            return result;
        } catch (ScriptException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String toString() {
        return "<" + getId() + ">";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;

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
        }

    }

}
