/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.core.graph.conditions;

import java.util.HashMap;
import java.util.Map;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.datapro.nfp.core.file.VariableHolder;
import com.datapro.nfp.core.graph.Exchange;
import com.datapro.nfp.core.graph.ExecutableNode;
import com.datapro.nfp.profile.DIEmulator;

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

    @Override
    public Object executeCode(Object target) {
        try {
            Exchange exchange = new Exchange();
            exchange.setIn(target);
            exchange.setContext(variableHolder);
            Map<String, Object> exchangeMap = new HashMap<String, Object>();
            exchangeMap.put("in", exchange.getIn());
            exchangeMap.put("context", exchange.getContext());
            
            Object result = invocable.invokeFunction(this.id, exchangeMap);
            return result;
        } catch (Exception ex) {
            System.err.println("Error executing Condition: " + id);
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
        String functionCode = "var " + this.id +"= function(exchange){\n";
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
