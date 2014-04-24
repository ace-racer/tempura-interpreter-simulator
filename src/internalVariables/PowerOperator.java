/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables;

import internalVariables.constantNodes.IntConstantNode;
import symbolTable.SymbolTable;

/**
 *
 * @author anurag
 */
public class PowerOperator extends BinaryOperator{
    @Override
    public NodeType PerformOperation(NodeType n1, NodeType n2) 
    {
        if(n1.isBasicNode)
        {
          n1 = n1.ExecuteOperation();
        }
        
        if(n2.isBasicNode)
        {
            n2 = n2.ExecuteOperation();
        }
        //get the values of n1 and n2 from the symbol table
        //add them
        //return the result
        IntConstantNode intConst1 = null, intConst2 = null;
        if(n1.isIdentifierNode)
        {
            intConst1 = (IntConstantNode)SymbolTable.AccessValueOfVariable(((IdentifierNode)n1).GetIdentifierName());
        }
        else if(n1.isConstant)
        {
            intConst1 = (IntConstantNode)n1;
        }
        
        if(n2.isIdentifierNode)
        {
            intConst2 = (IntConstantNode)SymbolTable.AccessValueOfVariable(((IdentifierNode)n2).GetIdentifierName());
        }
        else if(n2.isConstant)
        {
            intConst2 = (IntConstantNode)n2;
        }
        
        if(intConst1 != null && intConst2 != null)
        {
            return new IntConstantNode(GetPower(intConst1.GetValue(), intConst2.GetValue()));
        }
        else
        {
            return new BasicNode(new PowerOperator(), n1, n2);
        }
    }

    private int GetPower(int base, int exponent) 
    {
        if(exponent==0)
        {
            return 1;
        }
        else
        {
            //if exponent is even
            if(exponent%2==0)
            {
                int powerVal=GetPower(base,exponent/2);
                return powerVal*powerVal;
            }
            //if exponent is odd
            else
            {
                return base*GetPower(base, exponent-1);
            }
        }
    }
    
    @Override
    public String GetNodeContents()
    {
        return("Power operator node");
    }
}
