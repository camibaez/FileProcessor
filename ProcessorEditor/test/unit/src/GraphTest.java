
import com.datapro.nfp.core.graph.DecisionEdge;
import com.datapro.nfp.core.graph.DecisionGraph;
import com.datapro.nfp.core.graph.actions.ReplaceText;
import com.datapro.nfp.core.graph.conditions.FilePattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cbaez
 */
public class GraphTest {
    public DecisionGraph buildDummyGraph(){
        DecisionGraph graph = new DecisionGraph();
        FilePattern filePattern = new FilePattern("*.jsp");
        filePattern.setId("c1");
        graph.addVertex(filePattern);
        final ReplaceText replaceText = new ReplaceText("a", "b");
        replaceText.setId("a1");
        graph.addVertex(replaceText);
        
        graph.addEdge(DecisionGraph.START_NODE, filePattern, new DecisionEdge());
        graph.addEdge(filePattern, DecisionGraph.FAIL_NODE, new DecisionEdge(false));
        graph.addEdge(filePattern, replaceText, new DecisionEdge());
        graph.addEdge(replaceText, DecisionGraph.END_NODE);
        
        return graph;
    }
    
}
