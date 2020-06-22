package over.controller.listener;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ListListener implements MouseListener {
    private JList userList;
    private JScrollPane scrollConsole;
    private JTextPane txtConsole;
    private JTabbedPane tabbedPane;

    public ListListener(JList userList, JScrollPane scrollConsole, JTextPane txtConsole, JTabbedPane tabbedPane) {
        this.userList = userList;
        this.scrollConsole = scrollConsole;
        this.txtConsole = txtConsole;
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        doubleClic(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Adds a new JTextPane to the JTabbedPane whenever the user make a double clic.
     * @param e the mouse event.
     */
    public void doubleClic(MouseEvent e) {
        if (e.getClickCount() == 2) {
            scrollConsole = new JScrollPane();
            txtConsole = new JTextPane();

            txtConsole.setName("txtConsole");
            txtConsole.setEditable(false);

            scrollConsole.setName("scrollConsole");
            scrollConsole.setViewportView(txtConsole);

            tabbedPane.setName("tabbedPane");
            tabbedPane.addTab((String) userList.getSelectedValue(), scrollConsole);
        }
    }
}