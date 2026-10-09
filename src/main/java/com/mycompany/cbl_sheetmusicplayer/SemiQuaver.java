/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cbl_sheetmusicplayer;

/**
 *
 * @author freijosune
 */
public class SemiQuaver extends Note {
    
    public SemiQuaver (int pitch, int volume) {
        super(Sheet.beatSections / 4, pitch, volume); // One beat can be divided into maximum of 12 parts
    }
    
    public SemiQuaver (int pitch) {
        this(pitch, Sheet.defaultVolume);
    }
    
}
