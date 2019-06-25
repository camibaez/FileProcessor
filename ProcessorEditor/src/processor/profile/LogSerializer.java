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
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processor.core.file.ProcessingResult;
import processor.core.file.ProjectCentral;
import processor.core.graph.DecisionGraph;
import processor.core.graph.GraphNode;
import processor.core.graph.actions.Action;
import processor.core.graph.serialization.GraphSerializer;

/**
 *
 * @author cbaez
 */
public class LogSerializer {
    public static void saveLog(){
        String fileName = ProjectCentral.instance().getProfileFile().getName();
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        fileName = fileName + "_log_" + System.currentTimeMillis() + ".json";
        fileName = ProjectCentral.instance().getProfileFile().getParent() + "\\" + fileName;

        Map m = new JSONObject();
        List arr = new JSONArray();
        ProjectCentral.instance().getProfile().getFileCentral().getResultMap().entrySet().forEach((Entry<String, ProcessingResult> f) -> {
            List record = new JSONArray();
            record.add(f.getKey());
            List<Action> actions = f.getValue().getActions();
            actions.forEach(a -> {
                record.add(a.getId());
            });
            arr.add(record);
        });
        m.put("logs", arr);

        try (PrintWriter pw = new PrintWriter(fileName)) {
            pw.write(((JSONObject) m).toJSONString());
            pw.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public static Map<String, Set<String>> readLog(String path){
        Map<String, Set<String>> log = new FilesLog();
        
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(path)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray logs = (JSONArray) jsonObject.get("logs");
            logs.forEach(l -> {
                Set<String> cleaners = new HashSet<>();
                String fileName = (String) ((JSONArray) l).remove(0);
                cleaners.addAll(((JSONArray)l));
                log.put(fileName, cleaners);
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(ProfileSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return log;
    } 
}
