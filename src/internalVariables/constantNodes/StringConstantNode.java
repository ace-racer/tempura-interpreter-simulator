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
public class StringConstantNode extends BasicConstant {
    public String value;

    public StringConstantNode(String value) {
        super.constantType=Type.STRING;
        this.value = value;
    }
    
    @Override
    public String GetValue()
    {
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<this.value.length();i++)
        {
            if(this.value.charAt(i)!='>' && this.value.charAt(i)!='<')
            {
                stringBuilder.append(this.value.charAt(i));
            }
        }
        return stringBuilder.toString();
    }
    
    @Override
    public String GetNodeContents()
    {
        return("String constant node");
    }
}
