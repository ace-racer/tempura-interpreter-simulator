/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sscsimulator;

/**
 *
 * @author subhasish
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import interpreter.*;

public class gui {

     //Components Used So Far
 JFrame jfrm;
 JPanel jInput, jParameter, jTextOutput, jGridOutput;
 PaintGrid jGrid;
 TitledBorder titleInput;
 //Required Labels 
 JLabel jParamEnter,jInputFile;
 //Required Text Field 
 JTextField jtfParameter,jtfFileInput;
 //Buttons
 JButton jbtnParameter,jbtnBrowseFile,jRun;
 //File Browser
 JFileChooser jfcInput;
 //Text area
 JTextArea jtaCode,jtaTextOutput; 
 //Box Layout Manager
 Box box1,box2; //Main Boxes
 Box panelBox;
 Box inputBox1,inputBox2;
 Box parameterBox1,parameterBox2;
 Box textOutput;
 
 /*Interpreter*/
Program p;
String code;
 gui(){
   jfrm = new JFrame("SSC Simulator");
   jfrm.getContentPane().setLayout(new FlowLayout());
   jfrm.setSize(1000,600);
   jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   //Input Panel
   titleInput = BorderFactory.createTitledBorder("Input");
   jInput = new JPanel();
   jInput.setPreferredSize(new Dimension(300,300));
   jInput.setOpaque(true);
   jInput.setBorder(titleInput);
   panelBox = Box.createVerticalBox();
   inputBox1 = Box.createHorizontalBox();
   inputBox2 = Box.createVerticalBox();
   //"Enter file location: " Label
   jInputFile = new JLabel("Enter file location: ");
   jInput.add(jInputFile);
   //File Browser (1) Text field
   jtfFileInput = new JTextField(15);
   inputBox1.add(jtfFileInput);
   //File Browser (2) Browse Button => need to add filechooser
   jfcInput = new JFileChooser();
   jbtnBrowseFile = new JButton("Browse");
   inputBox1.add(jbtnBrowseFile);
   
   
   //Text area where code will be shown
   jtaCode = new JTextArea();
   jtaCode.setLineWrap(true);
   jtaCode.setWrapStyleWord(true);
   JScrollPane jscrlpCode = new JScrollPane(jtaCode);
   jscrlpCode.setPreferredSize(new Dimension(180,130));
   inputBox2.add(jscrlpCode);
   
   /*ActionListener for Browse Button*/
   jbtnBrowseFile.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent le){
      int result = jfcInput.showOpenDialog(null);
      if(result==JFileChooser.APPROVE_OPTION)
        jtfFileInput.setText(jfcInput.getSelectedFile().getName());
        File inputFile = jfcInput.getSelectedFile();
        if(!inputFile.exists()){
         JOptionPane.showMessageDialog(null,"Please enter a valid input file.","File not found",JOptionPane.WARNING_MESSAGE);
         return;
        }
        
        InputStream inputFileBuffer = null;
         try {
             inputFileBuffer = new FileInputStream(jfcInput.getSelectedFile());
         } catch (FileNotFoundException ex) {
             Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
         }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputFileBuffer));
        StringBuilder out = new StringBuilder();
        String line;
         try {
             while ((line = reader.readLine()) != null) {
                 out.append(line+"\n");
             }} catch (IOException ex) {
             Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
         }
        code = out.toString();   //Prints the string content read from input stream
         try {
             reader.close();
         } catch (IOException ex) {
             Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        /*String program will be sent to parser*/
         jtaCode.setText(code);
         
       }
     }
   );   
    
   //"Run" Button
   jRun = new JButton("Run");
   jRun.addActionListener(new ActionListener() {
       
       @Override
       public void actionPerformed(ActionEvent le){
         p=new Program();
         try {
             p.ParseFromText(code);
         } catch (FileNotFoundException ex) {
             Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
         }
          String formattedOutput = (p.RootNodeOfExpressionTree.GetFormattedOutput(0).toString());
        File f=new File("ProgramExpressionTree2.xml");
        try (PrintWriter printOut = new PrintWriter(f)) {
            printOut.println(formattedOutput);
        } catch (FileNotFoundException ex) {
             Logger.getLogger(gui.class.getName()).log(Level.SEVERE, null, ex);
         }
       }
   });
        
   
   
   
   
   inputBox2.add(Box.createRigidArea(new Dimension(5,8)));
   inputBox2.add(jRun);
   panelBox.add(inputBox1);
   panelBox.add(Box.createRigidArea(new Dimension(10,20)));
   panelBox.add(inputBox2);
   jInput.add(panelBox);
   //End of Input Panel


   //Parameter Panel
   titleInput = BorderFactory.createTitledBorder("Parameter");
   jParameter = new JPanel();
   jParameter.setPreferredSize(new Dimension(300,200));
   jParameter.setOpaque(true);
   jParameter.setBorder(titleInput);
   panelBox = Box.createVerticalBox();
   parameterBox1 = Box.createHorizontalBox();
   parameterBox2 = Box.createVerticalBox();
   //"Enter value: " Label
   jParamEnter = new JLabel("Enter value: ");
   parameterBox1.add(jParamEnter);
   //"Please put the parameter value" Text Field
   jtfParameter = new JTextField("Please put the parameter value", 20);
   parameterBox2.add(jtfParameter);
   //"Continue" Button
   jbtnParameter = new JButton("Continue");
   parameterBox2.add(Box.createRigidArea(new Dimension(10,20)));
   parameterBox2.add(jbtnParameter);
   panelBox.add(parameterBox1);
   panelBox.add(parameterBox2);
   jParameter.add(panelBox);   
   //End of Parameter Panel

   //Grid
   jGridOutput = new JPanel();
   jGridOutput.setPreferredSize(new Dimension(500, 300));
   titleInput = BorderFactory.createTitledBorder("Graphical Output");
   jGridOutput.setBorder(titleInput);
   jGridOutput.setLayout(new FlowLayout());
   jGrid = new PaintGrid();
   //jGrid.repaint();   
   jGridOutput.add(jGrid);
   //Text output panel
   jTextOutput = new JPanel();
   jTextOutput.setPreferredSize(new Dimension(500, 100));
   jtaTextOutput = new JTextArea();
   jtaTextOutput.setLineWrap(true);
   jtaTextOutput.setWrapStyleWord(true);
   JScrollPane jsclpOutput = new JScrollPane(jtaTextOutput);
   jsclpOutput.setPreferredSize(new Dimension(500, 100));
   textOutput = Box.createHorizontalBox();
   textOutput.add(jsclpOutput);
   jTextOutput.add(textOutput);   

   box1 = Box.createVerticalBox();
   box2 = Box.createVerticalBox();

   box1.add(jInput);
   box1.add(jParameter);
   box2.add(jGridOutput);
   box2.add(jTextOutput);
    
   jfrm.getContentPane().add(box1);
   jfrm.getContentPane().add(box2);
   
   jfrm.setVisible(true); 
 }

}
