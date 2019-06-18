package processor.core.graph.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.openide.util.Exceptions;

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

    @Override
    public Object transform(String target) {
        Object res = executeCode(target);
        if(res != null){
            return (String) res;
        }
        return target;
    }

    protected Object executeCode(String target) {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("nashorn");
        try {
            String code = "var cleanFunc = function(target){\n";
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

    public String toString() {
        return "<" + getId() + ">:ExecAction";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
