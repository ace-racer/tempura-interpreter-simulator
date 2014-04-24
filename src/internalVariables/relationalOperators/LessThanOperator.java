/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.relationalOperators;

import internalVariables.BinaryOperator;
import internalVariables.NodeType;

/**
 *
 * @author anurag
 */
public class LessThanOperator extends BinaryOperator{
    @Override
    public NodeType PerformOperation(NodeType n1, NodeType n2) 
    {
        return (new GreaterThanOperator()).PerformOperation(n2, n1);
    }
    
    @Override
    public String GetNodeContents()
    {
        return("LessThan operator node");
    }
}
