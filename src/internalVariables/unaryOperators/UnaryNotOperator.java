/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.unaryOperators;

import internalVariables.constantNodes.BooleanConstantNode;
import internalVariables.constantNodes.BasicConstant;
import Errors.OperandTypeIncorrect;
import internalVariables.BasicNode;
import internalVariables.NodeType;
import internalVariables.UnaryOperator;
import symbolTable.Type;

/**
 *
 * @author anurag
 */
public class UnaryNotOperator extends UnaryOperator{
    @Override
    public NodeType PerformOperation(NodeType n1)
    {
        if(n1.isConstant)
        {
            BasicConstant basicConstant=(BasicConstant)n1;
            if(basicConstant.GetConstantType()==Type.BOOL)
            {
                BooleanConstantNode booleanConstantNode=(BooleanConstantNode)basicConstant;
                return new BooleanConstantNode(!(booleanConstantNode.value));
            }
            else
            {
                OperandTypeIncorrect.PrintErrorMessage();
                return null;
            }
        }
        else if(n1.isBasicNode)
        {
            NodeType basicConstant=((BasicNode)n1).ExecuteOperation();
            if(((BasicConstant)basicConstant).GetConstantType()==Type.BOOL)
            {
                BooleanConstantNode booleanConstantNode=(BooleanConstantNode)basicConstant;
                return PerformOperation(booleanConstantNode);
            }
            else
            {
                OperandTypeIncorrect.PrintErrorMessage();
                return null;
            }
        }
        else
        {
            return null;
        }
    }
  @Override
    public String GetNodeContents()
    {
        return("Unary not operator node");
    }  
}
