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
import interpreter.*;
public class PaintGrid  extends JPanel {
Insets ins;
PaintGrid(){
 setOpaque(true);
 setPreferredSize(new Dimension(450, 250));
 setBorder(BorderFactory.createLineBorder(Color.BLACK));
 setBackground(Color.WHITE);
}
protected void paintComponent(Graphics g){
   super.paintComponent(g);
   int height = getHeight();
   int width  = getWidth();
   ins = getInsets();
   /*for(int i=ins.left+5; i<=width-ins.right-5; i+=4)
    g.drawLine(i,height-ins.bottom,i,5);*/
   for(int i = 0;i<=width;i+=50)
    g.drawLine(i,0,i,height);
   for(int i = 0;i<=height;i+=50)
    g.drawLine(0,i,width,i);
 }
    
}
