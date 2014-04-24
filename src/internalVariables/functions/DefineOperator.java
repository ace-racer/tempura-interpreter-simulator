/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.functions;

import internalVariables.BinaryOperator;
import internalVariables.NodeType;

/**
 *
 * @author anurag
 */
public class DefineOperator extends BinaryOperator{

    @Override
    protected NodeType PerformOperation(NodeType n1, NodeType n2) {
        if(n2.isBasicNode)
        {
            return n2.ExecuteOperation();
        }
        else
        {
            return null;
        }
    }

    @Override
    public String GetNodeContents() {
        return("DefineOperator node");
    }
    
}
