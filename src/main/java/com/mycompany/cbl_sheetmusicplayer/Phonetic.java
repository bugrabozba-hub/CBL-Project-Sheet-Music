/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit
 * this template
 */

package com.mycompany.cbl_sheetmusicplayer;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class is used when playing a specific bar.
 * It stores the notes themselves. When playing, since
 * it implements runnable, it allows polyphonic 
 * compositions.
 * @author freijosuneBar
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
    
    /**
     * Actual Play method. To allow multiple phonetics
     * being played at the same time w ehave to overwiite
     * the run method.
     */
    @Override
    public void run() {
        for (int i = 0; i < notes.size(); i++) {
            try {
                notes.get(i).play(this.place);
            } catch (InterruptedException ex) {
                return;
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
    
    public void fill(int uptoBeat) {
        int diff = uptoBeat - this.getLength();
        
        // Stop if the bar dosen't need filling
        if (diff <= 0) {
            return;
        }
        
        // Fill the necessary SemiQuaver rests
        // Number of semiq. = (portions of beat left) / (length of a semiq.)
        int semidiff = (diff % Sheet.BEAT_SECTIONS) / (Sheet.BEAT_SECTIONS / 4);
        for (int i = 0; i < semidiff; i++) {
            notes.add(new Note(this.getLength() + i, 1, Sheet.DEFAULT_PITCH, 0));
        }
        
        // Fill necesary Crochet rests
        diff = diff / Sheet.BEAT_SECTIONS;
        for (int i = 0; i < diff; i++) {
            notes.add(new Note(this.getLength() + i, 4, Sheet.DEFAULT_PITCH, 0));
        }
    }
    
    public void fill() {
        this.fill(Sheet.getTimeSignature() * Sheet.BEAT_SECTIONS);
    }
    
    /**
     * Deletes the last note of the phonetic and all the rests
     * that came before.
     */
    void deleteNote() {
        try {
            /* The first note should always be deleted.
            Then, if the next note is a rest keep deleting
            (for phonetic clean up). */
            do {
                notes.removeLast();
            } while (notes.getLast().getisRest());
            
        } catch (NoSuchElementException a) {
            /* If notes is empty it thows this type of
            exception for both getLast and removeLast.
            Since notes is empty we are done.
            */
        }
    }
    
    void deleteNote(int place) {
        notes.remove(place);
    }
    
    
    boolean isFilled() {
        if (this.getLength() == 16) {
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
        int thisLength = this.getLength();
        
        // calculates added length through power of two.
        for (int i = 0; i < type; i++) {
            addLength *= 2;
        }
        
        System.out.print(addLength);
        
        if (place + addLength > this.beats * Sheet.BEAT_SECTIONS) {
            /* Note at this place cannot fit in any posssible
            bar of this size.
            */
            throw new InvalidNoteTypeException(
                    "Note makes any bar exceed" + this.beats + " beats.");
        }
        
        if (place < thisLength) {
            /* notes are already being played where the user
            wants this specific note.
            */
            throw new NoteTooLongException("There already is a note here.");
        }
        
        if (thisLength + addLength > this.beats * Sheet.BEAT_SECTIONS) {
            // Note dosen't fit in this specific bar.
            throw new NoteTooLongException(
                    "Note makes bar exceeds" + this.beats + " beats.");
        }
        
        // Fill the phonetic up to the desired place
        this.fill(place);
        // Finnally, add the note.
        notes.add(new Note(place, addLength, pitch, volume));
    }
    
    
    public String toString() {
        String result = "";
        
        for (int i = 0; i < notes.size(); i++) {
            Note each = notes.get(i);
            result += "Note " + i + ":" + each.getPlace() + "," 
                        + each.getDuration();
            if (each.getisRest()) {
                result += "Rest";
            }
            result += "\n";
            
        }
        
        return result;
    }
    
}
