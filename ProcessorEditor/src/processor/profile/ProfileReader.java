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
import processor.core.file.Profile;
import processor.core.graph.GraphNode;
import processor.core.graph.actions.Action;
import processor.core.graph.actions.ExecutableAction;
import processor.core.graph.actions.ReplaceText;
import processor.core.graph.conditions.Condition;
import processor.core.graph.conditions.ConditionalPattern;
import processor.core.graph.conditions.ExecutableCondition;
import processor.core.graph.conditions.FileContent;
import processor.core.graph.conditions.FilePattern;
import processor.core.graph.conditions.TextContent;

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
    
    public static String readGraphData(JSONObject o){
        return (String) o.get("graph");
    }
    
    
    public static List<GraphNode> readNodes(JSONObject data) {
        List<GraphNode> nodesList = new LinkedList<>();
        JSONArray nodesData = (JSONArray) data.get("nodes");

        nodesData.forEach(n -> {
            GraphNode node = readNode((Map) n);
            if(node != null)
                nodesList.add(node);
        });

        return nodesList;
    }

    public static GraphNode readNode(Map map) {
        String type = (String) map.get("type");
        GraphNode node = null;
        if(type.equals(Condition.class.getSimpleName())){
            node = readCondition( map);
        }
        if(type.equals(Action.class.getSimpleName())){
            node = readAction(map);
        }
        
        return node;
    }

    public static Condition readCondition(Map data) {
        if(data == null)
            return null;
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
        if (clazz.equals(ExecutableCondition.class.getSimpleName())) {
            condition = readExecutableCondition(data);
        }
        if (condition != null) {
            condition.setId((String) data.get("id"));
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
            int flags = ((Long)((Map)e).get("flags")).intValue();

            expressionsList.add(ConditionalPattern.compile(pattern, flags, condition));
        });

        return new FileContent(expressionsList);
    }

    private static Condition readTextContentCondition(Map data) {
        String pattern = (String) data.get("pattern");
        String condition = (String) data.get("condition");
        int flags = ((Long)data.get("flags")).intValue();

        return new TextContent(ConditionalPattern.compile(pattern, flags, condition));
    }
    
    private static Condition readExecutableCondition(Map data) {
        ExecutableCondition executableCondition = new ExecutableCondition();
        executableCondition.setCode((String) data.get("code"));
        return executableCondition;
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
        if(data == null)
            return null;
        String clazz = (String) data.get("class");
        Action action = null;
        if (clazz.equals(ReplaceText.class.getSimpleName())) {
            action = readReplaceText(data);
        }
        if(clazz.equals(ExecutableAction.class.getSimpleName())){
            action = readExecutableAction(data);
        }
        if (action != null) {
            action.setId((String) data.get("id"));
        }

        return action;
    }

    public static ReplaceText readReplaceText(Map data) {
        String pattern = (String) data.get("pattern");
        String replace = (String) data.get("replace");
        int flags = ((Long)data.get("flags")).intValue();
        return new ReplaceText(pattern, replace, flags);
    }
    
    public static ExecutableAction readExecutableAction(Map data) {
        ExecutableAction executableAction = new ExecutableAction();
        executableAction.setCode((String) data.get("code"));
        return executableAction;
    }

    

}
