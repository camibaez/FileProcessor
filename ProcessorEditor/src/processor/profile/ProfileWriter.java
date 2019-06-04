/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.profile;

import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import processor.core.graph.conditions.Condition;
import processor.core.graph.conditions.FileContent;
import processor.core.graph.conditions.FilePattern;
import processor.core.graph.conditions.TextContent;
import processor.core.graph.actions.Action;
import processor.core.graph.actions.ReplaceText;

/**
 *
 * @author cbaez
 */
public class ProfileWriter {
    public static Map writeAction(Action action) {
        Map actionData = null;
        if (action instanceof ReplaceText) {
            actionData = replaceTextAction((ReplaceText) action);
        }
        if (actionData != null) {
            actionData.put("class", action.getClass().getSimpleName());
            actionData.put("id", action.getId());
            actionData.put("type", action.getType());
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

    public static Map writeCondition(Condition condition) {
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
        if (conditionData != null) {
            conditionData.put("class", condition.getClass().getSimpleName());
            conditionData.put("id", condition.getId());
            conditionData.put("type", condition.getType());
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

}
