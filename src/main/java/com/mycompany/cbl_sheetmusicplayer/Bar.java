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

public class Bar {
    
    private int beats;
    private int lastNote = 0;
    
    public Bar(int beats) {
        this.beats = beats;
    }
    
    public Bar() {
        this(Sheet.getTimeSignature());
    }
    
    ArrayList<Note> Notes = new ArrayList<>();
    
    int getLength() {                   
        int sum = 0;
        
        for (Note i: Notes) {
            sum += i.getDuration();
        }
        
        return sum;
    }
    
    /**
     * Checks if the bar id filled or not
     * @return 
     */
    boolean isFilled() {
        int length = this.getLength();
        
        if (length == (this.beats * Sheet.beatSections)){
            return true;
        }
        
        return false;
    }
    
    boolean isFull() {
        int length = this.getLength();
        
        if (length >= (this.beats * Sheet.beatSections)){
            return true;
        }
        
        return false;
    }
    
    /**
     * Fills the rest of the bar with rests.
     */
    private void fill() {
        int diff = (this.beats * Sheet.beatSections) - this.getLength();
        
        // Stop if the bar dosen't need filling
        if (diff <= 0) return;
        
        // Fill the necessary SemiQuaver rests
        // Number of semiq. = (portions of beat left) / (length of a semiq.)
        int semidiff = (diff % Sheet.beatSections) / (Sheet.beatSections / 4);
        for (int i = 0; i < semidiff; i++){
            Notes.add(new Note(0, Sheet.defaultPitch, 0));
        }
        
        // Fill necesary Crochet rests
        diff = diff / Sheet.beatSections;
        for (int i = 0; i < diff; i++){
            Notes.add(new Note(2, Sheet.defaultPitch, 0));
        }
    }
    
    /**
     * 
     * @param LastNote Note from which the playing should start
     * @return Next note to be played when playing again.
     */
    int play(int LastNote) throws InterruptedException {
        
        this.fill();
        
        for (int i = LastNote; i < this.Notes.size(); i++) {
            this.Notes.get(i).play();
            
            if (Sheet.getStop()) {
                return i;
            }
        }
        return 0;   
    }
    
    void play() throws InterruptedException {
        
        for (int i = lastNote; i < Notes.size(); i++) {
            Notes.get(i).play();
            
            if (Sheet.getStop()) {
                lastNote = i;
            }
        }
        
        lastNote = 0;        
    }
    
    void deleteNote(int place) {
        Notes.remove(place);
    }
    
    void deleteNote() {
        deleteNote(Notes.size() - 1); // Last note
    }
    
    /**
     * Clears all notes from the bar
     */
    void clear() {
        int repetitions = Notes.size();
        for (int i = 0; i < repetitions; i++) {
            this.deleteNote();
        }
    }
    
    public int getNotes() {
        return Notes.size();
    }
    
    public void addNote (int type, int pitch, int volume)
            throws NoteTooLongException, InvalidNoteTypeException {
        
        if (type < 0 | type > 4) {
            throw new InvalidNoteTypeException("Note type should be between 0 and 4");
        }
        
        int addLength = 1;
        
        // calculates added lenght through power of two.
        for (int i = 0; i < type; i++) {
            addLength *= 2;
        }
        
        if (this.getLength() + addLength > this.beats * Sheet.beatSections) {
            throw new NoteTooLongException("Note makes bar exceeds" + this.beats + " beats.");
        }
        
        Note AddedNote = new Note(addLength, pitch, volume);
        
        Notes.add(AddedNote);
    }
    
    
}