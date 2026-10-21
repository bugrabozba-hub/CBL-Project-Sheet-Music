/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cbl_sheetmusicplayer;

import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author bugra
 */
public class CBL_SheetMusicPlayer {

    public static void main(String[] args) throws MidiUnavailableException, InterruptedException, NoteTooLongException {
        Bar test = new Bar();
        
        try {
            test.addNote(0, 1, Sheet.defaultPitch-7, Sheet.defaultVolume);
            test.addNote(15, 0, Sheet.defaultPitch-4, Sheet.defaultVolume);
            test.addNote(0, 3, Sheet.defaultPitch, Sheet.defaultVolume);
            
            /*
            test.addNote(0, 1, Sheet.defaultPitch, Sheet.defaultVolume);
            test.addNote(2, 1, Sheet.defaultPitch - 5, Sheet.defaultVolume);
            test.addNote(4, 1, Sheet.defaultPitch - 7, Sheet.defaultVolume);
            test.addNote(6, 1, Sheet.defaultPitch + 2, Sheet.defaultVolume);
            test.addNote(0, 3, Sheet.defaultPitch - 9, Sheet.defaultVolume);
            */
        } catch (NoteTooLongException | InvalidNoteTypeException a) {
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
        
    }
}
