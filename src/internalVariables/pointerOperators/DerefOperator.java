/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.pointerOperators;

import internalVariables.NodeType;
import internalVariables.UnaryOperator;

/**
 *
 * @author anurag
 */
public class DerefOperator extends UnaryOperator{

    @Override
    public NodeType PerformOperation(NodeType n1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String GetNodeContents() {
        return("Pointer DerefOperator node");
    }
    
}
