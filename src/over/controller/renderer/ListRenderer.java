package over.controller.renderer;

import javax.swing.*;
import java.awt.*;

/**
 * ListRenderer class modifies the JList's classical view and includes an icon for each item added.
 */
public class ListRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel lblIcon = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        lblIcon.setIcon(new ImageIcon(getClass().getResource("/over/res/img/cocorin_connected.png")));
        
        return lblIcon;
    }
}