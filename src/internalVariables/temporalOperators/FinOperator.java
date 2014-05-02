package internalVariables.temporalOperators;
import executionRoutine.FinOperands;
import internalVariables.NodeType;
import internalVariables.UnaryOperator;
import internalVariables.constantNodes.BooleanConstantNode;


 //author Anurag

 public class FinOperator extends UnaryOperator
{
 @Override
 public NodeType PerformOperation(NodeType n1)
 { 
     //add the operand to the list of operands of fin that will 
     //be executed at the last state
     FinOperands.AddFinOperand(n1);
     return new BooleanConstantNode(true);
 }
 
@Override
public String GetNodeContents()
{
return("FinOperator node");
}

}