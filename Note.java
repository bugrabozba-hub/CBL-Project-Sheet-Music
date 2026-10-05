/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cbl_sheetmusicplayer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.FlowLayout;
import javax.sound.midi.*;
import javax.sound.sampled.*;
import java.awt.Toolkit;

/**
 *
 * @author bugra
 */
public class Note {

    public static void playViolinA442(int durationMs) {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();

            MidiChannel channel = synth.getChannels()[0];
            channel.programChange(40); // 40 = Violin
            channel.setPitchBend(8514); // Shift from 440 Hz to 442 Hz

            channel.noteOn(69, 100); // 69 = A4, velocity 100

            Thread.sleep(durationMs);

            channel.noteOff(69);
            synth.close();
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) {
        playViolinA442(2000);
    }
}