/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.project;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static processor.project.ProjectAdministration.generateProfileJSON;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author cbaez
 */
public class ConfigAdministration {
    public static File lastOpenedProject;
    
    public static void loadConfiguration(){
        JSONParser parser = new JSONParser();
        String path = "config.json";
        try (Reader reader = new FileReader(path)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

           String lastProject = (String) jsonObject.get("lastProject");
           lastOpenedProject = new File(lastProject);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(ProjectAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void saveConfiguration(){
        JSONObject jo = new JSONObject();
        jo.put("lastProject", lastOpenedProject);
        
        try (PrintWriter pw = new PrintWriter("config.json")) {
            String fileData = jo.toJSONString();
            pw.write(fileData);
            pw.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
