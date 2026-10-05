/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cbl_sheetmusicplayer;

import javax.sound.midi.*;

/**
 *
 * @author bugra
 */
public class Note {

    private double duration;        //
    private int pitch;          //the note character basically 
    private boolean isRest;     
    private boolean isUpper;  //going to be used to define the line extending the note
    private int volume;       //the loudness of the note
    
    public Note (double duration, int pitch) {
        this.duration = duration;
        this.pitch = pitch;
        
    }
}