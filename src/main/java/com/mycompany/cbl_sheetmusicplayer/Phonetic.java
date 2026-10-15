/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cbl_sheetmusicplayer;
import java.util.ArrayList;

/**
 * This class is used when playing a specific bar.
 * @author freijosune
 */
public class Phonetic implements Runnable {
    
    private ArrayList<Note> Notes;
    
    public Phonetic (ArrayList<Note> Notes) {
        this.Notes = (ArrayList<Note>) Notes.clone();
    }
    
    @Override
    public void run() {
        for (int i = 0; i < Notes.size(); i++) {
            try {
                Notes.get(i).play();
            } catch (InterruptedException ex) {
                System.getLogger(Phonetic.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        }
    }
    
    public void getLength() {
        
    }
}
