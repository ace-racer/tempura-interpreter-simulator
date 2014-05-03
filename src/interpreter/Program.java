/**
 *
 * @author anurag
 */
package interpreter;
import executionRoutine.*;
import static executionRoutine.GlobalVariables.symbolTables;
import java.io.*;
import internalVariables.*;
import internalVariables.constantNodes.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import symbolTable.*;

public class Program {
    
    public NodeType RootNodeOfExpressionTree;
    
    
    public void ParseFromFile(String fileName) throws FileNotFoundException
    {
      //System.out.println("Parsing file: "+fileName);
      File f=new File(fileName);
      if(f.exists())
       {
          Parser yyparser;
          yyparser = new Parser(new FileReader(fileName));
          yyparser.yyparse();
          this.RootNodeOfExpressionTree=yyparser.RootNodeOfExpressionTree; //root of the expression tree for the prrogram is obtained
       }
      else
       {
	 System.out.println(fileName+" cannot be opened");
       }
      //System.out.println("Parsing over");
    }
    
   
   public void ParseFromText(String tempuraCode) throws FileNotFoundException
   {
       String fileName = "Code.t";
       File f = new File(fileName);
       
       PrintWriter writer = new PrintWriter(f);
       writer.println(tempuraCode);
       writer.close();
       
       this.ParseFromFile(fileName);
       
       f.delete();
   }
    
  public List<List<VariableValuePair> > Simulate(String code) 
  {
      //this is the routine for simulation
      try
      {
        this.ParseFromText(code);
      }
      catch(FileNotFoundException fnfe)
      {
          
      }
      
      boolean isLastState = false;
      BasicNode program = (BasicNode)this.RootNodeOfExpressionTree;
      List<List<VariableValuePair> > allValues = new ArrayList<>();      
      
      while(GlobalVariables.GetStateNumber() < GlobalVariables.GetMaximumStates())
      {
              //get the weak next form  
              NodeType w = program.ExecuteOperation();
              
              if(w.isBasicNode)
              {
                program = (BasicNode)w;
              }
              else if(w.isConstant)
              {
                  //check for termination condition
                  BasicConstant basicConstant = (BasicConstant)w;
                  if(basicConstant.GetConstantType() == Type.BOOL)
                  {
                     boolean value = ((BooleanConstantNode)basicConstant).GetValue();
                     if(value == false)
                     {
                         isLastState = true;
                     }
                  }
              }
              
              if(isLastState)
              {
                  //execute all the statements in the fin operators
                  FinOperands.ExecuteAllFinOperands();
              }
              
              //modified version of this method is called by the paint routine
              List<VariableValuePair> values = GetVariableValues();
              allValues.add(values);
              
              if(isLastState)
              {
                  break;
              }
              
              GlobalVariables.IncrementState();
      }
      //restore all global variables once the simulation is over
      GlobalVariables.RestoreValues();
      return allValues;
  }
   
   public static void main(String args[]) throws IOException 
   {
        Program p = new Program();
        p.Simulate(args[0]);
   }


private void PrintValue(NodeType node)
   {
     if(node==null)
      {
        System.out.print("Node is null");
      } 
     else 
      {
       if(node.isConstant)
         {
           BasicConstant basicConstant=(BasicConstant)node;
           if(basicConstant.GetConstantType()==Type.STRING)
           {
		StringConstantNode stringNode=(StringConstantNode)node;
 		System.out.print("String value is: "+stringNode.GetValue());
           }
           else if(basicConstant.GetConstantType()==Type.INT)
            {
           	IntConstantNode intNode=(IntConstantNode)node;
           	System.out.print("Int value is: "+intNode.GetValue()); 
            }
           else if(basicConstant.GetConstantType()==Type.BOOL)
            {
		BooleanConstantNode boolNode=(BooleanConstantNode)node;
		System.out.print("Boolean value is: "+boolNode.GetValue());
            }
         }
       else
         {
           System.out.print("Value is not constant!");
         }
      }
   }

private List<VariableValuePair> GetVariableValues() 
{
        int stateNumber = GlobalVariables.GetStateNumber();
        
        //System.out.println("State: "+ stateNumber);
        
        List<VariableValuePair> values = new ArrayList<>();
        
        for(Map.Entry<String,NodeType> entry: symbolTables[stateNumber].map.entrySet())
        {
            if(null != entry.getValue() && entry.getValue().isConstant)
            {
                values.add(new VariableValuePair(entry.getKey(), ((IntConstantNode)entry.getValue()).GetValue()));
            }
        }
        return values;
    }
}