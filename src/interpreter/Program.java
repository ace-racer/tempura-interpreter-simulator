//package interpreter_new;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anurag
 */
package interpreter;
import executionRoutine.GlobalVariables;
import static executionRoutine.GlobalVariables.symbolTables;
import java.io.*;
import internalVariables.*;
import internalVariables.constantNodes.*;
import java.util.Map;
import symbolTable.*;

public class Program {
    
    public NodeType RootNodeOfExpressionTree;
    
    
    public void ParseFromFile(String fileName) throws FileNotFoundException
    {
      System.out.println("Parsing file: "+fileName);
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
      System.out.println("Parsing over");
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
    
  public void Simulate(String fileName) 
  {
      //this is the routine for simulation
      try
      {
        this.ParseFromFile(fileName);
      }
      catch(FileNotFoundException fnfe)
      {
          
      }
      BasicNode program = (BasicNode)this.RootNodeOfExpressionTree;
      
      while(GlobalVariables.GetStateNumber() < GlobalVariables.GetMaximumStates())
      {
              //get the weak next form  
              NodeType w = program.ExecuteOperation();
              PrintVariableValues();
              
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
                         break;
                     }
                  }
              }
              
              GlobalVariables.IncrementState();
      }        
      
  }
   
   public static void TestParsing(String fileName) throws FileNotFoundException
   {
       Program p = new Program();
    System.out.println("Parsing started");
    
         // parse a file
        p.ParseFromFile(fileName);
        
        //RootNodeOfExpressionTree.GetNodeContents();
        String formattedOutput=(p.RootNodeOfExpressionTree.GetFormattedOutput(0).toString());
        
        //the current directory where the created file is stored
        //System.out.println("Current directory: "+System.getProperty("user.dir"));
        
        File f=new File("ProgramExpressionTree1.xml");
        try (PrintWriter out = new PrintWriter(f)) {
            out.println(formattedOutput);
        }
        
        
        String tempuraCode="/* run */define test_2() = \n" +
        "{ exists I:\n" +
        "  {\n" +
        "     I=0 and I gets I+1 and always output I and len 5\n" +
        "  }\n" +
        "}.";
        p.ParseFromText(tempuraCode);
        formattedOutput = (p.RootNodeOfExpressionTree.GetFormattedOutput(0).toString());
        f=new File("ProgramExpressionTree2.xml");
        try (PrintWriter out = new PrintWriter(f)) {
            out.println(formattedOutput);
        }
   }
   
    public static void main(String args[]) throws IOException {
     
        /*
        if(args.length > 0)
        {
            TestParsing(args[0]);
        }
        */
        
        
        Program p = new Program();
        p.Simulate(args[0]);
        
        /*
        ((BasicNode)p.RootNodeOfExpressionTree).SetUpTerminationCondition();
        
        if(GlobalVariables.isTerminationCriterionSpecified)
            System.out.println("Yaay");
        else
            System.out.println("Noo");    
        */        
    
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

private void PrintVariableValues() {
    
        int stateNumber = GlobalVariables.GetStateNumber();
        System.out.println("State: "+ stateNumber);
        
        for(Map.Entry<String,NodeType> entry: symbolTables[stateNumber].map.entrySet())
        {
            if(null != entry.getValue() && entry.getValue().isConstant)
            {
                System.out.println("Variable: "+entry.getKey()+" Value: "+((IntConstantNode)entry.getValue()).GetValue());
            }
        }
    }
}
