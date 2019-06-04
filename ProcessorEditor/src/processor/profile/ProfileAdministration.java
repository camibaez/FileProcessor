/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.profile;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processor.core.file.Profile;

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
        ProfileStructure structure = new ProfileStructure(profile);
        
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
            conditions.add(ProfileWriter.writeCondition(c));
        });
        jo.put("conditions", conditions);
        
        return jo.toJSONString();
    }
    
    public static void saveProject(Profile project, String path) throws FileNotFoundException {

        try (PrintWriter pw = new PrintWriter(path)) {
            String fileData = generateProfileJSON(project);
            pw.write(fileData);
            pw.flush();
        }

    }

    public static Profile loadProject(String path) {
        Profile project = null;
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(path)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            project = ProfileReader.readProject(jsonObject);
            project.setActions(ProfileReader.readActions(jsonObject));
            project.setConditions(ProfileReader.readConditions(jsonObject));

            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(ProfileAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try (Reader reader = new FileReader(path.replace(".json", ".dot"))) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            project = ProfileReader.readProject(jsonObject);
            project.setActions(ProfileReader.readActions(jsonObject));
            project.setConditions(ProfileReader.readConditions(jsonObject));

            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(ProfileAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }

        return project;

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
