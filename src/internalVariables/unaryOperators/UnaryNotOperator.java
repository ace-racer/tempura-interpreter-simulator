/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.unaryOperators;

import internalVariables.constantNodes.BooleanConstantNode;
import internalVariables.constantNodes.BasicConstant;
import internalVariables.IdentifierNode;
import internalVariables.NodeType;
import internalVariables.UnaryOperator;
import symbolTable.SymbolTable;
import symbolTable.Type;

/**
 *
 * @author anurag
 */
public class UnaryNotOperator extends UnaryOperator{
    @Override
    public NodeType PerformOperation(NodeType n1)
    {
       BooleanConstantNode booleanConstant = null;
       if(n1.isBasicNode)
       {
           n1 = n1.ExecuteOperation();
       }
       
       if(n1.isConstant && ((BasicConstant)n1).GetConstantType() == Type.BOOL)
       {
           booleanConstant = (BooleanConstantNode)n1;
       }
       
       if(n1.isIdentifierNode)
       {
           NodeType identifierValue = SymbolTable.AccessValueOfVariable(((IdentifierNode)n1).GetIdentifierName());
           if(identifierValue.isConstant && ((BasicConstant)identifierValue).GetConstantType() == Type.BOOL)
              {
                    booleanConstant = (BooleanConstantNode)identifierValue;
              }
       }
       
       if(null != booleanConstant)
       {
           return new BooleanConstantNode(!booleanConstant.GetValue());
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
