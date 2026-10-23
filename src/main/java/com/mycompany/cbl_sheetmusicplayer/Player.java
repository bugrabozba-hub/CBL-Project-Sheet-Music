/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java 
 * to edit this template
 */

package com.mycompany.cbl_sheetmusicplayer;

import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author bugra
 */

public class Player extends Thread {
    
    public void run() {
        try {
            Sheet.play();
        } catch (MidiUnavailableException ex) {
            System.getLogger(Player.class.getName()).log(
                    System.Logger.Level.ERROR, (String) null, ex);
        } catch (InterruptedException ex) {
            Sheet.stopAll();
        }
    }
}
