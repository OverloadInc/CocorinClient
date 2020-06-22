package over.controller.listener;

import over.client.Client;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.net.URL;

/**
 * FrameListener class provides the mechanisms necessary to change the frame icon and play sounds for
 * user's messages notifications.
 */
public class FrameListener {

    /**
     * The FrameListener class instance.
     */
    private static FrameListener frameListener;

    /**
     * Private class constructor.
     */
    private FrameListener() {
    }

    /**
     * Gets a FrameListener instance.
     * @return the FrameListener instance.
     */
    public static FrameListener getFrameListener() {
        if(frameListener == null)
            frameListener = new FrameListener();

        return frameListener;
    }

    /**
     * Gets the icon for the chat application.
     * @return the icon's path for the application.
     */
    public ImageIcon getIcon() {
        URL iconURL = getClass().getResource("/over/res/img/cocorin_frame.png");

        return new ImageIcon(iconURL);
    }

    /**
     * Plays a sound every time the chat is started.
     * @param url the sound's path.
     */
    public synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();

                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Client.class.getResourceAsStream("/over/res/sound/" + url));

                    clip.open(inputStream);
                    clip.start();
                }
                catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}