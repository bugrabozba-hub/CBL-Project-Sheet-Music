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
    
    //drawing the sheetmusic
    private final int topStaffY = 160;
    private final int lineSpacing = 16;
    private final int stepDistance = lineSpacing / 2;
    
    private final int lowestNote = 55; //lowest G (violin)
    private final int highestNote = 92; //highest G
    
    public void mouseClicked(MouseEvent m) {
        
    }
    
    public SheetMusicCanvas() {
        this.addMouseMotionListener(this); 
        this.addMouseListener(this);
        
        setBackground(Color.WHITE);
        
        setPreferredSize(new Dimension(570,640));
        
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
    
        repaint();    
    }
    
    //pasted the draw method for now!!
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable smooth lines
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Draw the 5 main staff lines
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1.5f));

        int startX = 40;                // Left margin
        int endX = getWidth() - 40;     // Right margin

        for (int i = 0; i < 5; i++) {
            int currentY = topStaffY + (i * lineSpacing);
            g2.drawLine(startX, currentY, endX, currentY);
        }

        // 2. Draw hover preview note if mouse is inside the panel
        if (hoverX != -1 && hoverY != -1) {
            g2.setColor(new Color(0, 102, 204, 160)); // Translucent blue
            g2.fillOval(hoverX - 7, hoverY - 5, 14, 10);
        }

        g2.dispose();
    }
}
