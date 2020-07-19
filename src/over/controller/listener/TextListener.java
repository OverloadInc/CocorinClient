package over.controller.listener;

import over.client.Chat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * <code>TextListener</code> class provides the methods necessary to perform actions whenever the user presses a
 * specific key.
 *
 * @author Overload Inc.
 * @version %I%, %G%
 */
public class TextListener implements KeyListener {

    /**
     * The <code>Chat</code> client instance.
     */
    private Chat chat;

    /**
     * Class constructor.
     * @param chat the <code>Chat</code> client instance.
     */
    public TextListener(Chat chat) {
        this.chat = chat;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        chat.sendMessage(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}