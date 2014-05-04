/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables;

import internalVariables.constantNodes.BooleanConstantNode;

/**
 *
 * @author anurag
 */
public class IfOperator extends TernaryOperator {
    @Override
    public NodeType PerformOperation(NodeType n1, NodeType n2,NodeType n3) 
    {
        try
        {
            BooleanConstantNode booleanConstant = (BooleanConstantNode)n1.ExecuteOperation();
            if(booleanConstant.GetValue() == true)
            {
              n2.ExecuteOperation();
            }
            else
            {
                n3.ExecuteOperation();
            }
            return new BooleanConstantNode(true);
        }
        catch(ClassCastException cce)
        {
            Errors.OperandTypeIncorrect.PrintErrorMessage();
            return null;
        }
    }
    
    @Override
    public String GetNodeContents()
    {
       return("If loop node");
    }
    
}
