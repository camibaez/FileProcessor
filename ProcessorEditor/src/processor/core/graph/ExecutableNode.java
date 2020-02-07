/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core.graph;

/**
 *
 * @author cbaez
 */
public interface ExecutableNode {
    public void setCode(String code);
    public String getCode();
    public Object executeCode(Object o);
    public int compile();
}
