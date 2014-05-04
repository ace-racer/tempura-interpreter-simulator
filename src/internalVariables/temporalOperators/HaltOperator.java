package internalVariables.temporalOperators;
import internalVariables.BasicNode;
import internalVariables.NodeType;
import internalVariables.UnaryOperator;
import internalVariables.constantNodes.BooleanConstantNode;

 //author Anurag

public class HaltOperator extends UnaryOperator
{
 @Override
 public NodeType PerformOperation(NodeType n1)
 { 
   //if the relation evaluates to true then terminate execution
   //else continue  
   if(n1.isBasicNode)  
   {
       NodeType retValue = ((BasicNode)n1).ExecuteOperation();
       if(retValue.isConstant)
       {
           boolean value = ((BooleanConstantNode)retValue).GetValue();
           if(value == true)
           {
               //the execution has to halt
               return new BooleanConstantNode(false);
           }
       }
   }
   return new BasicNode(new HaltOperator(), n1);
 }
 
@Override
public String GetNodeContents()
{
return("HaltOperator node");
}  
}