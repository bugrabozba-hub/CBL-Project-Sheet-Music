/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cbl_sheetmusicplayer;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

/**
 *
 * @author bugra
 */
public class Audio {
    public static void playViolinA442(int durationMs) {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();

            MidiChannel channel = synth.getChannels()[0];
            channel.programChange(40); // 40 = Violin
            
            channel.noteOn(80, 100); // 69 = A4, velocity 100

            Thread.sleep(durationMs);

            channel.noteOff(69);
            synth.close();
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) {
        playViolinA442(2000);
    }
}
