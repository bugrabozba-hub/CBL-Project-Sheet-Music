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
public abstract class Note {
    
    // 55 - 92 (both including)

    private int duration;        //
    private int pitch;          //the note character basically 
    private boolean isRest;     
    private boolean isUpper;  //going to be used to define the line extending the note
    private int volume;       //the loudness of the note
    
    /**
     * Constructor of every note
     * @param duration
     * @param pitch 
     * @param volume
     */
    public Note (int duration, int pitch, int volume) {
        this.duration = duration;
        this.pitch = pitch;
        this.volume = volume;
        
        if (this.volume == 0) {
            this.isRest = true;
        }
    }
    
    
    public Note (int duration, int pitch) {
        this(duration, pitch, 100); // Default volume is 100
    }
    
    
    /**
     * Co
     * @param duration
     * @param rest 
     */
    public Note (int duration, boolean rest) {
        this(duration, 69, 0); // A rest is by default A4
    }
    
    //public abstract void draw();
    
    public void play() {
        try { //Added this because Netbeans couldn't stop crying
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();

            MidiChannel channel = synth.getChannels()[0];
            channel.programChange( Sheet.getInstrument() ); // 40 = Violin
            
            channel.noteOn(this.pitch, this.volume); // 69 = A4, velocity 100
        
            int time = (60000/Sheet.getBpm()) * this.duration;
            
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            System.getLogger(Note.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        channel.noteOff(69);
        synth.close();
    }
    
    
    public int getDuration() {
        return this.duration;
    }
    
    public void setDuration (int duration) {
        this.duration = duration;
    }
    
    public boolean getisRest() {
        return this.isRest;
    }
    
    public int getPitch() {
        return this.pitch;
    }
}