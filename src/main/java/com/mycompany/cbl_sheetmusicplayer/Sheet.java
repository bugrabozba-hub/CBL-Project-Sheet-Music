/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cbl_sheetmusicplayer;

/**
 *
 * @author bugra
 */
public class Sheet {
    
    private static int instrument = 40; //40 = violin
    private static int bpm = 100;
    private static int[] TimeSignature = {4, 4};
    private static boolean stop;
    protected final static int beatSections = 4; // For now, any multiple of 4.
    protected final static int defaultVolume = 100;
    protected final static int defaultPitch = 69; // A4
    
    //test 
    
    public static int getInstrument() {
        return instrument;
    }
    
    public static void setInstrument(int NewInstrument) {
        instrument = NewInstrument;
    }
    
    public static int getBpm() {
        return instrument;
    }
    
    public static void setBpm(int NewInstrument) {
        instrument = NewInstrument;
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
}
