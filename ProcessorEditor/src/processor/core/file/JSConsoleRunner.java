/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.file;

import java.io.PrintStream;
import java.util.Map;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import processor.profile.DIEmulator;

/**
 *
 * @author cbaez
 */
public class JSConsoleRunner {
    protected Map variableHolder = DIEmulator.getVariableHolder();
    protected String code;
    protected Invocable invocable;
    

    public JSConsoleRunner(PrintStream out){
        System.setOut(out);
    }
    
    
    public JSConsoleRunner(){
        
    }
    
     public  Object executeCode(String code) {
        setCode(code);
        try {
            Object result = invocable.invokeFunction("consoleCode", variableHolder);
            return result;
        } catch (ScriptException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void setCode(String code) {
        this.code = code;
        
        

        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("nashorn");

        String ecode = "var consoleCode = function(data){\n";
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
