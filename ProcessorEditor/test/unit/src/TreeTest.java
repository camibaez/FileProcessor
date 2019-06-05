/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import processor.core.graph.GraphBuilder;
import processor.core.file.Profile;
import processor.core.graph.conditions.FileContent;
import processor.core.graph.conditions.FilePattern;
import processor.profile.ProfileAdministration;

/**
 *
 * @author cbaez
 */
public class TreeTest {
    Profile profile;

    public TreeTest() {
    }
    
    
    @Before
    public void setUp() {
        profile = new Profile();
        profile.setName("Project test");
        profile.setLastWorkingDirectory("C:\\Users\\cbaez\\Desktop\\ProcessorWorkspace\\files");
        profile.setDescription("Project test description");
        
        FilePattern filePattern = new FilePattern("*jsp");
        filePattern.setId("n1");
        FileContent fileContent = new FileContent();
        fileContent.setId("n2");
        
        //profile.addNode(filePattern);
        //profile.addNode(fileContent);
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void saveGraph(){
        System.out.println(ProfileAdministration.generateProfileJSON(profile));
    }

}
