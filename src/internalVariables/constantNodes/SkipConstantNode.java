/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.constantNodes;

import internalVariables.NodeType;

/**
 *
 * @author anurag
 */
public class SkipConstantNode extends LanguageConstant{

    @Override
    public NodeType ExecuteOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String GetNodeContents() {
        return ("Skip constant node");
    }
    
    
    
}
