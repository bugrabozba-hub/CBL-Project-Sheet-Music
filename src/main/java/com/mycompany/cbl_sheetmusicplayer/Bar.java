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
    
    ArrayList<Phonetic> Phonetics = new ArrayList<>();
    
    /**
     * 
     * @return Maximum length of the phonetics inside the Bar
     */
    int getLength() {                   
        int max = 0;
        int size;
        
        for (int i = 0; i < Phonetics.size(); i++) {
            size = Phonetics.get(i).getLength();
            if (size > max) {
                max = size;
            }
        }
        
        return max;
    }
    
    /**
     * Checks if the bar id filled or not
     * @return 
     */
    boolean isFilled() {
        
        for (Phonetic i: Phonetics) {
            if (i.isFilled()) {
                return true;
            }
        }
        
        return false;
    }
    
    boolean isFull() {
        
        for (Phonetic i: Phonetics) {
            if (! i.isFilled()) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Fills the most full phonetic with rests.
     * Must note that if there are two of the same lenght
     * then the method will chose the phonetic that goes
     * first, so the oldest one so to say.
     */
    private void fill() {
        //Checks if already is filled
        if (this.isFilled()){
            return;
        }
        
        int MaxPhonetic = 0;
        int maxLength = -1;
        int tempLength;
        
        // For all of the phonetics
        for (int i = 0; i < Phonetics.size(); i++) {
            // Finds its length
            tempLength = Phonetics.get(i).getLength();
            
            // Makes it the max. if its higher than the one before. 
            if (maxLength < tempLength) {
                maxLength = tempLength;
                MaxPhonetic = i;
            }
        }
        
        // Fills the max. phonetic
        Phonetics.get(MaxPhonetic).fill();
    }
    
    /**
     * Plays the bar.
     * The method activates all of the Phonetic threads one
     * at a time, stops if the user decides to stop playing
     * the whole sheet.
     * @throws InterruptedException
     */
    void play() throws InterruptedException {
        this.fill();
        //Remember all of the Threads to be created
        ArrayList<Thread> Threads = new ArrayList<>();
        
        for (Phonetic i: Phonetics) {
            // Stop if user wants to.
            if (Sheet.getStop()){
                return;
            }
            
            // Create a new thread
            Thread myThread = new Thread(i);
            myThread.start();
            
            // Remember the created thread
            Threads.add(myThread);
        }
        
        // Wait for all of the created threads to end.
        for (Thread i: Threads) {
            i.join();
        }
    }
    
    /**
     * Deletes all the notes that are closest to the end
     * of the bar and of the smallest size available. 
     */
    void deleteNote() {
        // Remembers all phonetics that should change
        ArrayList<Phonetic> Change = new ArrayList<>();
        int max = -1;
        int temp;
        int lastNoteDuration;
        int samallestNote = -1;
        
        for (Phonetic i: Phonetics) {
            temp = i.getLength();
            
            // Searches for the phonetics of the biggest size
            if (temp > max) {
                max = temp;
                Change.clear();
            }
            if (temp == max) {
                lastNoteDuration = 
                        i.getNotes().getLast().getDuration();
                
                /* Inside the phonetics of the biggest size,
                it selects those with the smallest note at
                the end. 
                */
                if (samallestNote > lastNoteDuration) {
                    samallestNote = lastNoteDuration;
                    Change.clear();
                }
                if (samallestNote == lastNoteDuration) {
                    Change.add(i);
                }
            }
        }
        
        // Once they are found, the last note is deleted.
        for (Phonetic i: Change) {
            i.deleteNote();
        }
        
    }
    
    /**
     * Clears all notes from the bar
     */
    void clear() {
        Phonetics.clear();
    }
    
    public int getNumberofPhonetics() {
        return Phonetics.size();
    }
    
    public void addNote (int place, int type, int pitch, int volume)
            throws NoteTooLongException, InvalidNoteTypeException {
        
        // For internal problems, We deal with notes 0-4.
        if (type < 0 | type > 4) {
            throw new InvalidNoteTypeException("Note type should be between 0 and 4");
        }
        
        int i = 0;
        // Keep going until it finds a place for the note.
        while(true) {
            try {
                Phonetics.get(i).addNote(place, type, pitch, volume);
                break;
            } catch(NoteTooLongException e) {
                // If the note dosent fit go to the next
                i++;
            } catch(IndexOutOfBoundsException e) {
                /* If there are no more phonetics left then 
                create a new one.
                */
                Phonetics.add(new Phonetic(Phonetics.size()));
            } catch(InvalidNoteTypeException e) {
                // Note of this type can't fit in this place
                throw new NoteTooLongException(e.getMessage());
            }
        }        
    }
    
    
}