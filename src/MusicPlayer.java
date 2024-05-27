import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;
public class MusicPlayer implements Runnable { // Not currently in use, to be re-integrated
    private ArrayList<String> musicFiles;
    private int currentSongIndex;
    private Clip clip;
    public MusicPlayer (String... files ) {
        musicFiles = new ArrayList<String>(); // Array for the music files
        for(String file : files) {musicFiles.add("./media/music/" + file + ".wav");} // Adding the path files and file type
        // Just looking for the file name in the correct folder
    }
    private void playSound(String fileName) {
        try {
            File soundFile = new File(fileName);
            // Reference: https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/AudioInputStream.html
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = ais.getFormat();
            // Creating the clip sub-interface: https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/DataLine.Info.html#:~:text=minBufferSize%2C%20int%20maxBufferSize)-,Constructs%20a%20data%20line%27s%20info%20object%20from%20the%20specified%20information,information%20about%20a%20supported%20line.
            DataLine.Info info = new  DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10); // Lower music volume
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Continuous loop
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    @Override
    public void run() { // Play music
        playSound(musicFiles.get(currentSongIndex));
    }
    public void stop() { // Stop music and close clip
        if(clip.isRunning()) {clip.stop();}
        clip.close();
    }
}
