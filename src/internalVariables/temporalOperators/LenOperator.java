package internalVariables.temporalOperators;
import Errors.OperandTypeIncorrect;
import internalVariables.NodeType;
import internalVariables.UnaryOperator;
import internalVariables.constantNodes.*;
import symbolTable.Type;
import internalVariables.*;
//author Anurag
public class LenOperator extends UnaryOperator
{
 @Override
 public NodeType PerformOperation(NodeType n1)
 { 
     if(n1.isConstant)
     {
         BasicConstant basicConstant = (BasicConstant)n1;
         if(basicConstant.GetConstantType()==Type.INT)
         {
             int value = ((IntConstantNode)basicConstant).GetValue();
             if(value == 0)
             {
                 return new BooleanConstantNode(false);
             }
             else
             {
                 return new BasicNode(new LenOperator(),new IntConstantNode(value-1));
             }
         }
         else
         {
             return new BooleanConstantNode(false);
         }
     }
     else
     {
         //wrong operand error
         OperandTypeIncorrect.PrintErrorMessage();
         return new BooleanConstantNode(false);
     }
 }
 
@Override
public String GetNodeContents()
  {
    return ("LenOperator node");
  }  

}