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
public class CharConstantNode extends BasicConstant{
    public char value;
    public CharConstantNode(char value)
    {
        super.constantType=Type.CHAR;
        this.value=value;
    }
    
    
    @Override
    public Character GetValue()
    {
        return this.value;
    }
    
    @Override
    public String GetNodeContents()
    {
        return ("Character constant node");
    }
}
