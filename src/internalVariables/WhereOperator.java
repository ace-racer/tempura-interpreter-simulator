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
public class WhereOperator extends BinaryOperator {

    @Override
    protected NodeType PerformOperation(NodeType n1, NodeType n2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String GetNodeContents() {
      return("WhereOperator node");
    }
    
}
