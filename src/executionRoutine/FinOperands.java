/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package executionRoutine;

import internalVariables.*;

/**
 *
 * @author anuneeta
 */
public class FinOperands {
    //maximum number of fin operators
    private static final int finOperators;
    private static NodeType[] finOperands;
    //the index where the next operand will be inserted
    private static int index;
    
    static 
    {
        finOperators = 100;
        index = 0;
        finOperands = new NodeType[finOperators];
    }
    
    public static void AddFinOperand(NodeType opnd)
    {
        finOperands[index] = opnd;
        index++;
    }
    
    public static void ExecuteAllFinOperands()
    {
        for(int i=0; i<index; i++)
        {
            if(null != finOperands[i])
            {
                ((BasicNode)finOperands[i]).ExecuteOperation();
            }
        }
    }
    
    public static void RestoreValues()
    {
      index = 0;
      finOperands = new NodeType[finOperators];  
    }
}
