/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cbl_sheetmusicplayer;

import static com.mycompany.cbl_sheetmusicplayer.Sheet.synth;
import javax.sound.midi.*;

/**
 *
 * @author bugra
 */
public class Note {
    
    // 55 - 92 (both including)

    private int duration;        //
    public int pitch;          //the note character basically 
    private boolean isRest;     
    private boolean isUpper;  //going to be used to define the line extending the note
    private int volume;       //the loudness of the note
    
    /**
     * Constructor of every note
     * @param duration
     * @param pitch 
     * @param volume
     */
    public Note(int duration, int pitch, int volume) {
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
    
    public void play(int place) throws InterruptedException {
        MidiChannel channel = synth.getChannels()[place];
        channel.programChange(Sheet.getInstrument()); // 40 Violin
        channel.noteOn(this.pitch, this.volume);

        int time = (15000 / Sheet.getBpm()) * this.duration;

        Thread.sleep(time);

        channel.noteOff(this.pitch);
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
    
    public int getVolume() {
        return this.volume;
    }
    
    /**
     * Method returns the vertical positon of the note for
     * future drawing.
     * @return 
     */
    public int getY() {
        int vertPosition;
        
        // Find in which octave (wither 5, 6 or 7 mainly)
        int octave = this.pitch / 12;
        
        // Hight from the note inside the octave:
        int noteH = 0;
        int octavePitch = this.pitch - octave;
        
        switch (octavePitch) {
            case 0 -> noteH = 3; //C
            // case 1
            case 2 -> noteH = 2; //D
            // case 3
            case 4 -> noteH = 1; //E
            case 5 -> noteH = 0; //F
            // case 6
            case 7 -> noteH = -1; //G
            // case 8
            case 9 -> noteH = -2; //A
            //case 10
            case 11 -> noteH = -3; //B
        }
        
        vertPosition = noteH + (6 - octave);
        
        // return vertPosition * (OVAL HIGHT);
    }
    
    
    
}