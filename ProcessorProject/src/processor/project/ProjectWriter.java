/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.project;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import processor.core.file.Cleaner;
import processor.core.file.FilePrototype;
import processor.core.rules.ReplaceText;
import processor.core.rules.Rule;

/**
 *
 * @author cbaez
 */
public class ProjectWriter {
    protected static JSONArray writePrototypes(Map<String, FilePrototype> map){
        JSONArray prototypesData = new JSONArray();
        map.values().forEach(e -> {
            prototypesData.add(writePrototype(e));
        });
        
        return prototypesData;
    }
    
    
     protected static Map writePrototype(FilePrototype prototype) {
            Map prototypeData = new LinkedHashMap(2);
            prototypeData.put("extensions", prototype.getExtensions());
            JSONArray expressionsData = new JSONArray();
            prototype.getExpressions().forEach(e ->{
                Map data = new LinkedHashMap();
                data.put("pattern", e.getPattern().pattern());
                data.put("flags", e.getPattern().flags());
                data.put("condition", e.getCondition());
                expressionsData.add(data);
            });
            prototypeData.put("expressions", expressionsData);
            prototypeData.put("id", prototype.getId());
            return prototypeData;
        }

        public static Map writeRule(Rule rule) {
            Map ruleData = null;
            if (rule instanceof ReplaceText) {
                ruleData = writeReplaceTextRule((ReplaceText) rule);
            }
            if (ruleData != null) {
                ruleData.put("ruleClass", ReplaceText.class.getSimpleName());
            }
            return ruleData;
        }

        protected static Map writeCleaner(Cleaner cleaner, JSONArray rules) {
            Map cleanerData = new LinkedHashMap(3);
            cleanerData.put("id", cleaner.getId());
            cleanerData.put("description", cleaner.getDescription());
            cleanerData.put("rules", rules);
            if(cleaner.getPrototype() != null)
                cleanerData.put("prototype", cleaner.getPrototype().getId());
            return cleanerData;
        }

        public static Map writeReplaceTextRule(ReplaceText rule) {
            Map ruleData = new LinkedHashMap(3);
            ruleData.put("pattern", rule.getPatternText());
            ruleData.put("replace", rule.getReplace());
            ruleData.put("flags", rule.getFlags());
            return ruleData;
        }
        
        
        
}
