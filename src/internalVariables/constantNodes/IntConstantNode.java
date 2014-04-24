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
public class IntConstantNode extends BasicConstant {
    public int value;

    public IntConstantNode(int value) {
        super.constantType=Type.INT;
        this.value=value;
    }
    
    @Override
    public Integer GetValue()
    {
        return this.value;
    }
    
    @Override
    public String GetNodeContents()
    {
        return ("Int constant node");
    }
    
}
