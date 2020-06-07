package over.client;

import over.controller.ButtonListener;
import over.controller.FrameListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * Class that implements a Chat window to communicate a set of connected users vía sockets.
 */
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
    private JLabel lblSession;
    private JLabel lblOverload;
    private JList<String> userList;
    private DefaultListModel<String> model;
    private FontEditor fontEditor;



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
    }

    /**
     * Initializes the chat components.
     */
    private void initComponents() {
        btnSend = new JButton();
        northPanel = new JPanel();
        southPanel = new JPanel();
        mainPanel = new JPanel();
        scrollConsole = new JScrollPane();
        scrollList = new JScrollPane();
        scrollMessage = new JScrollPane();
        txtConsole = new JTextPane();
        txtMessage = new JTextPane();
        lblSession = new JLabel();
        lblOverload = new JLabel();
        model = new DefaultListModel<>();
        userList = new JList<>(model);
        fontEditor = new FontEditor();

        GridBagConstraints gridBagConstraints;

        setTitle("JChat v1.0");
        setName("frmChat");
        setIconImage(FrameListener.getFrameListener().getIcon().getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setPreferredSize(new Dimension(800, 600));

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

        btnSend.setName("btnSend");
        btnSend.setToolTipText("Send a message");
        btnSend.setIcon(new ImageIcon(getClass().getResource("/over/res/img/close_mail_01.png")));
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(e);
            }
        });
        btnSend.addMouseListener(new ButtonListener(btnSend));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new Insets(10, 0, 10, 10);

        mainPanel.add(btnSend, gridBagConstraints);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        northPanel.setName("northPanel");
        northPanel.setLayout(new BorderLayout());

        lblSession.setHorizontalAlignment(SwingConstants.CENTER);
        lblSession.setHorizontalTextPosition(SwingConstants.CENTER);
        lblSession.setMinimumSize(new Dimension(100, 25));
        lblSession.setName("lblSession");
        lblSession.setPreferredSize(new Dimension(100, 25));

        northPanel.add(lblSession, BorderLayout.CENTER);

        getContentPane().add(northPanel, BorderLayout.NORTH);

        southPanel.setName("southPanel");
        southPanel.setLayout(new BorderLayout());

        lblOverload.setName("lblOverload");
        lblOverload.setText("Powered by Overload Inc.");
        lblOverload.setHorizontalAlignment(SwingConstants.CENTER);
        lblOverload.setHorizontalTextPosition(SwingConstants.CENTER);
        lblOverload.setMinimumSize(new Dimension(100, 25));
        lblOverload.setPreferredSize(new Dimension(100, 25));

        southPanel.add(lblOverload, BorderLayout.CENTER);

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

        fontEditor.setBold(txtConsole, client.getClientId() + " => " + receiver + ":");
        fontEditor.setSimple(txtConsole,message + "\n");

        txtMessage.setText("");
    }

    /**
     * Adds a new connected user to the list.
     * @param user the connected user.
     */
    public void addConnectedUser(String user) {
        model.addElement(user);
    }

    /**
     * Adds a new message to the console.
     * @param transmitter the user who sends the message.
     * @param message the current message.
     */
    public void addMessage(String transmitter, String message) {
        fontEditor.setBold(txtConsole, transmitter + ":");
        fontEditor.setSimple(txtConsole, message + "\n");
    }

    /**
     * Adds the tittle for the window session.
     * @param id the id for the current session.
     */
    public void initSession(String id) {
        FrameListener.getFrameListener().playSound("bubble_wav.wav");

        lblSession.setText("Session: <" + id + "> started.");
    }

    /**
     * Builds a new window to enter the host IP, port number, and the client's name.
     * @return the array which contains the server IP, connection PORT and the client's user name.
     */
    private String[] getPortData() {
        return new ConfigurationPanel().getConfigurationData();
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