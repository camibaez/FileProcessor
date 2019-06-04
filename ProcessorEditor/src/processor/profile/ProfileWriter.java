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
        Map ruleData = null;
        if (action instanceof ReplaceText) {
            ruleData = replaceTextAction((ReplaceText) action);
        }
        if (ruleData != null) {
            ruleData.put("class", action.getClass().getSimpleName());
        }
        return ruleData;
    }

    public static Map replaceTextAction(ReplaceText rule) {
        Map ruleData = new LinkedHashMap(3);
        ruleData.put("pattern", rule.getPatternText());
        ruleData.put("replace", rule.getReplace());
        ruleData.put("flags", rule.getFlags());
        return ruleData;
    }

    public static Map writeCondition(Condition c) {
        Map conditionData = new LinkedHashMap();
        if (c instanceof FilePattern) {
            fileTypeCondition(conditionData, (FilePattern) c);
        }
        if (c instanceof FileContent) {
            fileContentCondition(conditionData, (FileContent) c);
        }
        if(c instanceof TextContent){
            genericContentCondition(conditionData, (TextContent) c);
        }
        if (conditionData != null) {
            conditionData.put("class", c.getClass().getSimpleName());
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
