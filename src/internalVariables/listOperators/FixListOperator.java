/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.listOperators;

import internalVariables.BinaryOperator;
import internalVariables.NodeType;

/**
 *
 * @author anurag
 */
public class FixListOperator extends BinaryOperator{

    @Override
    protected NodeType PerformOperation(NodeType n1, NodeType n2) {
        return null;
    }

    @Override
    public String GetNodeContents() {
        return("FixListOperator node");
    }
    
}
