package internalVariables.temporalOperators;
import internalVariables.BasicNode;
import internalVariables.NodeType;
import internalVariables.UnaryOperator;
import internalVariables.constantNodes.BasicConstant;
import internalVariables.constantNodes.BooleanConstantNode;
import symbolTable.Type;


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
     if(n1.isConstant)
     {
        return new BooleanConstantNode(false);
     }
     return new BasicNode(new AlwaysOperator(), n1);
 }
 
@Override
public String GetNodeContents()
{
    return("AlwaysOperator node");
}

}