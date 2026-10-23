/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit 
this template
 */

package com.mycompany.cbl_sheetmusicplayer;

import javax.sound.midi.*;

/**
 *
 * @author bugra
 */
public class Sheet {
    
    private static int instrument = 40; //40 = violin
    private static int bpm = 40;
    private static int[] TimeSignature = {4, 4};
    private static boolean stop;
    public static Bar[] Bars = new Bar[3];
    private static int lastBar = 0;
    
    protected final static int beatSections = 4;    // For now, any multiple of 4.
    protected final static int defaultVolume = 100;
    protected final static int defaultPitch = 69;   // 69 = A4
    
    protected static Synthesizer synth;
    
    //test 
    
    public static int getInstrument() {
        return instrument;
    }
    
    public static void setInstrument(int NewInstrument) {
        instrument = NewInstrument;
    }
    
    public static int getBpm() {
        return bpm;
    }
    
    public static void setBpm(int NewBPM) {
        bpm = NewBPM;
    }
    
    public static boolean getStop() {
        return stop;
    }
    
    public static void setStop(boolean a) {
        stop = a;
    }
    
    public static void setTimeSignatureN(int numerator) {
        TimeSignature[0] = numerator;
    }
    
    public static void setTimeSignatureD(int denominator) {
        TimeSignature[1] = denominator;
    }
    
    public static void setTimeSignature(int numerator, int denominator) {
        setTimeSignatureN(numerator);
        setTimeSignatureD(denominator);
    }
    
    public static int getTimeSignature(int a) {
        return TimeSignature[a];
    }
    
    public static int getTimeSignature() {
        return getTimeSignature(0);
    }
    
    
    public static void play() throws MidiUnavailableException, InterruptedException {
        synth = MidiSystem.getSynthesizer();
        synth.open();
        
        for (int i = lastBar; i < Bars.length; i++) {
            if (Bars[i] == null) {
                Bars[i] = new Bar();
            }
            
            Bars[i].play();
            
            if (stop) {
                lastBar = i;
                return;
            }
        }
        
        lastBar = 0;
        
    }
    
    public static void ClearAll() {
        for (Bar i: Bars) {
            i.clear();
        }
    }
    
    public static void addNote(int bar, int place, int type, int pitch, int volume) 
            throws NoteTooLongException {
        
        if (Bars[bar] == null) {
            Bars[bar] = new Bar();
        }
        
        Bars[bar].addNote(place, type, pitch, volume);
    }
    
    public static void setLastBar(int value) {
        lastBar = value;
    }
    
    public static void print() {
        for (int i = 0; i < Bars.length; i++) {
            System.out.println("Bar " + i + ":\n" + Bars[i]);
        }
    }
    
    public static void deleteNote() {
        int i = 2;
        while(true) {
            try {
                if (! Bars[i].getPhonetics().isEmpty()){
                    /* Delete last note if there is at least 
                    one phonetic. */
                    Bars[i].deleteNote();
                    return;
                }
                // The bar has no phonetics, try the next one.
                i--;
            } catch (NullPointerException a) {
                /* The current bar does not exist since it's null,
                or the bar's phonetics are empty.
                The method tries to go to the one before. */ 
                i--;
            } catch (IndexOutOfBoundsException e){
                // There are no bars in the Sheet. Move on, do nothing.
                return;
            }
        }
    }
    
    public static void stopAll() {
        for (Bar i: Bars) {
            i.stop();
        }
    }
}
