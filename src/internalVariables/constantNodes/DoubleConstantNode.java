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
public class DoubleConstantNode extends BasicConstant {
   double value;
   public DoubleConstantNode(double value)
   {
       super.constantType=Type.DOUBLE;
       this.value=value;
   }
   
   
   @Override
   public Double GetValue()
   {
       return this.value;
   }
   
   @Override
    public String GetNodeContents()
    {
        return ("Double constant node");
    }
}
