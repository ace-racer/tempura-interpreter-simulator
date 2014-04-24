/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.relationalOperators;

import internalVariables.BinaryOperator;
import internalVariables.constantNodes.BooleanConstantNode;
import internalVariables.NodeType;
import internalVariables.OrOperator;

/**
 *
 * @author anurag
 */
public class GreaterThanEqualToOperator extends BinaryOperator{
    @Override
    public NodeType PerformOperation(NodeType n1, NodeType n2) 
    {
        return new BooleanConstantNode(((BooleanConstantNode)(new OrOperator().PerformOperation(new GreaterThanOperator().PerformOperation(n1, n2),new IsEqualToOperator().PerformOperation(n1, n2)))).value);
    }
    
    @Override
    public String GetNodeContents()
    {
        return("GreaterThanEqualTo operator node");
    }
}
