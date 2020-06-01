package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Chat extends JFrame {
    DefaultListModel<String> model = new DefaultListModel<>();
    private JPanel mainPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel centerPanel;
    private JTextPane txtConsole;
    private JList userList = new JList(model);
    private JTextPane txtMessage;
    private JButton btnSend;
    private FontEditor fontEditor = new FontEditor();

    /**
     * Constante que almacena el puerto por defecto para la aplicación.
     */
    private final String DEFAULT_PORT="10101";

    /**
     * Constante que almacena la IP por defecto (localhost) para el servidor.
     */
    private final String DEFAULT_IP="127.0.0.1";

    /**
     * Constante que almacena el cliente, con el cual se gestiona la comunicación
     * con el servidor.
     */
    private final Client client;

    public Chat() {
        String ip_puerto_nombre[] = getIP_Puerto_Nombre();
        String ip = ip_puerto_nombre[0];
        String puerto = ip_puerto_nombre[1];
        String nombre = ip_puerto_nombre[2];

        client = new Client(this, ip, Integer.valueOf(puerto), nombre);

        setContentPane(new Chat().mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                sendMessage(event);
            }
        });

        pack();
    }

    public void formWindowClosed(WindowEvent event) {
    }

    public void formWindowClosing(WindowEvent event) {
        client.confirmarDesconexion();
    }

    public void sendMessage(ActionEvent e) {
        //Si no hay más clientes del chat con quien comunicarse.
        if(userList.getSelectedValue() == null){
            JOptionPane.showMessageDialog(null, "Debe escoger un destinatario válido, si no \n"
                    + "hay uno, espere a que otro usuario se conecte\n"
                    + "para poder chatear con él.");
            return;
        }
        String cliente_receptor=userList.getSelectedValue().toString();
        String mensaje=txtMessage.getText();
        client.enviarMensaje(cliente_receptor, mensaje);
        //se agrega en el historial de la conversación lo que el cliente ha dicho
        fontEditor.setSimple(txtConsole, "## Yo -> "+cliente_receptor+ " ## : \n" + mensaje+"\n");
        txtMessage.setText("");
    }

    /**
     * Agrega un contacto al JComboBox de contactos.
     * @param contacto
     */
    void addContacto(String contacto) {
        //cmbContactos.addItem(contacto);
        model.addElement(contacto);
    }

    /**
     * Agrega un nuevo mensaje al historial de la conversación.
     * @param emisor
     * @param mensaje
     */
    void addMensaje(String emisor, String mensaje) {
        fontEditor.setSimple(txtConsole, "##### "+emisor + " ##### : \n" + mensaje+"\n");
    }

    /**
     * Se configura el título de la ventana para una nueva sesión.
     * @param identificador
     */
    void sesionIniciada(String identificador) {
        this.setTitle(" --- "+identificador+" --- ");
    }

    /**
     * Método que abre una ventana para que el usuario ingrese la IP del host en
     * el que corre el servidor, el puerto con el que escucha y el nombre con el
     * que quiere participar en el chat.
     * @return
     */
    private String[] getIP_Puerto_Nombre() {
        String s[]=new String[3];
        s[0]=DEFAULT_IP;
        s[1]=DEFAULT_PORT;
        JTextField ip = new JTextField(20);
        JTextField puerto = new JTextField(20);
        JTextField usuario = new JTextField(20);
        ip.setText(DEFAULT_IP);
        puerto.setText(DEFAULT_PORT);
        usuario.setText("Usuario");
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(3, 2));
        myPanel.add(new JLabel("IP del Servidor:"));
        myPanel.add(ip);
        myPanel.add(new JLabel("Puerto de la conexión:"));
        myPanel.add(puerto);
        myPanel.add(new JLabel("Escriba su nombre:"));
        myPanel.add(usuario);
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Configuraciones de la comunicación", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            s[0]=ip.getText();
            s[1]=puerto.getText();
            s[2]=usuario.getText();
        }
        else{
            System.exit(0);
        }
        return s;
    }

    /**
     * Método que elimina cierto cliente de la lista de contactos, este se llama
     * cuando cierto usuario cierra sesión.
     * @param identificador
     */
    void eliminarContacto(String identificador) {
        /*for (int i = 0; i < cmbContactos.getItemCount(); i++) {
            if(cmbContactos.getItemAt(i).toString().equals(identificador)){
                cmbContactos.removeItemAt(i);
                return;
            }
        }*/
    }

    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        JFrame frame = new JFrame("JChat Client");

        frame.setVisible(true);
    }
}