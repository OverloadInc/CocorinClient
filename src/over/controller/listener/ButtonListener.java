package over.controller.listener;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonListener implements MouseListener {
    private JButton btnSend;

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