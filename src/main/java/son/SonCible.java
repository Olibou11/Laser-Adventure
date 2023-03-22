// Importation package
package son;

// Importations

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

// Classe ''SonCible''
public class SonCible {

    public static void jouer() {
        try {
            File cheminFichier = new File("son/cible.wav");
            if (cheminFichier.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(cheminFichier);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*
Theme: Steve hurt sound
Game: Minecraft
© Microsoft 2012. All rights reserved
*/