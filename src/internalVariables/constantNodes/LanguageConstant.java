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
public abstract class LanguageConstant extends NodeType
{

    public LanguageConstant() {
        super.isLanguageConstant=true;
    }
    
     @Override
    public StringBuilder GetFormattedOutput(int level)
    {
        return new StringBuilder("<LanguageConstantNode>  </LanguageConstantNode>\n");
    }
    
}
