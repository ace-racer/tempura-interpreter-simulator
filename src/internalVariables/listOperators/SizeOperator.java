/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.listOperators;

import internalVariables.NodeType;
import internalVariables.UnaryOperator;

/**
 *
 * @author anurag
 */
public class SizeOperator extends UnaryOperator {

    @Override
    public NodeType PerformOperation(NodeType n1) {
        return null;
    }

    @Override
    public String GetNodeContents() {
        return("List SizeOperator node");
    }
    
}
