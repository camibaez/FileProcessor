/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datapro.nfp.profile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import com.datapro.nfp.core.graph.GraphNode;
import com.datapro.nfp.core.graph.actions.Action;
import com.datapro.nfp.core.graph.actions.ExecutableAction;
import com.datapro.nfp.core.graph.actions.ReplaceText;
import com.datapro.nfp.core.graph.conditions.Condition;
import com.datapro.nfp.core.graph.conditions.ExecutableCondition;
import com.datapro.nfp.core.graph.conditions.FileContent;
import com.datapro.nfp.core.graph.conditions.FilePattern;
import com.datapro.nfp.core.graph.conditions.TextContent;

/**
 *
 * @author cbaez
 */
public class ProfileWriter {
    
    public static JSONArray writeNodes(List<GraphNode> nodes){
        JSONArray nodesData = new JSONArray();
        nodes.forEach(n -> {
            Map map = writeNode(n);
            nodesData.add(map);
        });
        return nodesData;
    }
    
    public static Map writeNode(GraphNode node){
        Map data  = null;
        String type = "";
        if(node instanceof Condition){
            data = writeCondition((Condition) node);
            type = Condition.class.getSimpleName();
        }
        if(node instanceof Action){
            data = writeAction((Action) node);
            type = Action.class.getSimpleName();
        }
        if(data != null){
            data.put("class", node.getClass().getSimpleName());
            data.put("id", node.getId());
            data.put("type", type);
            data.put("active", node.isActive());
        }
        return data;
    }
    
    public static Map writeAction(Action action) {
        if(action == null)
            return null;
        Map actionData = null;
        if (action instanceof ReplaceText) {
            actionData = replaceTextAction((ReplaceText) action);
        }
        if (action instanceof ExecutableAction) {
            actionData = executableAction((ExecutableAction)action);
        }
        
        return actionData;
    }

    public static Map replaceTextAction(ReplaceText rule) {
        Map ruleData = new LinkedHashMap(3);
        ruleData.put("pattern", rule.getPatternText());
        ruleData.put("replace", rule.getReplace());
        ruleData.put("flags", rule.getFlags());
        return ruleData;
    }
    public static Map executableAction(ExecutableAction rule) {
        Map ruleData = new LinkedHashMap(3);
        ruleData.put("code", rule.getCode());
        return ruleData;
    }

    public static Map writeCondition(Condition condition) {
        if(condition == null)
            return null;
        Map conditionData = new LinkedHashMap();
        if (condition instanceof FilePattern) {
            fileTypeCondition(conditionData, (FilePattern) condition);
        }
        if (condition instanceof FileContent) {
            fileContentCondition(conditionData, (FileContent) condition);
        }
        if(condition instanceof TextContent){
            genericContentCondition(conditionData, (TextContent) condition);
        }
        if(condition instanceof ExecutableCondition){
            executableCondition(conditionData, (ExecutableCondition) condition);
        }
        
        return conditionData;
    }

    public static void fileTypeCondition(Map map, FilePattern c) {
        map.put("pattern", c.getPattern());
    }

    private static void fileContentCondition(Map map, FileContent fileContent) {
        JSONArray expressionsData = new JSONArray();
        fileContent.getExpressions().forEach(e -> {
            Map data = new LinkedHashMap();
            data.put("pattern", e.getPattern().pattern());
            data.put("flags", e.getPattern().flags());
            data.put("condition", e.getCondition());
            expressionsData.add(data);
        });
        map.put("expressions", expressionsData);
    }

    private static void genericContentCondition(Map map, TextContent genContent){
        map.put("pattern", genContent.getPattern().getPattern().pattern());
        map.put("flags", genContent.getPattern().getPattern().flags());
        map.put("condition", genContent.getPattern().getCondition());
    }

    private static void executableCondition(Map map, ExecutableCondition executableCondition) {
        map.put("code", executableCondition.getCode());
    }

}
