/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interpreter;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anuneeta
 */
public class ProgramTest {
    
    public ProgramTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSimulateFinKeep() {
       
        System.out.println("Input tempura file has fin and keep operators");
        String code="/* run */define test_2() = \n" +
"       { exists I:\n" +
"       {\n" +
"           len(5) and keep {I=0} and fin {I=100} and always output I\n" +
"       }\n"     +
"       }.";
        Program instance = new Program();
        List<List<VariableValuePair> > returnedValues = instance.Simulate(code);
        
        //whether the number of states is 6
        assertEquals(6, returnedValues.size());
        
        for(int i=0; i<5;i++)
        {
            List<VariableValuePair> values = returnedValues.get(i);
            assertEquals("I", values.get(0).VariableName);
            assertEquals(0, values.get(0).value);
        }
        
        //value at the last state changes
        assertEquals("I", returnedValues.get(5).get(0).VariableName);
        assertEquals(100, returnedValues.get(5).get(0).value);
    }

    @Test
    public void testSimulateGetsHalt()
    {
       System.out.println("Input tempura file has gets and halt operators");
       String code="/* run */define test_2() = \n" +
"{ exists I:\n" +
"  {\n" +
"     I=0 and I gets I+2 and halt(I == 10)\n" +
"  }\n" +
"}.";
        Program instance = new Program();
        List<List<VariableValuePair> > returnedValues = instance.Simulate(code);
        
        //whether the number of states is 6
        assertEquals(6, returnedValues.size());
        
        for(int i=0; i<6;i++)
        {
            List<VariableValuePair> values = returnedValues.get(i);
            assertEquals("I", values.get(0).VariableName);
            assertEquals(i*2, values.get(0).value);
        }
    }
    
    @Test
    public void testSimulateNextEmpty()
    {
       System.out.println("Input tempura file has next operator and empty temporal constant");
        String code="/* run */define myFunc() = \n" +
"{ exists I,J:\n" +
"  {\n" +
"     ( next next empty) and (I = 0) and (I gets I + 1) and always(J = 2*I)\n" +
"  }\n" +
"}.";
        Program instance = new Program();
        List<List<VariableValuePair> > returnedValues = instance.Simulate(code);
        
        //whether the number of states is 3
        assertEquals(3, returnedValues.size());
        
        for(int i=0; i<3;i++)
        {
            List<VariableValuePair> values = returnedValues.get(i);
            assertEquals("I", values.get(0).VariableName);
            assertEquals(i, values.get(0).value);
            assertEquals("J", values.get(1).VariableName);
            assertEquals(2*i, values.get(1).value);
        }
    }
    
    @Test
    public void testSimulateNextLen()
    {
       System.out.println("Input tempura file has next and len operators");
       String code="/* run */define myFunc() = \n" +
"{ exists I,J:\n" +
"  {\n" +
"     J=2 and (next I) = J and len 5 \n" +
"  }\n" +
"}.";
        Program instance = new Program();
        List<List<VariableValuePair> > returnedValues = instance.Simulate(code);
        
        //whether the number of states is 6
        assertEquals(6, returnedValues.size());

        assertEquals("J", returnedValues.get(0).get(0).VariableName);
        assertEquals(2, returnedValues.get(0).get(0).value);
        
        assertEquals("I", returnedValues.get(1).get(0).VariableName);
        assertEquals(2, returnedValues.get(1).get(0).value);
    }
    
    @Test
    public void testSimulateGetsLen()
    {
       System.out.println("Input tempura file has gets and len operators");
       String code="/* run */define test_2() = \n" +
"{ exists I:\n" +
"  {\n" +
"     I=0 and I gets I+2 and len 5\n" +
"  }\n" +
"}.";
        Program instance = new Program();
        List<List<VariableValuePair> > returnedValues = instance.Simulate(code);
        
        //whether the number of states is 6
        assertEquals(6, returnedValues.size());
        
        for(int i=0; i<6;i++)
        {
            List<VariableValuePair> values = returnedValues.get(i);
            assertEquals("I", values.get(0).VariableName);
            assertEquals(i*2, values.get(0).value);
        }
        
    }
}
