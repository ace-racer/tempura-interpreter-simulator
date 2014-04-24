package internalVariables.temporalOperators;
import internalVariables.BasicNode;
import internalVariables.NodeType;
import internalVariables.BinaryOperator;


 //author Anurag

public class GetsOperator extends BinaryOperator
{
 @Override
 public NodeType PerformOperation(NodeType n1, NodeType n2)
 { 
     return new BasicNode(new AlwaysOperator(), new BasicNode(new IassignOperator(), n1,n2));
 }
 
 
@Override
public String GetNodeContents()
{
return ("GetsOperator node");
}  
}