/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.constantNodes;
import symbolTable.Type;
/**
 *
 * @author anurag
 */
public class BooleanConstantNode extends BasicConstant 
{
    
    public boolean value;
     
    public BooleanConstantNode(boolean value) 
    {
        this.value = value;
        super.constantType=Type.BOOL;
    }
        
    @Override
    public Boolean GetValue()
    {
        return this.value;
    }
    
    @Override
    public String GetNodeContents()
    {
        return("Boolean constant node");
    }
}
