/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package executionRoutine;

import internalVariables.NodeType;
import java.util.Map;
/**
 *
 * @author anurag
 */
public class GlobalVariables {
    
    private static final int maximumStates;
    private static int stateNumber;
    public static final MapWrapper[] symbolTables;
    
    
    //static block to be executed only once
    static
    {
        
        maximumStates = 1000;
        stateNumber = 0;
        symbolTables =  new MapWrapper[maximumStates+1];
        symbolTables[0] = new MapWrapper();
    }
    
    public static void IncrementState()
    {
        //copy contents of the previous symbol table into the new one that is made      
        stateNumber++;
        
        //create the new symbol table
        symbolTables[stateNumber] = new MapWrapper(); 
        
        //install all the old variables
        for(Map.Entry<String,NodeType> entry: symbolTables[stateNumber-1].map.entrySet())
        {
            symbolTables[stateNumber].map.put(entry.getKey(),null);
        }
    }
    
    public static int GetStateNumber()
    {
        return stateNumber;
    }
    
    public static int GetMaximumStates()
    {
        return maximumStates;
    }
}
