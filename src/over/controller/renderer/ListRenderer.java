package over.controller.renderer;

import javax.swing.*;
import java.awt.*;

/**
 * <code>ListRenderer</code> class modifies the <code>JList</code>'s classical view and includes an icon for each item added.
 *
 * @author Overload Inc.
 * @version 1.0, 09 Jan 2020
 */
public class ListRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel lblIcon = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        lblIcon.setIcon(new ImageIcon(getClass().getResource("/over/res/img/cocorin_connected.png")));
        
        return lblIcon;
    }
}