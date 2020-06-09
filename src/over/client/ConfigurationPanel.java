package over.client;

import javax.swing.*;
import java.awt.*;

/**
 * ConfigurationPanel class builds a JPanel to enter the configuration data necessary to communicate
 * the chat application with the target server.
 */
public class ConfigurationPanel {

    /**
     * Sets the main configuration panel.
     */
    private JPanel dataPanel;

    /**
     * Sets the target server IP.
     */
    private JTextField txtIP;

    /**
     * Sets the communication port.
     */
    private JTextField txtPort;

    /**
     * Sets the user name.
     */
    private JTextField txtUser;

    /**
     * Specifies the default application's port number.
     */
    private final String PORT ="10101";

    /**
     * Specifies the default server IP (localhost: 127.0.0.1).
     */
    private final String IP ="10.156.30.207";

    /**
     * Builds a JPanel to enter the target server's communication data.
     * @return the configuration JPanel.
     */
    public JPanel getConfigurationPanel() {
        GridBagConstraints gridBagConstraints;

        dataPanel = new JPanel();
        dataPanel.setName("dataPanel");
        dataPanel.setLayout(new java.awt.GridBagLayout());

        JLabel lblLogo = new JLabel();
        lblLogo.setName("lblLogo");
        lblLogo.setIcon(new ImageIcon(getClass().getResource("/over/res/img/cocorin_color_350.png")));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;

        dataPanel.add(lblLogo, gridBagConstraints);

        JLabel lblIP = new JLabel();
        lblIP.setText("IP Address");
        lblIP.setName("lblIP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;

        dataPanel.add(lblIP, gridBagConstraints);

        txtIP = new JTextField(20);
        txtIP.setName("txtIP");
        txtIP.setText(IP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        dataPanel.add(txtIP, gridBagConstraints);

        JLabel lblPort = new JLabel();
        lblPort.setText("Port number");
        lblPort.setName("lblPort");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        dataPanel.add(lblPort, gridBagConstraints);

        txtPort = new JTextField(20);
        txtPort.setName("txtPort");
        txtPort.setText(PORT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        dataPanel.add(txtPort, gridBagConstraints);

        JLabel lblUser = new JLabel();
        lblUser.setText("User name");
        lblUser.setName("lblUser");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        dataPanel.add(lblUser, gridBagConstraints);

        txtUser = new JTextField(20);
        txtUser.setText("User");
        txtUser.setName("txtUser");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        dataPanel.add(txtUser, gridBagConstraints);

        return dataPanel;
    }

    /**
     * Gets the application's configuration data.
     * @return the array with the configuration data.
     */
    public String[] getConfigurationData() {
        String data[] = new String[3];

        int result = JOptionPane.showConfirmDialog(null, getConfigurationPanel(),"Communication configuration", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            data[0] = txtIP.getText();
            data[1] = txtPort.getText();
            data[2] = txtUser.getText();
        }
        else {
            data[0] = IP;
            data[1] = PORT;

            System.exit(0);
        }

        return data;
    }
}