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
            test.addNote(1, Sheet.defaultPitch, Sheet.defaultVolume);
            test.addNote(1, Sheet.defaultPitch - 5, Sheet.defaultVolume);
            test.addNote(1, Sheet.defaultPitch - 7, Sheet.defaultVolume);
            test.addNote(1, Sheet.defaultPitch + 2, Sheet.defaultVolume);
            test.addNote(3, Sheet.defaultPitch - 9, Sheet.defaultVolume);
        } catch (Exception a) {
            System.out.println("Bro check yo code");
        }
        Sheet.play();
        test.play();
        test.play();
        
        
        
    }
}
