package over.controller;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;

public class ListRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setIcon(new ImageIcon(getClass().getResource("/over/res/img/cocorin_connected.png")));
        
        return label;
    }
}