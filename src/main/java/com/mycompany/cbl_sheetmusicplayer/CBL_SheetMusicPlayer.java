/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-DEFAULT_.txt 
 * to change this license
 */

package com.mycompany.cbl_sheetmusicplayer;

import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author bugra
 */
public class CBL_SheetMusicPlayer {

    public static void main(String[] args) 
            throws MidiUnavailableException, InterruptedException, 
            NoteTooLongException {
        
        Bar test = new Bar();
        Sheet.setBpm(180);
        try {
            test.addNote(1, 0, Sheet.DEFAULT_PITCH + 4, Sheet.DEFAULT_VOLUME);
            test.addNote(2, 0, Sheet.DEFAULT_PITCH + 9, Sheet.DEFAULT_VOLUME);
            test.addNote(3, 0, Sheet.DEFAULT_PITCH + 11, Sheet.DEFAULT_VOLUME);
            test.addNote(4, 0, Sheet.DEFAULT_PITCH + 16, Sheet.DEFAULT_VOLUME);
            test.addNote(5, 1, Sheet.DEFAULT_PITCH + 20, Sheet.DEFAULT_VOLUME);

        } catch(NoteTooLongException a) {
            System.out.println("Bro check yo code");
            System.out.println(a.getClass());
            System.out.println(a.getMessage());
            System.out.println(a.getLocalizedMessage());
        }
        Sheet.play();

        GUIJFrame appWindow = new GUIJFrame();
        
        appWindow.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        
        appWindow.setTitle("Sheet Music Player");
        appWindow.setLocationRelativeTo(null); 
        appWindow.setVisible(true);
        
        test.play();
        
        Sheet.setBpm(40);
    }
}
