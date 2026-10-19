/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cbl_sheetmusicplayer;
import java.util.ArrayList; 
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
    private static ArrayList<Bar> Bars = new ArrayList<>();
    private static int lastBar;
    
    protected final static int beatSections = 4; // For now, any multiple of 4.
    protected final static int defaultVolume = 100;
    protected final static int defaultPitch = 69; // 69 = A4
    
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
    
    public static void setTimeSignatureN (int numerator) {
        TimeSignature[0] = numerator;
    }
    
    public static void setTimeSignatureD (int denominator) {
        TimeSignature[1] = denominator;
    }
    
    public static void setTimeSignature (int numerator, int denominator) {
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
        
        for (int i = lastBar; i < Bars.size(); i++) {
            Bars.get(i).play();
            
            if (stop) {
                lastBar = i;
            }
        }
        
        lastBar = 0;
        
    }
    
    public static void ClearAll() {
        for (Bar i: Bars) {
            i.clear();
        }
    }
}
