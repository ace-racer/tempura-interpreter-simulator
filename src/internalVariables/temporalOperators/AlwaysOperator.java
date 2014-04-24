package internalVariables.temporalOperators;
import internalVariables.BasicNode;
import internalVariables.NodeType;
import internalVariables.UnaryOperator;


//author Anurag
public class AlwaysOperator extends UnaryOperator
{    
 @Override
 public NodeType PerformOperation(NodeType n1)
 { 
     if(n1.isBasicNode)
     {
         ((BasicNode)n1).ExecuteOperation();
     }
     return new BasicNode(new AlwaysOperator(), n1);
 }
 
@Override
public String GetNodeContents()
{
    return("AlwaysOperator node");
}

}