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
        GUIJFrame mainAppWindow = new GUIJFrame();
        
        mainAppWindow.setTitle("Sheet Music Player");
        mainAppWindow.setLocationRelativeTo(null); 
        mainAppWindow.setVisible(true);
        
        Bar test = new Bar();
        test.play();
    }
}
