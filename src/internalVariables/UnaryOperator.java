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
public abstract class UnaryOperator extends BasicOperator{

    public UnaryOperator() {
        super.operands=1;
    }
    
    public abstract NodeType PerformOperation(NodeType n1);
    
}
