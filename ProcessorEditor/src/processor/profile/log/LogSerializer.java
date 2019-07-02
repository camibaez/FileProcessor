/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.profile.log;

import java.io.File;
import processor.profile.log.FilesLog;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.openide.util.Exceptions;
import processor.core.file.ProcessingResult;
import processor.core.file.Profile;
import processor.core.file.ProjectCentral;
import processor.core.graph.DecisionGraph;
import processor.core.graph.GraphNode;
import processor.core.graph.actions.Action;
import processor.core.graph.serialization.GraphSerializer;
import processor.profile.ProfileSerializer;

/**
 *
 * @author cbaez
 */
public class LogSerializer {

    public static void saveLog(Path backupPath) {
        String fileName = backupPath.toString() + ".json";

        Map m = new JSONObject();
        List arr = new JSONArray();
        Profile profile = ProjectCentral.instance().getProfile();
        profile.getFileCentral().getResultMap().entrySet().forEach((Entry<String, ProcessingResult> e) -> {
            if (e.getValue().getActions().size() < 1) {
                return;
            }
            List record = new JSONArray();
            record.add(e.getKey());
            List<Action> actions = e.getValue().getActions();
            actions.forEach(a -> {
                record.add(a.getId());
            });
            arr.add(record);
        });
        m.put("logs", arr);

        try (PrintWriter pw = new PrintWriter(fileName)) {
            pw.write(((JSONObject) m).toJSONString());
            pw.flush();
            System.out.println("Savend log " + fileName);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static Map<String, Set<String>> readLog(String path) {
        Map<String, Set<String>> log = new FilesLog();

        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(path)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray logs = (JSONArray) jsonObject.get("logs");
            logs.forEach(l -> {
                Set<String> cleaners = new HashSet<>();
                String fileName = (String) ((JSONArray) l).remove(0);
                cleaners.addAll(((JSONArray) l));
                log.put(fileName, cleaners);
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(ProfileSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return log;
    }

    public static void backupFile(Path basePath, Path originPath, Path backupPath) {
        String o = originPath.toString();
        String base = basePath.toString();
        String backup = backupPath.toString();
        
        Path finalPath = Paths.get(backup, o.replace(base, ""));
        
        try {
            Files.createDirectories(finalPath.getParent());
            Files.copy(originPath, finalPath);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    public static Path generateLogFolder(File profileFile) throws IOException{
        String fileName = profileFile.getName();
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        fileName = fileName + "_log_" + System.currentTimeMillis();
        fileName = profileFile.getParent() + "\\" + fileName;
        Path folder = Paths.get(fileName);
        Files.createDirectory(folder);
        return folder;
    }

}
