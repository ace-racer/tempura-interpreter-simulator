/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables;
import symbolTable.*;
/**
 *
 * @author anurag
 */
public class IdentifierNode extends NodeType{
    
    private final String identifierName;
    protected boolean isStatic;
    protected boolean isList;
    
    public IdentifierNode(String identifierName)
    {
        super.isIdentifierNode=true;
        this.identifierName=identifierName;
    }
    
    public NodeType AccessValueOfIdentifier()
    {
        return SymbolTable.AccessValueOfVariable(this.identifierName);
    }
    
    public void InstallIdentifier()
    {
        SymbolTable.InstallVariable(this.identifierName);
    }
   
    public void AssignValueToIdentifier(NodeType value)
    {
        SymbolTable.AssignValueToVariable(this.identifierName, value);
    }
    
    public String GetIdentifierName()
    {
        return this.identifierName;
    }
    
    @Override
    public NodeType ExecuteOperation()
    {
        //if this variable has value assigned to it then return the value
        return AccessValueOfIdentifier();
    }
    
    @Override
    public String GetNodeContents()
    {
        return("Identifier node");
    }
    
    @Override
    public StringBuilder GetFormattedOutput(int level)
    {
        return new StringBuilder("<Identifier Node>  "+this.identifierName+"</Identifier Node>\n");
    }
}
