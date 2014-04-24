/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables;
/**
 *
 * @author anurag
 */
public abstract class BasicOperator extends NodeType{
protected int operands;


    public BasicOperator() {
        super.isOperator=true;
        
    }
    
    //this will not be required
    @Override
    public NodeType ExecuteOperation()
    {
        return null;
    }
    
    @Override
    public StringBuilder GetFormattedOutput(int level)
    {
        StringBuilder returnStr=new StringBuilder();
        returnStr.append("<BasicOperator>   </BasicOperator>\n");
        return returnStr;
    }
}

