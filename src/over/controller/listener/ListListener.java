package over.controller.listener;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * <code>ListListener</code> class provides the mechanism to create a tabbed pane for each user conversation.
 *
 * @author Overload Inc.
 * @version 1.0, 09 Jan 2020
 */
public class ListListener implements MouseListener {

    /**
     * The connected users <code>JList</code> instance.
     */
    private JList userList;

    /**
     * The console <code>JScrollPane</code> instance.
     */
    private JScrollPane scrollConsole;

    /**
     * The console <code>JTextPane</code> instance.
     */
    private JTextPane txtConsole;

    /**
     * The <code>JTabbedPane</code> instance.
     */
    private JTabbedPane tabbedPane;

    /**
     * Class constructor.
     * @param userList the connected users list.
     * @param scrollConsole the console scrollPane.
     * @param txtConsole the messages console.
     * @param tabbedPane the conversations tabbed pane.
     */
    public ListListener(JList userList, JScrollPane scrollConsole, JTextPane txtConsole, JTabbedPane tabbedPane) {
        this.userList = userList;
        this.scrollConsole = scrollConsole;
        this.txtConsole = txtConsole;
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        doubleClick(e);
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
     * Adds a new <code>JTextPane</code> to the <code>JTabbedPane</code> whenever the user make a double click.
     * @param e the mouse event.
     */
    public void doubleClick(MouseEvent e) {
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