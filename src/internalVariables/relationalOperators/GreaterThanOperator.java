/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.relationalOperators;

import Errors.TypeMismatch;
import internalVariables.constantNodes.BasicConstant;
import internalVariables.BasicNode;
import internalVariables.BinaryOperator;
import internalVariables.IdentifierNode;
import internalVariables.constantNodes.BooleanConstantNode;
import internalVariables.constantNodes.IntConstantNode;
import internalVariables.NodeType;
import internalVariables.constantNodes.StringConstantNode;
import symbolTable.SymbolTable;
import symbolTable.Type;

/**
 *
 * @author anurag
 */
public class GreaterThanOperator extends BinaryOperator {
    @Override
    public NodeType PerformOperation(NodeType n1, NodeType n2) 
    {
        
        IntConstantNode intConstant1 = null,intConstant2 = null;
        if(n1.isConstant)
        {
            if(((BasicConstant)n1).GetConstantType() == Type.INT)
            {
                intConstant1 = (IntConstantNode)n1;
            }
        }
        if(n2.isConstant)
        {
            if(((BasicConstant)n2).GetConstantType() == Type.INT)
            {
                intConstant2 = (IntConstantNode)n2;
            }
        }
        
        BasicConstant basicConstant;
        if(n1.isIdentifierNode)
        {
            basicConstant = (BasicConstant)SymbolTable.AccessValueOfVariable(((IdentifierNode)n1).GetIdentifierName());
            if(basicConstant != null && basicConstant.GetConstantType() == Type.INT)
            {
                intConstant1 = (IntConstantNode)basicConstant;
            }
        }
        
        
        if(n2.isIdentifierNode)
        {
            basicConstant = (BasicConstant)SymbolTable.AccessValueOfVariable(((IdentifierNode)n2).GetIdentifierName());
            if(basicConstant != null && basicConstant.GetConstantType() == Type.INT)
            {
                intConstant2 = (IntConstantNode)basicConstant;
            }
        }
        
        if(intConstant1!=null && intConstant2!=null)
        {
            return new BooleanConstantNode(intConstant1.GetValue()>intConstant2.GetValue());
        }
        else
        {
            return new BooleanConstantNode(false);
        }
    }
    
    @Override
    public String GetNodeContents()
    {
        return("GreaterThan operator node");
    }
}
