/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.datapro.nfp.core.graph.parsers.CodeScope;
import com.datapro.nfp.core.graph.parsers.JavaParserUtils;

/**
 *
 * @author cbaez
 */
public class JavaParserUnitTest {
    String code;
    JavaParserUtils jParser;
    
    @Before
    public void setUp() throws IOException {
        Path file = Paths.get("C:\\Users\\cbaez\\Desktop\\ProcessorWorkspace\\SSLMigration\\test\\files\\JSEIN0530.java");
        code = new String(Files.readAllBytes(file));
        jParser = new JavaParserUtils(code);
        
    }
    
    

    @Test
    public void testCodeScopes(){
        List<CodeScope> scopes = jParser.getScopes(CodeScope.METHOD);
        
    }
}
