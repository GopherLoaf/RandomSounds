import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.KeyEvent;

public class Main extends Frame implements KeyListener {
    // Constructor
    public Main() {
        // Set frame properties
        setTitle("Random Sounds");
        setSize(400, 200);
        setLayout(new FlowLayout());

        Label label1 = new Label("Press G to manually play a random sound.");
        Label label2 = new Label("Press esc to end the program.");

        add(label1);
        add(label2);

        // Ensure the frame can receive key events
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // Make the frame visible
        setVisible(true);

        addKeyListener(this);
    }

    public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        String input;
        System.out.println("Hello world!");
        System.out.println("This program creates a folder in LocalLow\\George Wei named RandomSounds. Audio files in this folder will be played randomly.");
        //Create a folder path to store for the program
        File studioFolder = new File(System.getProperty("user.home") + "\\AppData\\LocalLow\\George Wei");
        if (!studioFolder.exists()){
            studioFolder.mkdir();
        }
        File programFolder = new File(System.getProperty("user.home") + "\\AppData\\LocalLow\\George Wei\\RandomSounds");
        if (!programFolder.exists()){
            programFolder.mkdir();
        }
        BufferedReader readguy = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the amount of time in milliseconds between attempts to play audio.");
        input = readguy.readLine();
        try {
            Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("That was not a number.");
            System.exit(0);
        }
        Long playAttemptTime = Long.parseLong(input);
        if (playAttemptTime <= 0){
            System.out.println("No");
            System.exit(0);
        }
        System.out.println("Please enter the chance for the audio to play every attempt as a percentage (0-10000, 100 = 1%).");
        input = readguy.readLine();
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("That was not a number.");
            System.exit(0);
        }
        int chanceToPlay = Integer.parseInt(input);
        if (chanceToPlay < 0 || chanceToPlay > 10000){
            System.out.println("That was not 0-10000");
            System.exit(0);
        }
        AudioInputStream audioInputStream;
        File[] files = programFolder.listFiles();
        if (files.length == 0){
            System.out.println("No files in the folder.");
            System.exit(0);
        }
        new Main();
        while (true){
            if ((int) (Math.random() * 10000) + 1 <= chanceToPlay) {
                Clip clip = AudioSystem.getClip();
                File audio = files[(int) (Math.random() * files.length)];
                audioInputStream = AudioSystem.getAudioInputStream(audio.getAbsoluteFile());
                clip.open(audioInputStream);
                clip.start();
                System.out.println("Automatically Playing " + audio.getName());
            }
            Thread.sleep(playAttemptTime);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_G) {
            File programFolder = new File(System.getProperty("user.home") + "\\AppData\\LocalLow\\George Wei\\RandomSounds");
            File[] files = programFolder.listFiles();
            AudioInputStream audioInputStream;
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            int randomNumber = (int) (Math.random() * files.length);
            while (randomNumber == 8 || randomNumber == 16 || randomNumber == 26) {
                randomNumber = (int) (Math.random() * files.length);
            }
            File audio = files[randomNumber];
            try {
                audioInputStream = AudioSystem.getAudioInputStream(audio.getAbsoluteFile());
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                clip.open(audioInputStream);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            clip.start();
            System.out.println("Manually Playing " + audio.getName());
        }

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
}