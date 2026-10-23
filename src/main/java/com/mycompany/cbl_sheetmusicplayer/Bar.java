/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.cbl_sheetmusicplayer;

import java.util.ArrayList; 

/**
 *
 * @author bugra
 */

public class Bar {
    
    ArrayList<Phonetic> phonetics = new ArrayList<>();
    ArrayList<Thread> threads;
    
    /**
     * 
     * @return Maximum length of the phonetics inside the Bar
     */
    int getLength() {                   
        int max = 0;
        int size;
        
        for (int i = 0; i < phonetics.size(); i++) {
            size = phonetics.get(i).getLength();
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
        
        for (Phonetic i: phonetics) {
            if (i.isFilled()) {
                return true;
            }
        }
        
        return false;
    }
    
    boolean isFull() {
        
        for (Phonetic i: phonetics) {
            if (! i.isFilled()) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Fills the most full phonetic with rests.
     * Must note that if there are two of the same length
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
        for (int i = 0; i < phonetics.size(); i++) {
            // Finds its length
            tempLength = phonetics.get(i).getLength();
            
            // Makes it the max. if its higher than the one before. 
            if (maxLength < tempLength) {
                maxLength = tempLength;
                MaxPhonetic = i;
            }
        }
        
        // Fills the max. phonetic
        try {
            phonetics.get(MaxPhonetic).fill();
        } catch (IndexOutOfBoundsException a) {
            Phonetic first = new Phonetic(0);
            phonetics.add(first);
            first.fill();
        }
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
        //Remember all of the threads to be created
        threads = new ArrayList<>();
        
        for (Phonetic i: phonetics) {
            // Stop if user wants to.
            if (Thread.currentThread().isInterrupted()
                    || Sheet.getStop()){
                return;
            }
            
            // Create a new thread
            Thread myThread = new Thread(i);
            myThread.start();
            
            // Remember the created thread
            threads.add(myThread);
        }
        
        // Wait for all of the created threads to end.
        for (Thread i: threads) {
            i.join();
        }
    }
    
    /**
     * Deletes all the notes that are closest to the end
     * of the bar and of the smallest size available. 
     */
    void deleteNote() throws NullPointerException {
        // Remembers all phonetics that should change
        ArrayList<Phonetic> change = new ArrayList<>();
        int max = -1;
        int temp;
        int lastNoteDuration;
        int samallestNote = -1;
        
        for (Phonetic i: phonetics) {
            if (i.getNumberofnotes() == 0) {
                continue;
            }
            temp = i.getLength();
            
            // Searches for the phonetics of the biggest size
            if (temp > max) {
                max = temp;
                change.clear();
            }
            if (temp == max) {
                lastNoteDuration = 
                        i.getNotes().getLast().getDuration();
                
                /* Inside the phonetics of the bi1ggest size,
                it selects those with the smallest note at
                the end. 
                */
                if (samallestNote > lastNoteDuration) {
                    samallestNote = lastNoteDuration;
                    change.clear();
                }
                if (samallestNote == lastNoteDuration) {
                    change.add(i);
                }
            }
        }
        
        // If there is nothing to change ergo 
        if (change.isEmpty()) {
            throw new NullPointerException();
        }
        
        // Once they are found, the last note is deleted.
        for (Phonetic i: change) {
            i.deleteNote();
        }
        
    }
    
    /**
     * Clears all notes from the bar
     */
    void clear() {
        phonetics.clear();
    }
    
    public int getNumberofphonetics() {
        return phonetics.size();
    }
    
    public void addNote (int place, int type, int pitch, int volume)
            throws NoteTooLongException {
        
        int i = 0;
        // Keep going until it finds a place for the note.
        while(true) {
            try {
                phonetics.get(i).addNote(place, type, pitch, volume);
                break;
            } catch(NoteTooLongException e) {
                // If the note dosent fit go to the next
                i++;
            } catch(IndexOutOfBoundsException e) {
                /* If there are no more phonetics left then 
                create a new one.
                */
                phonetics.add(new Phonetic(phonetics.size()));
            } catch(InvalidNoteTypeException e) {
                // Note of this type can't fit in this place
                throw new NoteTooLongException(e.getMessage());
            }
        }        
    }
    
    public ArrayList<Note> getDrawNotes() {
        ArrayList<Note> result = new ArrayList<>();
        
        for (Phonetic phonetic: phonetics) {
            for (Note i: phonetic.getNotes()){
                if (! i.getisRest()) {
                    result.add(i);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Testing method for debugging.
     * @return 
     */
    public String toString() {
        String result = "";
        
        for (int i = 0; i < phonetics.size(); i++) {
            result += "Phonetic " + i + ": \n" + phonetics.get(i).toString();
        }
        
        return result;
    }
    
    public ArrayList<Phonetic> getPhonetics() {
        return phonetics;
    }
    
    /**
     * Stops all of the threads created by this Bar.
     */
    public void stop() {
        if (threads != null) {
            for (Thread i : threads) {
                i.interrupt();
            }
        }
    }
}