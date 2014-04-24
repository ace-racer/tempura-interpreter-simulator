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
public abstract class NodeType {
    /* holds the number of valid items in the node */
    public boolean isOperator;
    public boolean isConstant;
    public boolean isLanguageConstant;
    public boolean isBasicNode; //whether the node is a tree node
    public boolean isIdentifierNode;
    
    public NodeType() {
        isOperator=false;
        isConstant=false;
        isBasicNode=false;
        isIdentifierNode=false;
        isLanguageConstant=false;
    }
    
     public abstract NodeType ExecuteOperation();
     
     public abstract String GetNodeContents();
     
     public abstract StringBuilder GetFormattedOutput(int level);
    
}
