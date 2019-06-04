/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.profile;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import processor.core.ProcessingNode;
import processor.core.graph.conditions.Condition;
import processor.core.graph.conditions.ConditionalPattern;
import processor.core.graph.conditions.FileContent;
import processor.core.graph.conditions.FilePattern;
import processor.core.graph.conditions.TextContent;
import processor.core.file.Profile;
import processor.core.graph.actions.Action;
import processor.core.graph.actions.ReplaceText;

/**
 *
 * @author cbaez
 */
public class ProfileReader {

    public static Profile readProject(JSONObject jsonObject) {
        Profile project = new Profile();
        project.setName((String) jsonObject.get("name"));
        project.setDescription((String) jsonObject.get("description"));
        project.setLastWorkingDirectory((String) jsonObject.get("lastWorkingDirectory"));
        return project;
    }

    public static List<ProcessingNode> readNodes(JSONObject data){
        List<ProcessingNode> nodesList = new LinkedList<>();
        JSONArray nodesData = (JSONArray) data.get("nodes");
        
        nodesData.forEach(n -> {
            ProcessingNode node = null;
            node = readCondition((Map) n);
            if(node != null){
                nodesList.add(node);
                return;
            }
            node = readAction((Map) n);
            if(node != null){
                nodesList.add(node);
            }
                
        });
        
        return nodesList;
    }
    
    
    public static List<Condition> readConditions(JSONObject data) {
        List<Condition> conditionsList = new LinkedList<>();
        JSONArray conditionsData = (JSONArray) data.get("conditions");
        conditionsData.forEach(c -> {
            conditionsList.add(readCondition((Map) c));
        });

        return conditionsList;
    }

    public static Condition readCondition(Map data) {
        String clazz = (String) data.get("class");
        Condition condition = null;
        if (clazz.equals(FilePattern.class.getSimpleName())) {
            condition = readFilePatterCondition(data);
        }
        if (clazz.equals(FileContent.class.getSimpleName())) {
            condition = readFileContentCondition(data);
        }
        if (clazz.equals(TextContent.class.getSimpleName())) {
            condition = readTextContentCondition(data);
        }
        if (condition != null) {
            condition.setId((String) data.get("id"));
            condition.setType((int) data.get("type"));
        }

        return condition;
    }

    public static FilePattern readFilePatterCondition(Map data) {
        String pattern = (String) data.get("pattern");
        return new FilePattern(pattern);
    }

    public static FileContent readFileContentCondition(Map data) {
        JSONArray expressions = (JSONArray) data.get("expressions");
        List<ConditionalPattern> expressionsList = new LinkedList<>();
        expressions.forEach(e -> {
            String pattern = (String) ((Map) e).get("pattern");
            String condition = (String) ((Map) e).get("condition");
            int flags = (int) ((Map) e).get("flags");

            expressionsList.add(ConditionalPattern.compile(pattern, flags, condition));
        });

        return new FileContent(expressionsList);
    }

    private static Condition readTextContentCondition(Map data) {
        String pattern = (String) data.get("pattern");
        String condition = (String) data.get("condition");
        int flags = (int) data.get("flags");

        return new TextContent(ConditionalPattern.compile(pattern, flags, condition));
    }

    public static List<Action> readActions(JSONObject data) {
        List<Action> actionsList = new LinkedList<>();
        JSONArray actionsData = (JSONArray) data.get("actions");
        actionsData.forEach(a -> {
            actionsList.add(readAction((Map) a));
        });

        return actionsList;
    }

    public static Action readAction(Map data) {
        String clazz = (String) data.get("class");
        Action action = null;
        if (clazz.equals(ReplaceText.class.getSimpleName())) {
            action = readReplaceText(data);
        }
        if (action != null) {
            action.setId((String) data.get("id"));
            action.setType((int) data.get("type"));
        }
        
        return action;
    }

    public static ReplaceText readReplaceText(Map data) {
        String pattern = (String) data.get("pattern");
        String replace = (String) data.get("replace");
        int flags = (int) data.get("flags");
        return new ReplaceText(pattern, replace, flags);
    }

}
