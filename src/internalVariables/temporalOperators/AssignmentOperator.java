/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.temporalOperators;
import internalVariables.BinaryOperator;
import internalVariables.IdentifierNode;
import internalVariables.NodeType;
/**
 *
 * @author anurag
 */
public class AssignmentOperator extends BinaryOperator{
    
    @Override
    public NodeType PerformOperation(NodeType n1, NodeType n2) 
    {
        System.out.println("Last state Assignment operator called: Replace with Iassign for immediate effect");
        return null;
    }
    @Override
    public String GetNodeContents()
    {
        return("Assignment operator node");
    }
}
