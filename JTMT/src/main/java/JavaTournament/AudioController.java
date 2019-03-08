package JavaTournament;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioController {
  public static Clip clip;

        public static void startAudio(String name) {
            URL resource = ScreenController.class.getResource("/"  + name);

            AudioInputStream inputStream = null;
            try {
                inputStream = AudioSystem.getAudioInputStream(resource);
                DataLine.Info info = new DataLine.Info(Clip.class, inputStream.getFormat());
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(inputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

        }
        public static void stopAudio(){
            clip.stop();
        }
}
