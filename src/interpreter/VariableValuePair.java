/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interpreter;

/**
 *
 * @author anuneeta
 */
public class VariableValuePair {
    public String VariableName;
    public int value;

    public VariableValuePair(String variableName, int value) 
    {
      this.VariableName = variableName;
      this.value = value;
    }
    
}
