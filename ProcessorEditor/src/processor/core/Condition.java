/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.core;

/**
 *
 * @author cbaez
 */
public abstract class Condition implements ProcessingNode{
    public abstract boolean test(Object o);
}
