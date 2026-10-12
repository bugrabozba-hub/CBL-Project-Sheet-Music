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
            JFrame frame = new JFrame("Sheet Music Player");         //create JFrame with the title
        
            frame.setSize(730, 750);                                    //Set window size (sidth, height)

            frame.setLocationRelativeTo(null);                          //Center the window on the screen

            frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);   
            
            frame.setLayout(new FlowLayout());//close the application when window is closed
            
            frame.setVisible(true);

    }
}
    
