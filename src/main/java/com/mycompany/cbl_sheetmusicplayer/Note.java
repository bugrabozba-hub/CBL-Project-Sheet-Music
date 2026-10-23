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
    private int volume;       //the loudness of the note
    private int place;
    
    /**
     * Constructor of every note
     * @param duration
     * @param pitch 
     * @param volume
     */
    public Note(int place, int duration, int pitch, int volume) {
        this.duration = duration;
        this.pitch = pitch;
        this.volume = volume;
        this.place = place;
        
        if (this.volume == 0) {
            this.isRest = true;
        }
    }
    
    //public abstract void draw();
    
    public void play(int place) throws InterruptedException {
        MidiChannel channel = synth.getChannels()[place];
        channel.programChange(Sheet.getInstrument()); // 40 Violin
        
        /* This try block is used in case the phonetic thread,
        that is playing this note, is interrupted. The finnally
        makes sure the note gets turned off, otherwise it would
        keep the note playing idefinetly (Kinda annoying).
        */
        try {
            channel.noteOn(this.pitch, this.volume);
            
            /* Calculation of the time beeing played based
            on the duration of this note */
            int time = (15000 / Sheet.getBpm()) * this.duration;

            Thread.sleep(time);
        } finally {
            channel.noteOff(this.pitch);
        }
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
     * Method returns the vertical position of the note for
     * future drawing.
     * @return 
     */
    public int getY() {
        int vertPosition;
        
        // Find in which octave (wither 5, 6 or 7 mainly)
        int octave = this.pitch / 12;
        int pitchInOctave = this.pitch % 12;
        int ovalHeight = 8;
        
        // Hight from the note inside the octave:
        int noteH = 0;
        
        switch (pitchInOctave) {
            case 0 -> noteH = 3; //C
            case 1 -> noteH = 3; //C#
            case 2 -> noteH = 2; //D
            case 3 -> noteH = 2; //D#
            case 4 -> noteH = 1; //E
            case 5 -> noteH = 0; //F
            case 6 -> noteH = 0; //F#
            case 7 -> noteH = -1; //G
            case 8 -> noteH = -1; //G#
            case 9 -> noteH = -2; //A
            case 10 -> noteH = -2; //A#
            case 11 -> noteH = -3; //B
        }
        
        vertPosition = noteH + 7 * (6 - octave);      //each octave has 7 notes
        
        return vertPosition * (ovalHeight);
    }
    
    public int getPlace() {
        return this.place;
    }

    
    
}