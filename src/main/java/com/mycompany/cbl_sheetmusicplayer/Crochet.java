/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cbl_sheetmusicplayer;

/**
 *
 * @author freijosune
 */
public class Crochet extends Note {
    
    public Crochet (int pitch, int volume) {
        super(Sheet.beatSections, pitch, volume); // One beat can be divided into maximum of 12 parts
    }
    
    public Crochet (int pitch) {
        this(pitch, Sheet.defaultVolume);
    }
}
