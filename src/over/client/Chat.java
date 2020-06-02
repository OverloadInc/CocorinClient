package over.client;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class Chat extends JFrame {
    private JButton btnSend;
    private JPanel mainPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JScrollPane scrollConsole;
    private JScrollPane scrollList;
    private JScrollPane scrollMessage;
    private JTextPane txtConsole;
    private JTextPane txtMessage;
    private JList<String> userList;
    private DefaultListModel<String> model;
    private FontEditor fontEditor;

    /**
     * Specifies the default application's port number.
     */
    private final String PORT ="10101";

    /**
     * Specifies the default server IP (localhost).
     */
    private final String IP ="127.0.0.1";

    /**
     * Instance for the client which communicates with the server.
     */
    private final Client client;

    /**
     * Class constructor.
     */
    public Chat() {
        initComponents();

        String portData[] = getPortData();
        String portIP = portData[0];
        String portNumber = portData[1];
        String portName = portData[2];

        client = new Client(this, portIP, Integer.valueOf(portNumber), portName);

        playSound("bubble_wav.wav");
    }

    /**
     * Initializes the chat components.
     */
    private void initComponents() {
        model = new DefaultListModel<>();
        fontEditor = new FontEditor();
        mainPanel = new JPanel();
        scrollConsole = new JScrollPane();
        txtConsole = new JTextPane();
        scrollList = new JScrollPane();
        userList = new JList<>(model);
        scrollMessage = new JScrollPane();
        txtMessage = new JTextPane();
        btnSend = new JButton();
        northPanel = new JPanel();
        southPanel = new JPanel();
        GridBagConstraints gridBagConstraints;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat client");
        setIconImage(getIcon().getImage());
        setMinimumSize(new Dimension(600, 600));
        setName("frmChat");
        setPreferredSize(new Dimension(600, 600));

        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        mainPanel.setName("mainPanel");
        mainPanel.setLayout(new GridBagLayout());

        txtConsole.setName("txtConsole");

        scrollConsole.setName("scrollConsole");
        scrollConsole.setViewportView(txtConsole);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(0, 10, 0, 10);

        mainPanel.add(scrollConsole, gridBagConstraints);

        userList.setName("userList");
        userList.setPreferredSize(new Dimension(126, 150));
        userList.setVisibleRowCount(15);

        scrollList.setName("scrollList");
        scrollList.setViewportView(userList);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(0, 0, 0, 10);

        mainPanel.add(scrollList, gridBagConstraints);

        txtMessage.setName("txtMessage");

        scrollMessage.setName("scrollMessage");
        scrollMessage.setViewportView(txtMessage);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);

        mainPanel.add(scrollMessage, gridBagConstraints);

        btnSend.setText("Send");
        btnSend.setName("btnSend");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                sendMessage(event);
            }
        });

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new Insets(10, 0, 10, 10);

        mainPanel.add(btnSend, gridBagConstraints);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        GroupLayout northPanelLayout = new GroupLayout(northPanel);
        northPanel.setName("northPanel");
        northPanel.setLayout(northPanelLayout);

        northPanelLayout.setHorizontalGroup(northPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 600, Short.MAX_VALUE));
        northPanelLayout.setVerticalGroup(northPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));

        getContentPane().add(northPanel, BorderLayout.NORTH);

        GroupLayout southPanelLayout = new GroupLayout(southPanel);
        southPanel.setName("southPanel");
        southPanel.setLayout(southPanelLayout);

        southPanelLayout.setHorizontalGroup(southPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 600, Short.MAX_VALUE));
        southPanelLayout.setVerticalGroup(southPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));

        getContentPane().add(southPanel, BorderLayout.SOUTH);

        pack();

        setLocationRelativeTo(null);
    }

    /**
     * Windows closed event.
     * @param event the event occurred in the window.
     */
    public void formWindowClosed(WindowEvent event) {
        client.acceptDisconnection();
    }

    /**
     * Window closing event.
     * @param event the event occurred in the window.
     */
    public void formWindowClosing(WindowEvent event) {
        client.acceptDisconnection();
    }

    /**
     * Gets the icon for the chat application.
     * @return the icon's path for the application.
     */
    private ImageIcon getIcon() {
        URL iconURL = getClass().getResource("/over/res/img/icon_title.png");

        return new ImageIcon(iconURL);
    }

    /**
     * Plays a sound every time the chat is started.
     * @param url the sound's path.
     */
    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();

                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Client.class.getResourceAsStream("/over/res/sound/" + url));

                    clip.open(inputStream);
                    clip.start();
                }
                catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    /**
     * Displays a warning message every time the user tries to send a message to a disconnected client.
     * @param event the event.
     */
    public void sendMessage(ActionEvent event) {
        if(userList.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(null, "Please, select a valid target user or wait for connected users.");

            return;
        }

        String receiver = userList.getSelectedValue().toString();
        String message = txtMessage.getText();

        client.sendMessage(receiver, message);

        fontEditor.setSimple(txtConsole, "## Me -> " + receiver + " ## : \n" + message + "\n");

        txtMessage.setText("");
    }

    /**
     * Adds a new connected user to the list.
     * @param user the connected user.
     */
    void addConnectedUser(String user) {
        model.addElement(user);
    }

    /**
     * Adds a new message to the console.
     * @param transmitter the user who sends the message.
     * @param message the current message.
     */
    void addMessage(String transmitter, String message) {
        fontEditor.setSimple(txtConsole, "##### " + transmitter + " ##### : \n" + message + "\n");
    }

    /**
     * Adds the tittle for the window session.
     * @param id the id for the current session.
     */
    void initSession(String id) {
        this.setTitle("Session:\t" + id + "\tinitiated.");
    }

    /**
     * Builds a new window to enter the host IP, port number, and the client's name.
     * @return the array which contains the server IP, connection PORT and the client's user name.
     */
    private String[] getPortData() {
        String data[] = new String[3];

        data[0] = IP;
        data[1] = PORT;

        JTextField txtIP = new JTextField(20);
        txtIP.setText(IP);

        JTextField txtPort = new JTextField(20);
        txtPort.setText(PORT);

        JTextField txtUser = new JTextField(20);
        txtUser.setText("User name");

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(3, 2));
        dataPanel.add(new JLabel("Serve IP:"));
        dataPanel.add(txtIP);
        dataPanel.add(new JLabel("Connection port:"));
        dataPanel.add(txtPort);
        dataPanel.add(new JLabel("Enter your user name:"));
        dataPanel.add(txtUser);

        int result = JOptionPane.showConfirmDialog(null, dataPanel,"Communication configuration", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            data[0] = txtIP.getText();
            data[1] = txtPort.getText();
            data[2] = txtUser.getText();
        }
        else
            System.exit(0);

        return data;
    }

    /**
     * Removes a connected user of the list whenever the session is finished.
     * @param id the user Id to remove from the list.
     */
    void removeUser(String id) {
        for (int i = 0; i < model.getSize(); i++) {
            if(model.getElementAt(i).toString().equals(id)){
                model.remove(i);
                return;
            }
        }
    }

    /**
     * Starts the applications
     * @param args the initial input parameters for the application.
     */
    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chat().setVisible(true);
            }
        });
    }
}