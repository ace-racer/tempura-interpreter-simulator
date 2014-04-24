/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables;

/**
 *
 * @author anurag
 */
public class IfOperator extends TernaryOperator {
    @Override
    public NodeType PerformOperation(NodeType n1, NodeType n2,NodeType n3) 
    {
        return null;
    }
    
    @Override
    public String GetNodeContents()
    {
       return("If loop node");
    }
    
}
