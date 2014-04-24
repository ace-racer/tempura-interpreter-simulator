/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.listOperators;

import internalVariables.NodeType;
import internalVariables.TernaryOperator;

/**
 *
 * @author anurag
 */
public class SubList extends TernaryOperator {

    @Override
    protected NodeType PerformOperation(NodeType n1, NodeType n2, NodeType n3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String GetNodeContents() {
        return("Sub script Operator node");
    }
    
}
