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
public abstract class TernaryOperator extends BasicOperator{

    public TernaryOperator() {
        super.operands=3;
    }
    
    protected abstract NodeType PerformOperation(NodeType n1, NodeType n2,NodeType n3);
    
}