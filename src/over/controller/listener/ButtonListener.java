package over.controller.listener;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * <code>ButtonListener</code> provides the methods to change the <code>Send</code> button's icon whenever the user
 * press, release, enter or exit the mouse. In addition, it provides the methods to react to a key pressed.
 *
 * @author Overload Inc.
 * @version 1.0, 09 Jan 2020
 */
public class ButtonListener implements MouseListener {

    /**
     * The <code>Send JButton</code> instance.
     */
    private JButton btnSend;

    /**
     * Class constructor.
     * @param btnSend the <code>JButton</code> instance.
     */
    public ButtonListener(JButton btnSend) {
        this.btnSend = btnSend;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        btnSend.setIcon(new ImageIcon(getClass().getResource("/over/res/img/open_mail_01.png")));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        btnSend.setIcon(new ImageIcon(getClass().getResource("/over/res/img/close_mail_01.png")));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        btnSend.setIcon(new ImageIcon(getClass().getResource("/over/res/img/open_mail_01.png")));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        btnSend.setIcon(new ImageIcon(getClass().getResource("/over/res/img/close_mail_01.png")));
    }
}