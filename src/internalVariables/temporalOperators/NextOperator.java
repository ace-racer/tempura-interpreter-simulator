package internalVariables.temporalOperators;
import internalVariables.NodeType;
import internalVariables.UnaryOperator;


 //author Anurag

 public class NextOperator extends UnaryOperator
{
 @Override
 public NodeType PerformOperation(NodeType n1)
 { 
     return n1; 
 }

@Override
public String GetNodeContents()
  {
    return ("NextOperator node");
  }  
}