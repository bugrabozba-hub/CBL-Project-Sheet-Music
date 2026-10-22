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
    
    public ArrayList<Note> notes = new ArrayList<>();
    private int beats;
    private int place;
    
    public Phonetic(ArrayList<Note> notes) {
        this.notes = (ArrayList<Note>) notes.clone();
    }
    
    public Phonetic(int place, int beats) {
        this.place = place;
        this.beats = beats;
    }
    
    public Phonetic(int place) {
        this(place, Sheet.getTimeSignature());
    }
    
    @Override
    public void run() {
        for (int i = 0; i < notes.size(); i++) {
            try {
                notes.get(i).play(this.place);
            } catch (InterruptedException ex) {
                System.getLogger(Phonetic.class.getName()).log(
                        System.Logger.Level.ERROR, (String) null, ex);
            }
            
            if (Sheet.getStop()) {
                return;
            }
        }
    }
    
    int getLength() {
        if (notes == null) {
            return 0;
        }
        
        int sum = 0;
        
        for (Note i: notes) {
            sum += i.getDuration();
        }
        
        return sum;
    }
    
    
    public int getNumberofnotes() {
        return notes.size();
    }
    
    public ArrayList<Note> getNotes() {
        return notes;
    }
    
    public void fill(int UptoBeat) {
        int diff = UptoBeat - this.getLength();
        
        // Stop if the bar dosen't need filling
        if (diff <= 0) {
            return;
        }
        
        // Fill the necessary SemiQuaver rests
        // Number of semiq. = (portions of beat left) / (length of a semiq.)
        int semidiff = (diff % Sheet.beatSections) / (Sheet.beatSections / 4);
        for (int i = 0; i < semidiff; i++){
            notes.add(new Note(this.getLength() + i, 0, Sheet.defaultPitch, 0));
        }
        
        // Fill necesary Crochet rests
        diff = diff / Sheet.beatSections;
        for (int i = 0; i < diff; i++){
            notes.add(new Note(this.getLength() + i, 2, Sheet.defaultPitch, 0));
        }
    }
    
    public void fill() {
        this.fill(Sheet.getTimeSignature() * Sheet.beatSections);
    }
    
    /**
     * Deletes the last note of the phonetic and all the rests
     * that came before.
     */
    void deleteNote() {
        do {
            notes.removeLast();
        } while (notes.getLast().getisRest());
    }
    
    void deleteNote(int place) {
        notes.remove(place);
    }

    boolean isFilled() {
        if (this.getLength() == this.beats * Sheet.beatSections) {
            return true;
        } else {
            return false;
        }
    }
    
    int getLengthUpto(int index) throws NonexistantNoteException {
        
        if (index == 0) {
            return 0;
        } else if (index < 0) {
            throw new NonexistantNoteException(
                    "No note of this type in this phonetic.");
        } else {
            return this.getLengthUpto(index - 1) 
                    + notes.get(index - 1).getDuration();
        }
        
    } 
    
    void clear() {
        notes.clear();
    }
    
    /**
     * Method ads a note to the phonetic. If it is not possible
     * it throws exceptions to give information to the Bar
     * object.
     * @param place
     * @param type
     * @param pitch
     * @param volume
     * @throws NoteTooLongException
     * @throws InvalidNoteTypeException 
     */
    public void addNote(int place, int type, int pitch, int volume)
            throws NoteTooLongException, InvalidNoteTypeException {
        
        int addLength = 1;
        // New variable called to reduce computations.
        int ThisLength = this.getLength();
        
        // calculates added length through power of two.
        for (int i = 0; i < type; i++) {
            addLength *= 2;
        }
        
        if (place + addLength > this.beats * Sheet.beatSections) {
            /* Note at this place cannot fit in any posssible
            bar of this size.
            */
            throw new InvalidNoteTypeException(
                    "Note makes any bar exceed" + this.beats + " beats.");
        }
        
        if (place < ThisLength) {
            /* notes are already being played where the user
            wants this specific note.
            */
            throw new NoteTooLongException("There already is a note here.");
        }
        
        if (ThisLength + addLength > this.beats * Sheet.beatSections) {
            // Note dosen't fit in this specific bar.
            throw new NoteTooLongException(
                    "Note makes bar exceeds" + this.beats + " beats.");
        }
        
        // Fill the phonetic up to the desired place
        this.fill(place);
        // Finnally, add the note.
        notes.add(new Note(place, addLength, pitch, volume));
    }
    
}
