/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.relationalOperators;
import internalVariables.*;
/**
 *
 * @author anurag
 */
public class NotEqualToOperator extends BinaryOperator{
    @Override
    public NodeType PerformOperation(NodeType n1, NodeType n2) 
    {
        return null;
    }
    
    @Override
    public String GetNodeContents()
    {
        return("NotEqualTo operator node");
    }
}
