/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cbl_sheetmusicplayer;

/**
 *
 * @author bugra
 */

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class SheetMusicCanvas extends javax.swing.JPanel implements MouseListener, MouseMotionListener {
    
    //for mouse tracking
    private int hoverX = -1;
    private int hoverY = -1;
    private int hoverSlotX = -1;
    private int hoverLine = 0;
    int slotWidth = 30;
    
    public final int NoteDurationSemibreve = 16;
    public final int NoteDurationMinim = 8;
    public final int NoteDurationCrochet = 4;
    public final int NoteDurationQuaver = 2;
    public final int NoteDurationSemiQuaver = 1;
    
            
    //draws "sheetLineCount" times sheets.
    private final int sheetLineCount = 3;

    //drawing the sheetmusic
    private final int topStaffY = 100;
    private final int lineSpacing = 16;
    private final int stepDistance = lineSpacing / 2;
    
    private final GUIJFrame sheet;

    private final int[] scale = {0, 2, 4, 5, 7, 9, 11};
    
    int step;
    
    boolean placed = false;    
    private java.awt.Image trebleClefImage;
    private java.awt.Image timeSignatureImage;
    private java.awt.Image flatImage;
    private java.awt.Image sharpImage;
    
    public SheetMusicCanvas(GUIJFrame sheet) {
        this.addMouseMotionListener(this); 
        this.addMouseListener(this);
        this.sheet = sheet;
        
        setBackground(Color.WHITE);
        setSize(new Dimension(680,640));
        
        try {
            flatImage = new javax.swing.ImageIcon(getClass().getResource("/Resources/flatx16.png")).getImage();
            sharpImage = new javax.swing.ImageIcon(getClass().getResource("/Resources/sharp2x16.png")).getImage();
            trebleClefImage = new javax.swing.ImageIcon(getClass().getResource("/Resources/treble-clefx64.png")).getImage();
            timeSignatureImage = new javax.swing.ImageIcon(getClass().getResource("/Resources/timeSignature-removebgx64.png")).getImage();
        } catch (Exception e) {
            System.out.println("Image not found!");
        }
    } 

    public void mouseClicked(MouseEvent m) {
       
    }
    
    public void mousePressed(MouseEvent e) {
        
        int startX = 150;
        int slot = (e.getX() - 150) / 30;
        int Line = (e.getY() - topStaffY + 40 ) / 144;
        int selectedAccidental = sheet.getSelectedAccidental();
        int type = sheet.getNoteType();
        int volume = sheet.getVolume();
        
        hoverX = e.getX();
        
        
        
        if (hoverSlotX < 0 || Line >= sheetLineCount) {
            return;
        }
        // steps below the top line of this staff (0 = top line, 1 = first space, ...)
        step = Math.round((float) (e.getY() - (topStaffY + Line * 144)) / stepDistance);

        // count betweenLine notes: 38 = F5 (the top line), each step down is one note lower
        int betweenLineNote = 38 - step;

        // turn that into a midi number: 12 * octave + position in the scale
        int pitch = 12 * (betweenLineNote / 7 + 1) + scale[betweenLineNote % 7];
        pitch = pitch + selectedAccidental;    

        if (hoverSlotX >= 0) {
           sheet.getNoteType();
           sheet.getPitch();
           placed = true;
           
           try {
               Sheet.addNote(Line, slot, type, pitch, volume);
            } catch (NoteTooLongException ex) {
                System.getLogger(SheetMusicCanvas.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            } 
            
           System.out.println("line " + Line + " slot " + slot + " pitch " + pitch);   // test line
           

           repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
        placed = false;
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        hoverX = -1;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hoverX = e.getX();
        hoverSlotX = (e.getX() - 150) / 30;
        hoverLine = (e.getY() - topStaffY + 40) / 144;
    
        //to the nearest space
        int offsetFromTop = e.getY() - topStaffY;
        int stepIndex = Math.round((float) offsetFromTop / stepDistance);
        hoverY = topStaffY + (stepIndex * stepDistance);
        
        if (hoverX >= 150 && hoverX < 630) {
            hoverSlotX = (hoverX - 150) / 30;
        } else {
            hoverSlotX = -1;
        }
        
        repaint();   
    }
    
    void drawNote(Graphics2D paint, int slot, int y, int duration, int pitch) {
        paint.setColor(Color.BLACK);        
        paint.setStroke(new BasicStroke(3f));          
        //int selectedAccidental = sheet.getSelectedAccidental();
        int centerX = 150 + (slot * 30) + 15;
        int sign = (pitch < 71) ? 1 : -1;
        paint.fillOval(centerX - 8, y - 5, 16, 10);
        if (duration != NoteDurationSemibreve) {
            paint.drawLine(centerX + (sign * 7), y, centerX + (sign * 7), y - (sign * 48));
        }
        switch (duration) {
            case NoteDurationCrochet:
                break;
            case NoteDurationSemibreve: 
                paint.setColor(Color.WHITE);
                paint.fillOval(centerX - 4, y - 3, 8, 6);    //check the visuals
                paint.setColor(Color.BLACK);
                break;
            case NoteDurationMinim:                          
                paint.setColor(Color.WHITE);
                paint.fillOval(centerX - 4, y - 3, 8, 6);
                paint.setColor(Color.BLACK);
                break;
            case NoteDurationQuaver: 
                paint.drawLine(centerX + (sign * 8), y - (sign * 48), centerX + (sign * 20), y - (sign * 33));
                break;
            case NoteDurationSemiQuaver:
                paint.drawLine(centerX + (sign * 8), y - (sign * 48), centerX + (sign * 20), y - (sign * 35));
                paint.drawLine(centerX + (sign * 8), y - (sign * 40), centerX + (sign * 20), y - (sign * 27));
                break;
            default:       
        }
        /*
        if (selectedAccidental == 1) {
            paint.drawImage(sharpImage, (slot * 30) + 150 -15, y + 2, this);
        }
        if (selectedAccidental == -1) {
            paint.drawImage(flatImage, slot * 30 + 150 -15, y + 2, this);
        }
        */
    }
    
    void drawExtraLines(Graphics2D paint, int centerX, int staffTop, int y) {
            
        //already declared sth similar in mouseMoved
        
        //for the ones above: -2, -4, -6
        if (-7 < step && step < -1) {
            for (int i = 0; i < 3; i ++) {
                int lineY = i * stepDistance + staffTop;
                paint.drawLine(centerX - 10, lineY, centerX + 10, lineY);
            }
        }
        //for the ones below: 10, 12
        if (9 < step && step < 13) {
            for (int i = 0; i < 2; i++) {
                int lineY = i * stepDistance + staffTop;
                paint.drawLine(centerX - 10, lineY, centerX + 10, lineY);
            }   
        }
        
        //fix this
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D paint = (Graphics2D) g.create();

        // 1. Draw the 5 main staff lines
        paint.setColor(Color.BLACK);
        paint.setStroke(new BasicStroke(1.5f));

        int startX = 40;                // Left margin
        int endX = getWidth() - 40;     // Right margin
        
        for (int j = 0; j < sheetLineCount; j++) {
            for (int i = 0; i < 5; i++) {
                int currentY = topStaffY + (i * lineSpacing) + (j * 144);
                paint.drawLine(startX, currentY, endX, currentY);
            }
            paint.drawLine(startX, 100 + (j * 144), startX, 164 + (j * 144));   //draw the first bar line
            //lineDrawer.drawLine((startX + endX) / 2, 100 + (j * 144), (startX + endX) / 2, 164 + (j * 144));  //maybe divide in half bar?
            paint.drawLine(endX, 100 + (j * 144), endX, 164 + (j * 144));   //draw the last bar line
        }
        
        
        // 2. Draw hover preview note if mouse is inside the panel
        if (hoverX != -1 && hoverY != -1 && hoverSlotX >= 0 && hoverLine < sheetLineCount) {
            paint.setColor(new Color(0, 102, 204, 160));    //blue

            int centerX = 150 + (hoverSlotX * 30) + 15;
            int topLine = 144 * hoverLine + topStaffY;
            
            drawExtraLines(paint, centerX, topLine , hoverY);
                
            paint.fillOval(centerX - 7, hoverY - 5, 14, 10);
        }
        
        //draw treble clefs
        if (trebleClefImage != null) {
            for (int i = 0; i < sheetLineCount; i++) {
                paint.drawImage(trebleClefImage, 40, (topStaffY - 20) + (144 * i), 50, 100, this);
            }
        }
        
        //draw time signatures
        if (timeSignatureImage != null) {
            for (int i = 0; i < sheetLineCount; i++) {
                paint.drawImage(timeSignatureImage, 80, (topStaffY - 12) + (144 * i), 45, 90, this);
            }
        }
        
        for (int i = 0; i < Sheet.Bars.length; i++) {
            for (Note note : Sheet.Bars[i].getDrawNotes()) {
                drawNote(paint, note.getPlace(), topStaffY + 144 * i + note.getY(), note.getDuration(), note.getPitch());
            }
        }

        paint.dispose();
    }
}
    