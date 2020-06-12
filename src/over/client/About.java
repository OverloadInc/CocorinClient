package over.client;

import over.controller.FontEditor;

import javax.swing.*;
import java.awt.*;

public class About extends JFrame {
    private JPanel aboutPanel;
    private JScrollPane aboutScroll;
    private JPanel creditsPanel;
    private JScrollPane creditsScroll;
    private JLabel lblLogo;
    private JPanel softwarePanel;
    private JTabbedPane tabbedPane;
    private JTextPane txtAbout;
    private JTextPane txtCredits;

    public About() {
        initComponents();
        setAbout();
    }

    private void initComponents() {
        softwarePanel = new JPanel();
        lblLogo = new JLabel();
        tabbedPane = new JTabbedPane();
        aboutPanel = new JPanel();
        aboutScroll = new JScrollPane();
        txtAbout = new JTextPane();
        creditsPanel = new JPanel();
        creditsScroll = new JScrollPane();
        txtCredits = new JTextPane();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About");
        setName("frmAbout");
        setResizable(false);

        softwarePanel.setName("softwarePanel");
        softwarePanel.setLayout(new BorderLayout());

        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setName("lblLogo");
        lblLogo.setIcon(new ImageIcon(getClass().getResource("/over/res/img/cocorin_about.png")));
        softwarePanel.add(lblLogo, BorderLayout.CENTER);

        getContentPane().add(softwarePanel, BorderLayout.CENTER);

        tabbedPane.setName("tabbedPane");

        aboutPanel.setMaximumSize(new Dimension(400, 100));
        aboutPanel.setMinimumSize(new Dimension(400, 100));
        aboutPanel.setName("aboutPanel");
        aboutPanel.setPreferredSize(new Dimension(400, 100));
        aboutPanel.setLayout(new BorderLayout());

        aboutScroll.setName("aboutScroll");

        txtAbout.setName("txtAbout");
        txtAbout.setEditable(false);
        aboutScroll.setViewportView(txtAbout);

        aboutPanel.add(aboutScroll, BorderLayout.CENTER);

        tabbedPane.addTab("About", aboutPanel);

        creditsPanel.setMaximumSize(new Dimension(400, 100));
        creditsPanel.setMinimumSize(new Dimension(400, 100));
        creditsPanel.setName("creditsPanel");
        creditsPanel.setPreferredSize(new Dimension(400, 100));
        creditsPanel.setLayout(new BorderLayout());

        creditsScroll.setName("creditsScroll");

        txtCredits.setName("txtCredits");
        txtCredits.setEditable(false);
        creditsScroll.setViewportView(txtCredits);

        creditsPanel.add(creditsScroll, BorderLayout.CENTER);

        tabbedPane.addTab("Credits", creditsPanel);

        getContentPane().add(tabbedPane, BorderLayout.SOUTH);

        pack();

        setLocationRelativeTo(null);
    }

    public void setAbout() {
        String application = "Cocorin";
        String description = " is an open source chat application and is licensed under GNU General Public License v3.";
        String team = "Developer";
        String company = "\tOverload Inc.";
        String developer01 = "\n\tJuan-Alberto Hernández-Martínez";
        String contact = "\nContact";
        String email01 = "\toverload.inc.mx@gmail.com";
        String email02 = "\n\tjuan.alberto.hernandez.martinez@gmail.com";

        try {
            FontEditor fontEditor = new FontEditor();
            fontEditor.setBold(txtAbout, application);
            fontEditor.setSimple(txtAbout, description);
            fontEditor.setBold(txtCredits, team);
            fontEditor.setSimple(txtCredits, company);
            fontEditor.setSimple(txtCredits, developer01);
            fontEditor.setSimple(txtCredits,"\n");
            fontEditor.setBold(txtCredits, contact);
            fontEditor.setSimple(txtCredits, email01);
            fontEditor.setSimple(txtCredits, email02);
        }
        catch(Exception e) {
        }
    }
}