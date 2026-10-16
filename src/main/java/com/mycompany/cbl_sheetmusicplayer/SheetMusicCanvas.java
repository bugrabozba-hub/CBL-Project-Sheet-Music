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

    //drawing the sheetmusic
    private final int topStaffY = 100;
    private final int lineSpacing = 16;
    private final int stepDistance = lineSpacing / 2;
    
    private final int lowestNote = 55; //lowest G (violin)
    private final int highestNote = 92; //highest G
    
    private java.awt.Image trebleClefImage;
    private java.awt.Image timeSignatureImage;
    
    public void mouseClicked(MouseEvent m) {
        int startX = 150;
        int slotWidth = 40;
        
        //horizontal slotting
    }
    
    public SheetMusicCanvas() {
        this.addMouseMotionListener(this); 
        this.addMouseListener(this);
        
        setBackground(Color.WHITE);
        
        setPreferredSize(new Dimension(570,640));
        
        try {
            trebleClefImage = new javax.swing.ImageIcon(getClass().getResource("/Resources/treble-clefx64.png")).getImage();
            timeSignatureImage = new javax.swing.ImageIcon(getClass().getResource("/Resources/timeSignature-removebgx64.png")).getImage();
        } catch (Exception e) {
            System.out.println("Image not found!");
        }
    } 
    
    
    public void mousePressed(MouseEvent e) {
        
    }

    public void mouseReleased(MouseEvent e) {
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
    
        // Snap to nearest staff line or space
        int offsetFromTop = e.getY() - topStaffY;
        int stepIndex = Math.round((float) offsetFromTop / stepDistance);
        hoverY = topStaffY + (stepIndex * stepDistance);
        
        if (hoverX >= 150 && hoverX < 640) {
            hoverSlotX = (hoverX - 160) / 30;
        } else {
            hoverSlotX = -1;
        }
    
        repaint();    
    }
    
    //pasted the draw method for now TO DO
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D lineDrawer = (Graphics2D) g.create();

        // Enable smooth lines / just in case :)
        //lineDrawer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Draw the 5 main staff lines
        lineDrawer.setColor(Color.BLACK);
        lineDrawer.setStroke(new BasicStroke(1.5f));

        int startX = 40;                // Left margin
        int endX = getWidth() - 40;     // Right margin
        
        //draws "sheetLineCount" times sheets.
        int sheetLineCount = 3;
        
        for (int j = 0; j < sheetLineCount; j++) {
            for (int i = 0; i < 5; i++) {
                int currentY = topStaffY + (i * lineSpacing) + (j * 144);
                lineDrawer.drawLine(startX, currentY, endX, currentY);
            }
            lineDrawer.drawLine(startX, 100 + (j * 144), startX, 164 + (j * 144));   //draw the first bar line
            lineDrawer.drawLine(endX, 100 + (j * 144), endX, 164 + (j * 144));   //draw the last bar line
        }
        
        
        // 2. Draw hover preview note if mouse is inside the panel
        if (hoverX != -1 && hoverY != -1) {
            lineDrawer.setColor(new Color(0, 102, 204, 160)); //blue
            int centerX = 160 + (hoverSlotX * 30) + 20;
            lineDrawer.fillOval(centerX - 7, hoverY - 5, 14, 10);
        }
        
        //draw treble clefs
        if (trebleClefImage != null) {
            for (int i = 0; i < sheetLineCount; i++) {
                lineDrawer.drawImage(trebleClefImage, 40, (topStaffY - 20) + (144 * i), 50, 100, this);
            }
        }
        
        //draw time signatures
        if (timeSignatureImage != null) {
            for (int i = 0; i < sheetLineCount; i++) {
                lineDrawer.drawImage(timeSignatureImage, 80, (topStaffY - 12) + (144 * i), 45, 90, this);
            }
        }
        //(image, x, y, width, height, observer)

        lineDrawer.dispose();
    }
}
