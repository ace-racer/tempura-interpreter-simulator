/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables;

import internalVariables.constantNodes.BooleanConstantNode;
import internalVariables.constantNodes.BasicConstant;
import Errors.TypeMismatch;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import symbolTable.SymbolTable;
import symbolTable.Type;

/**
 *
 * @author anurag
 */
public class OrOperator extends BinaryOperator{
    @Override
    public NodeType PerformOperation(NodeType n1, NodeType n2) 
    {
        if(n1.isBasicNode || n1.isLanguageConstant)
        {
            n1 = n1.ExecuteOperation();
        }
        if(n2.isBasicNode || n2.isLanguageConstant)
        {
            n2 = n2.ExecuteOperation();
        }
        
        BooleanConstantNode value1=null,value2=null;
        if(n1.isConstant)
        {
            if(((BasicConstant)n1).GetConstantType() == Type.BOOL)
            {
                value1 = (BooleanConstantNode)n1;
            }
        }
        if(n1.isIdentifierNode)
        {
            BasicConstant basicConstant = (BasicConstant)SymbolTable.AccessValueOfVariable(((IdentifierNode)n1).GetIdentifierName());
            if(basicConstant.GetConstantType() == Type.BOOL)
            {
                value1 = (BooleanConstantNode)basicConstant;
            }
        }
        
        if(n2.isConstant)
        {
            if(((BasicConstant)n2).GetConstantType() == Type.BOOL)
            {
                value2 = (BooleanConstantNode)n2;
            }
        }
        if(n2.isIdentifierNode)
        {
            BasicConstant basicConstant = (BasicConstant)SymbolTable.AccessValueOfVariable(((IdentifierNode)n2).GetIdentifierName());
            if(basicConstant.GetConstantType() == Type.BOOL)
            {
                value2 = (BooleanConstantNode)basicConstant;
            }
        }
        
        if(value1==null && value2==null)
        {
            return new BasicNode(new OrOperator(), n1, n2);
        }
        else if(value1 == null)
        {
            if(value2.GetValue() == true)
            {
                return new BooleanConstantNode(true);
            }
            else
            {
                return n1;
            }
        }
        else if(value2 == null)
        {
            if(value1.GetValue() == true)
            {
                return new BooleanConstantNode(true);
            }
            else
            {
                return n2;
            }
        }
        //both are boolean constants
        else 
        {
            return new BooleanConstantNode(value1.GetValue() || value2.GetValue());
        }
    }
    @Override
    public String GetNodeContents()
    {
        return("Or operator node");
    }
}
