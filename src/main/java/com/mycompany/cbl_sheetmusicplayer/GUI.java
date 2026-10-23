/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java
 * to edit this template
 */

package com.mycompany.cbl_sheetmusicplayer;

import java.awt.FlowLayout;
import javax.swing.JFrame;

/**
 *
 * @author Ferran
 * 
 * @author bugra
 */
public class GUI {
    public static void main(String[] args) {
        //create JFrame with the title
        JFrame frame = new JFrame("Sheet Music Player");
        
        //Set window size (sidth, height)
        frame.setSize(730, 750);

        //Center the window on the screen
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
        //close the application when window is closed
        frame.setLayout(new FlowLayout());
            
        frame.setVisible(true);
    }
}
    
