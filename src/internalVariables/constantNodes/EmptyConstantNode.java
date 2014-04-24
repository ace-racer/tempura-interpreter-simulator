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
public class EmptyConstantNode extends LanguageConstant {
    
    @Override
    
    //returns the weak next form of this constant
    public NodeType ExecuteOperation() {
        return new BooleanConstantNode(false);
    }

    @Override
    public String GetNodeContents() {
        return ("Empty constant node");
    }
}
