/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cbl_sheetmusicplayer;

/**
 *
 * @author freijosune
 */
public class Minim extends Note {
    
    public Minim (int pitch, int volume) {
        super(Sheet.beatSections * 2, pitch, volume); // One beat can be divided into maximum of 12 parts
    }
    
    public Minim (int pitch) {
        this(pitch, Sheet.defaultVolume);
    }
}
