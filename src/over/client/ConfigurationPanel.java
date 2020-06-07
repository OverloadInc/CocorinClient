package over.client;

import javax.swing.*;
import java.awt.*;

public class ConfigurationPanel {

    /**
     *
     */
    private JPanel dataPanel;

    /**
     *
     */
    private JTextField txtIP;

    /**
     *
     */
    private JTextField txtPort;

    /**
     *
     */
    private JTextField txtUser;

    /**
     * Specifies the default application's port number.
     */
    private final String PORT ="10101";

    /**
     * Specifies the default server IP (localhost).
     */
    private final String IP ="127.0.0.1";

    /**
     *
     * @return
     */
    public JPanel getConfigurationPanel() {
        txtIP = new JTextField(20);
        txtIP.setText(IP);

        txtPort = new JTextField(20);
        txtPort.setText(PORT);

        txtUser = new JTextField(20);
        txtUser.setText("User");

        dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(3, 2));
        dataPanel.add(new JLabel("Server IP:"));
        dataPanel.add(txtIP);
        dataPanel.add(new JLabel("Connection port:"));
        dataPanel.add(txtPort);
        dataPanel.add(new JLabel("Enter your user name:"));
        dataPanel.add(txtUser);

        return dataPanel;
    }

    /**
     *
     * @return
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