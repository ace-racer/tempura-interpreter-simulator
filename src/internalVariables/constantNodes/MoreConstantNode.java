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
public class MoreConstantNode extends LanguageConstant{
    
    @Override
    public NodeType ExecuteOperation() 
    {
        return new BooleanConstantNode(true);
    }

    @Override
    public String GetNodeContents() {
        return ("More constant node");
    }
}
