/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.profile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import processor.core.conditions.Condition;
import processor.core.conditions.FileContent;
import processor.core.rules.RuleCluster;
import processor.core.conditions.FilePrototype;
import processor.core.conditions.FileType;
import processor.core.conditions.GenericContent;
import processor.core.rules.Action;
import processor.core.rules.ReplaceText;
import processor.core.rules.Rule;

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
        if (c instanceof FileType) {
            fileTypeCondition(conditionData, (FileType) c);
        }
        if (c instanceof FileContent) {
            fileContentCondition(conditionData, (FileContent) c);
        }
        if(c instanceof GenericContent){
            genericContentCondition(conditionData, (GenericContent) c);
        }
        if (conditionData != null) {
            conditionData.put("class", c.getClass().getSimpleName());
        }
        return conditionData;
    }

    public static void fileTypeCondition(Map map, FileType c) {
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

    private static void genericContentCondition(Map map, GenericContent genContent){
        map.put("pattern", genContent.getPattern().getPattern().pattern());
        map.put("flags", genContent.getPattern().getPattern().flags());
        map.put("condition", genContent.getPattern().getCondition());
    }

}
