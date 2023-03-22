// Importation package
package son;

// Importations

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

// Classe ''SonMenu''
public class SonMenu {

    // Déclaration du clip pour la musique du menu

    private static Clip clip;
    public static Clip getClip() { return clip; }

    public static void jouer() {

        try {
            File cheminFichier = new File("son/menu.wav");
            if (cheminFichier.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(cheminFichier);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*
Theme: Central Highway (Opening Stage)
Game: Mega Man X
Track #: 4
Platform(s): Super Nintendo Entertainment System (SNES)
Composer(s): Setsuo Yamamoto, Makato Tomozawa, Yuki Iwai, Yuko Takehara, Toshihiko Horiyama
© 1993 CAPCOM. All Rights Reserved
*/