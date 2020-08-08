package over.controller.renderer;

import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * <code>TabbedPaneRenderer</code> class creates a <code>JTabbedPane</code> and auto-sets a close button whenever
 * a tab is added.
 *
 * @author Overload Inc.
 * @version 1.0, 09 Jan 2020
 */
public class TabbedPaneRenderer extends JTabbedPane {

    /**
     * Class constructor.
     */
    public TabbedPaneRenderer() {
        super();
    }

    /**
     * Overrides the <code>addTab</code> method to add the <code>close</code> button.
     * @param title the <code>JTabbedPane</code> title.
     * @param icon the <code>JTabbedPane</code> icon.
     * @param component the <code>Component</code> to add.
     * @param tooltip the <code>JTabbedPane</code> tool tip text.
     */
    @Override
    public void addTab(String title, Icon icon, Component component, String tooltip) {
        super.addTab(title, icon, component, tooltip);

        int count = this.getTabCount() - 1;

        setTabComponentAt(count, new CloseButtonTab(component, title, icon));
    }

    /**
     * Overrides the <code>addTab</code> method to add the <code>close</code> button.
     * @param title the <code>JTabbedPane</code> title.
     * @param icon the <code>JTabbedPane</code> icon.
     * @param component the <code>Component</code> to add.
     */
    @Override
    public void addTab(String title, Icon icon, Component component) {
        addTab(title, icon, component, null);
    }

    /**
     * Overrides the <code>addTab</code> method to add the <code>close</code> button.
     * @param title the <code>JTabbedPane</code> title.
     * @param component the <code>Component</code> to add.
     */
    @Override
    public void addTab(String title, Component component) {
        addTab(title, null, component);
    }

    /**
     * Adds a new tab without the <code>close</code> button.
     * @param title the <code>JTabbedPane</code> title.
     * @param icon the <code>JTabbedPane</code> icon.
     * @param component the <code>Component</code> to add.
     * @param tooltip the tool tip text of the <code>JTabbedPane</code>.
     */
    public void addTabNoExit(String title, Icon icon, Component component, String tooltip) {
        super.addTab(title, icon, component, tooltip);
    }

    /**
     * Adds a new tab without the <code>close</code> button.
     * @param title  the <code>JTabbedPane</code> title.
     * @param icon the <code>JTabbedPane</code> icon.
     * @param component the <code>Component</code> to add.
     */
    public void addTabNoExit(String title, Icon icon, Component component) {
        addTabNoExit(title, icon, component, null);
    }

    /**
     * Adds a new tab without the <code>close</code> button.
     * @param title the <code>JTabbedPane</code> title.
     * @param component the <code>Component</code> to add.
     */
    public void addTabNoExit(String title, Component component) {
        addTabNoExit(title, null, component);
    }

    /**
     * Inner class to add the <code>close</code> button to the <code>JTabbedPane</code>.
     */
    public class CloseButtonTab extends JPanel {
        private Component tab;

        /**
         * Inner class constructor.
         * @param tab the component to add.
         * @param title the title to add.
         * @param icon the icon to add.
         */
        public CloseButtonTab(final Component tab, String title, Icon icon) {
            this.tab = tab;
            setOpaque(false);

            FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 3, 3);
            setLayout(flowLayout);

            JLabel lblTitle = new JLabel(title);
            lblTitle.setIcon(icon);
            add(lblTitle);

            JButton btnClose = new JButton(MetalIconFactory.getInternalFrameCloseIcon(16));
            btnClose.setMargin(new Insets(0, 0, 0, 0));
            btnClose.addMouseListener(new CloseListener(tab));
            add(btnClose);
        }
    }

    /**
     * Inner class to control the mouse events.
     */
    public class CloseListener implements MouseListener {
        private Component tab;

        /**
         * Inner class constructor.
         * @param tab the JTappedPane component.
         */
        public CloseListener(Component tab) {
            this.tab = tab;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() instanceof JButton){
                JButton clickedButton = (JButton) e.getSource();

                JTabbedPane tabbedPane = (JTabbedPane) clickedButton.getParent().getParent().getParent();
                tabbedPane.remove(tab);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(e.getSource() instanceof JButton){
                JButton clickedButton = (JButton) e.getSource();
                clickedButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource() instanceof JButton){
                JButton clickedButton = (JButton) e.getSource();
                clickedButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,3));
            }
        }
    }
}