# Sheet Music Player

For this challenge-based learning project, we decided to design a sheet music 
editor/ player so that people who want to compose music could have an 
application that not only will make composing easier, but also gives them 
a chance to see how their composition sounds. 

Sheet Music Player is a Java Swing application where users could place notes 
on a sheet music canvas and play them back with the help of MIDI.

## Features

- Choose the character of the note / rest you want to place from the buttons 
panel on top.
- Modify the pitch of the note (flat, natural, sharp).
- Modify the volume of the note (pp, p, mp, mf, f, ff).
- Set the bpm from the box below.
- Click on the staves to place a note, then the pitch is then calculated 
accordingly.
- Hover preview of the note to show its future placement 
- Play the music composed. Polyphonic melodies and chords can also be played 
with the help of multi-threading.
- Play and pause the music with the help of the play button. 
- Delete the last placed note using delete button. 
- Reset the whole composition.


## Requirements to Run The Project

- JDK 17 or newer versions are supported, since the code uses newer syntax
 (Java 25.0.4.1 is used)
- Apache Netbeans is the recommended IDE to run this program
(it was also coded using Netbeans)
- A working audio output is recommended
- No other libraries are required to be installed.

## Building

- Open Netbeans and open the project, by clicking *File -> Open Project
- Select and open the folder "CBL_SheetMusicPlayer"
- Right-click the project and select "Clean and Run" or Shift + F11

## Running

- Run the project either by clicking the run button on top or F6

## How to use the Sheet Music Canvas

- Select the length of the note you want to place down 
(a crochet is selected as default)
- Select the semi-tone modification of the note from the top-right panel.
(a natural is selected as default)
- Select the volume modification of the note from the panel below the 
semi-tone selection panel (mezzo forte is selected as default)
- Move the mouse over the staves, a blue hover preview will show where 
the note will be placed
- Click to place as many notes as desired (watch out for the length of 
the bar, or will get an error message asking for a valid placement).
- Delete the last placed note by pressing the delete button below
- Press the play button to hear the sheet
- Reset the composition by pressing the reset button
- Name your composition from the Title box on top of the staves.

NOTE: The previous and next buttons are for decorative purposes only. 
Since there is no previous or next composition, these buttons are not attached 
to any method. 
