/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import processor.core.graph.serialization.GraphBuilder;
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

        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void saveGraph(){
        System.out.println(ProfileAdministration.generateProfileJSON(profile));
    }

}
