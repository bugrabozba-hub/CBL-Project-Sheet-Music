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
    
    //drawing the sheetmusic
    private final int topStaffY = 100;
    private final int lineSpacing = 16;
    private final int stepDistance = lineSpacing / 2;
    
    private final int lowestNote = 55; //lowest G (violin)
    private final int highestNote = 92; //highest G

    private final int[] scale = {0, 2, 4, 5, 7, 9, 11};
    
    boolean placed = false;
    GUIJFrame sheet = new GUIJFrame();
    
    private java.awt.Image trebleClefImage;
    private java.awt.Image timeSignatureImage;
    
    public SheetMusicCanvas() {
        this.addMouseMotionListener(this); 
        this.addMouseListener(this);
        
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(680,640));
        
        try {
            trebleClefImage = new javax.swing.ImageIcon(getClass().getResource("/Resources/treble-clefx64.png")).getImage();
            timeSignatureImage = new javax.swing.ImageIcon(getClass().getResource("/Resources/timeSignature-removebgx64.png")).getImage();
        } catch (Exception e) {
            System.out.println("Image not found!");
        }
    } 
    
    public void mouseClicked(MouseEvent m) {
       
    }
    int selectedDuration = 4;
    
    public void mousePressed(MouseEvent e) {
        
        int startX = 150;
        int slotWidth = 30;
        int slot = (e.getX() - 150) / 30;
        int Line = (e.getY() - topStaffY + 40 ) / 144;
        int selectedAccidental = sheet.getSelectedAccidental();
        
        hoverX = e.getX();
        
        if (hoverX >= 150 && hoverX < 630) {
            hoverSlotX = (hoverX - 150) / 30;
        } else {
            hoverSlotX = -1;
        }
        
        if (hoverSlotX < 0) {
            return;
        }
        // steps below the top line of this staff (0 = top line, 1 = first space, ...)
        int step = Math.round((float) (e.getY() - (topStaffY + Line * 144)) / stepDistance);

        // count betweenLine notes: 38 = F5 (the top line), each step down is one note lower
        int betweenLineNote = 38 - step;

        // turn that into a midi number: 12 * octave + position in the scale
        int pitch = 12 * (betweenLineNote / 7 + 1) + scale[betweenLineNote % 7];
        pitch = pitch + selectedAccidental;    

        if (hoverSlotX >= 0) {
           sheet.getNoteType();
           sheet.getPitch();
           placed = true;
           
           Sheet.addNote(Line, slot, sheet.getNoteType() , pitch, sheet.getVolume());
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
        hoverY = -1;
        hoverSlotX = -1;
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
        
        if (hoverX < 150 || hoverX >= 630) {
            hoverSlotX = -1;
        }
    
        repaint();    
    }
    
    //pasted the draw method for now TO DO
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D paint = (Graphics2D) g.create();

        // 1. Draw the 5 main staff lines
        paint.setColor(Color.BLACK);
        paint.setStroke(new BasicStroke(1.5f));

        int startX = 40;                // Left margin
        int endX = getWidth() - 40;     // Right margin
        
        //draws "sheetLineCount" times sheets.
        int sheetLineCount = 3;
        
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
        //(image, x, y, width, height, observer)
        if (placed) {
            if (sheet.getNoteType() == 1) {
            paint.setColor(Color.BLACK); 
            int centerX = 150 + (hoverSlotX * 30) + 15;

            paint.fillOval(centerX - 7, hoverY - 5, 14, 10);
            paint.drawLine(centerX + 7, hoverY - 5, centerX + 7, hoverY + 15);
            }
        }

        paint.dispose();
    }
}
    