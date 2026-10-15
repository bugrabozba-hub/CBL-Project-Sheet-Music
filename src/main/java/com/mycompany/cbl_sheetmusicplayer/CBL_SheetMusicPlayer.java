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
        GUIJFrame appWindow = new GUIJFrame();
        
        appWindow.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        
        appWindow.setTitle("Sheet Music Player");
        appWindow.setLocationRelativeTo(null); 
        appWindow.setVisible(true);
        
        Bar test = new Bar();
        test.play();
    }
}
