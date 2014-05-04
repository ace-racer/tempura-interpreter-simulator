/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.temporalOperators;
import internalVariables.BasicNode;
import internalVariables.BinaryOperator;
import internalVariables.IdentifierNode;
import internalVariables.NodeType;
import internalVariables.constantNodes.BooleanConstantNode;
/**
 *
 * @author anurag
 */
public class IassignOperator extends BinaryOperator{
     @Override
    public NodeType PerformOperation(NodeType n1, NodeType n2) 
    {
        if(n1.isIdentifierNode)
        {
              //assign value to the variable in the current state
              ((IdentifierNode)n1).AssignValueToIdentifier(n2.ExecuteOperation());
              return new BooleanConstantNode(true);
        }
        else if(n1.isBasicNode)
        {
            //for next operator
            n1 = n1.ExecuteOperation();
            if(n1.isIdentifierNode)
            {
                return new BasicNode(new IassignOperator(), n1, n2);
            }
            else
            {
                return new BooleanConstantNode(false);
            }
        }
        else
        {
            //error: assignment done to a non identifier
            return new BooleanConstantNode(false);
        }
    }
    @Override
    public String GetNodeContents()
    {
        return("Immediate Assignment operator node");
    }
    
}
