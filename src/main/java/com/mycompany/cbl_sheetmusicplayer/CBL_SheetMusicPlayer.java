/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cbl_sheetmusicplayer;
import javax.swing.JFrame;
/**
 *
 * @author bugra
 */
public class CBL_SheetMusicPlayer {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sheet Music Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Add your GUIJFrame panel into the window
        GUIJFrame panel = new GUIJFrame();
        frame.setContentPane(panel);
        
        frame.pack();
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null); // Centers on screen
        frame.setVisible(true);
        new GUIJFrame().setVisible(true);
    
        Bar test = new Bar();
        
        test.play();
    }
}
