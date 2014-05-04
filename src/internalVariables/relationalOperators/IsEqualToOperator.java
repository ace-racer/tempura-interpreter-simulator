/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.relationalOperators;

import internalVariables.constantNodes.StringConstantNode;
import internalVariables.constantNodes.IntConstantNode;
import internalVariables.constantNodes.BooleanConstantNode;
import internalVariables.constantNodes.BasicConstant;
import Errors.TypeMismatch;
import internalVariables.*;
import symbolTable.SymbolTable;
import symbolTable.Type;

/**
 *
 * @author anurag
 */
public class IsEqualToOperator extends BinaryOperator{
    
    @Override
    public NodeType PerformOperation(NodeType n1, NodeType n2) 
    {
        IntConstantNode intConstant1 = null,intConstant2 = null;
        BooleanConstantNode boolConstant1 = null,boolConstant2 = null;
        if(n1.isConstant)
        {
            if(((BasicConstant)n1).GetConstantType() == Type.INT)
            {
                intConstant1 = (IntConstantNode)n1;
            }
            if(((BasicConstant)n1).GetConstantType() == Type.BOOL)
            {
                boolConstant1 = (BooleanConstantNode)n1;
            }
        }
        
        if(n2.isConstant)
        {
            if(((BasicConstant)n2).GetConstantType() == Type.INT)
            {
                intConstant2 = (IntConstantNode)n2;
            }
            
            if(((BasicConstant)n2).GetConstantType() == Type.BOOL)
            {
                boolConstant2 = (BooleanConstantNode)n2;
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
            if(basicConstant != null && basicConstant.GetConstantType() == Type.BOOL)
            {
                boolConstant1 = (BooleanConstantNode)basicConstant;
            }
        }
        
        
        if(n2.isIdentifierNode)
        {
            basicConstant = (BasicConstant)SymbolTable.AccessValueOfVariable(((IdentifierNode)n2).GetIdentifierName());
            if(basicConstant != null && basicConstant.GetConstantType() == Type.BOOL)
            {
                boolConstant2 = (BooleanConstantNode)basicConstant;
            }
        }
        
        if(intConstant1!=null && intConstant2!=null)
        {
            return new BooleanConstantNode(intConstant1.GetValue()== intConstant2.GetValue());
        }
        else if(boolConstant1!=null && boolConstant2!=null)
        {
            return new BooleanConstantNode(boolConstant1.GetValue() == boolConstant2.GetValue());
        }
        else
        {
            return new BooleanConstantNode(false);
        }
    }
    
    @Override
    public String GetNodeContents()
    {
        return("IsEqualTo operator node");
    }
}
