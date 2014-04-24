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
public class ExistsOperator extends BinaryOperator{

    @Override
    protected NodeType PerformOperation(NodeType n1, NodeType n2) 
    {    
        return n2.ExecuteOperation();
    }

    @Override
    public String GetNodeContents() {
        return("ExistsOperator node");
    }
    
}
