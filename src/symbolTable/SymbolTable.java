/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package symbolTable;
import java.util.*;
import Errors.*;
import executionRoutine.*;
import internalVariables.*;
/**
 *
 * This class contains all hash table operations and the hash table itself
 */
public final class SymbolTable 
{
     /**
     * @param varName name of the variable which is to be installed
     */
    public static void InstallVariable(String varName)
    {
        GlobalVariables.symbolTables[GlobalVariables.GetStateNumber()].map.put(varName,null);        
    }
    
    public static void AssignValueToVariable(String varName,NodeType value)
    {
        if(value != null)
        {
            Map<String,NodeType> symbolTable = GlobalVariables.symbolTables[GlobalVariables.GetStateNumber()].map;      
            
            //check if the variable name exists in the hash table
            if(symbolTable.containsKey(varName))
            {
                if(value.isIdentifierNode)
                {
                   IdentifierNode identifierNode = (IdentifierNode)value;
                   String identifierName = identifierNode.GetIdentifierName();
                   if(symbolTable.containsKey(identifierName))
                   {
                       NodeType identifierValue = AccessValueOfVariable(identifierName);
                       symbolTable.put(varName, identifierValue);
                   }
                   else
                   {
                       VariableNotDeclared.PrintErrorMessage();
                   }
                }
                
                //Assign the new value to the variable
                symbolTable.put(varName, value);
            }
            else
            {
                //error symbol not present in symbol table and hence value cannot be assigned
                VariableNotDeclared.PrintErrorMessage();
            }
        }
    }
    
   public static NodeType AccessValueOfVariable(String varName)
    {
        if(varName!=null && !varName.isEmpty())
        {
             NodeType node = GlobalVariables.symbolTables[GlobalVariables.GetStateNumber()].map.get(varName);
             if(null != node)
             {
                return node;
             }
             else
             {
                 if(GlobalVariables.GetStateNumber()>0)
                 {
                     //access value from the previous state
                     node = GlobalVariables.symbolTables[GlobalVariables.GetStateNumber()-1].map.get(varName);
                     return node;
                 }
                     
             }
        }
        return null;
    }
}
