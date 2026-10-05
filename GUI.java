/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cbl_sheetmusicplayer;

import javax.swing.*;
import java.awt.FlowLayout;

/**
 *
 * @author Ferran
 * 
 * @author bugra
 */
public class GUI {
        public static void main(String[] args) {
            JFrame frame = new JFrame("Music Composing System");         //create JFrame with the title
        
            frame.setSize(1000,750);                                    //Set window size (sidth, height)

            frame.setLocationRelativeTo(null);                          //Center the window on the screen

            frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);   
            
            frame.setLayout(new FlowLayout());//close the application when window is closed

            JLabel label1 = new JLabel("Enter a name");          //text only
            JLabel label2 = new JLabel(new ImageIcon("bulb.png")); 
            
            frame.setVisible(true);//icon only

            frame.add(label1);
            frame.add(label2);
            
            JLabel label3 = new JLabel("Enter a name");
            JTextField txtname = new JTextField("Enter your name here");
            JPasswordField txtPassword = new JPasswordField(" ");

            frame.add(txtname);
            frame.add(txtPassword);

            frame.setVisible(true);

    }
}
    
