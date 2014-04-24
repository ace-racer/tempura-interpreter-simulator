/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables.constantNodes;
import internalVariables.NodeType;
import symbolTable.Type;
/**
 *
 * @author anurag
 */
/* contains a constant value*/
public abstract class BasicConstant extends NodeType 
{
    protected Type constantType;
    
    
    public BasicConstant() {
        super.isConstant=true;
    }
    
    public Type GetConstantType()
    {
        return this.constantType;
    }
    
    @Override
    public NodeType ExecuteOperation()
    {
        return this;
    }
    
    public abstract Object GetValue();
    
    @Override
    public StringBuilder GetFormattedOutput(int level)
    {
        return new StringBuilder("<BasicConstantNode>  "+this.GetValue().toString()+"</BasicConstantNode>\n");
    }
    
}

