/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.profile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processor.core.conditions.Condition;
import processor.core.conditions.ConditionalPattern;
import processor.core.rules.RuleCluster;
import processor.core.conditions.FilePrototype;
import processor.core.file.Profile;
import processor.core.rules.Action;
import processor.core.rules.TextRule;

/**
 *
 * @author cbaez
 */
public class ProfileAdministration {

    
    
    
    public static void createEmptyProject(String path) throws FileNotFoundException, IOException{
        String data = new String(Files.readAllBytes(Paths.get("C:\\Users\\cbaez\\Documents\\baseproject.json")));
        try (PrintWriter pw = new PrintWriter(path)) {
            pw.write(data);
            pw.flush();
        }
    }
    
    
    public static String generateProfileJSON(Profile profile){
        JSONObject jo = new JSONObject();
        ProfileStructure structure = new ProfileStructure(profile.getGraph());
        
        jo.put("name", structure.getName());
        jo.put("description", structure.getDescription());
        jo.put("lastWorkingDirectory", structure.getLastWorkingDirectory());
        
        JSONArray actions = new JSONArray();
        structure.getActions().forEach(a -> {
            actions.add(ProfileWriter.writeAction(a));
        });
        jo.put("actions", actions);
        
        JSONArray conditions = new JSONArray();
        structure.getConditions().forEach(c -> {
            conditions.add(ProfileWriter.writeCondition(a));
        });
        
        
        return jo.toJSONString();
    }
    
    
   
    
    
    /*
    public static String generateProfileJSON(Profile profile) {
        JSONObject jo = new JSONObject();

        jo.put("name", profile.getName());
        jo.put("description", profile.getDescription());
        jo.put("lastWorkingDirectory", profile.getWorkingDirectory());
        jo.put("prototypes", ProfileWriter.writePrototypes(profile.getPrototypesMap()));

        JSONArray cleanersArray = new JSONArray();
        for (Cleaner cleaner : profile.getCleaners()) {
            JSONArray rules = new JSONArray();
            cleaner.getRules().forEach(rule -> {
                rules.add(ProfileWriter.writeRule(rule));
            });

            cleanersArray.add(ProfileWriter.writeCleaner(cleaner, rules));
        }
        jo.put("cleaners", cleanersArray);
        return jo.toJSONString();
    }
    */

    public static void saveProject(Profile project, String path) throws FileNotFoundException {

        try (PrintWriter pw = new PrintWriter(path)) {
            String fileData = generateProfileJSON(project);
            pw.write(fileData);
            pw.flush();
        }

    }

    public static Profile loadProject(String path) {
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(path)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            Profile project = ProfileReader.readProject(jsonObject);
            project.setPrototypesMap(ProfileReader.readPrototypes(jsonObject));

            List<RuleCluster> cleaners = ProfileReader.readCleaners(jsonObject);
            project.setCleaners(cleaners);

            return project;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(ProfileAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public static String prettyPrintJSON(String unformattedJsonString) {
        StringBuilder prettyJSONBuilder = new StringBuilder();
        int indentLevel = 0;
        boolean inQuote = false;
        for (char charFromUnformattedJson : unformattedJsonString.toCharArray()) {
            switch (charFromUnformattedJson) {
                case '"':
                    // switch the quoting status
                    inQuote = !inQuote;
                    prettyJSONBuilder.append(charFromUnformattedJson);
                    break;
                case ' ':
                    // For space: ignore the space if it is not being quoted.
                    if (inQuote) {
                        prettyJSONBuilder.append(charFromUnformattedJson);
                    }
                    break;
                case '{':
                case '[':
                    // Starting a new block: increase the indent level
                    prettyJSONBuilder.append(charFromUnformattedJson);
                    indentLevel++;
                    appendIndentedNewLine(indentLevel, prettyJSONBuilder);
                    break;
                case '}':
                case ']':
                    // Ending a new block; decrese the indent level
                    indentLevel--;
                    appendIndentedNewLine(indentLevel, prettyJSONBuilder);
                    prettyJSONBuilder.append(charFromUnformattedJson);
                    break;
                case ',':
                    // Ending a json item; create a new line after
                    prettyJSONBuilder.append(charFromUnformattedJson);
                    if (!inQuote) {
                        appendIndentedNewLine(indentLevel, prettyJSONBuilder);
                    }
                    break;
                default:
                    prettyJSONBuilder.append(charFromUnformattedJson);
            }
        }
        return prettyJSONBuilder.toString();
    }

    /**
     * Print a new line with indention at the beginning of the new line.
     *
     * @param indentLevel
     * @param stringBuilder
     */
    private static void appendIndentedNewLine(int indentLevel, StringBuilder stringBuilder) {
        stringBuilder.append("\n");
        for (int i = 0; i < indentLevel; i++) {
            // Assuming indention using 2 spaces
            stringBuilder.append("  ");
        }
    }
}
