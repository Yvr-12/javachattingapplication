package chatapp;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

/**
 * Client class for the chat application.
 * Handles GUI, message sending/receiving, encryption, and chat history.
 */
public class client implements ActionListener {
    // Text field for user input
    JTextField text;
    // Main chat panel (static for access in static methods)
    static JPanel a1;
    // Vertical box to stack chat messages
    static Box vertical = Box.createVerticalBox();
    // Main application window
    static JFrame f = new JFrame();
    // Output stream to send messages to the server
    static DataOutputStream dout;
    // Username of the client user
    static String username;

    /**
     * Constructor sets up the GUI for the client chat window.
     */
    client() {
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        // image1-back button
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));// address of image on panel
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);// scaling of return arrow
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3); // to show the image on panel
        back.setBounds(5, 20, 25, 25); // coordinates of the image
        p1.add(back);
        // closing of application on back button click
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                f.setVisible(false);
            }
        });

        // image2-profile
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/download (1).png"));// address of image on
                                                                                              // panel
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);// scaling of return arrow
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6); // to show the image on panel
        profile.setBounds(40, 10, 50, 50); // coordinates of the image
        p1.add(profile);

        // image3-video icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));// address of image on panel
        Image i8 = i7.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);// scaling of return arrow
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9); // to show the image on panel
        video.setBounds(300, 20, 30, 30); // coordinates of the image
        p1.add(video);

        // image4-phone icon
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));// address of image on panel
        Image i11 = i10.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);// scaling of return arrow
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12); // to show the image on panel
        phone.setBounds(360, 20, 35, 30); // coordinates of the image
        p1.add(phone);

        // image5-morevert(3 dots)
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));// address of image on panel
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);// scaling of return arrow
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15); // to show the image on panel
        morevert.setBounds(420, 20, 10, 25); // coordinates of the image
        p1.add(morevert);

        // name of user
        JLabel name = new JLabel("Receiver");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);// color of text
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));// font size
        p1.add(name);

        // active now
        JLabel status = new JLabel("Active now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.WHITE);// color of text
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));// font size
        p1.add(status);

        // Main chat area with scroll pane for scrolling through chat history
        a1 = new JPanel();
        a1.setLayout(new BoxLayout(a1, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(a1);
        scrollPane.setBounds(5, 75, 440, 570);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        f.add(scrollPane);

        // Text field for typing messages
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        // Add ActionListener for Enter key to send message
        text.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Simulate Send button click by calling the same method
                client.this.actionPerformed(new ActionEvent(text, ActionEvent.ACTION_PERFORMED, null));
            }
        });
        f.add(text);

        // Send button
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);// action needed on pressing the button. main action in action listener method
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(send);

        // Window settings
        f.setSize(450, 700);// size of frame
        f.setLocation(800, 50);// location of frame
        // Remove undecorated to allow dragging and resizing
        // f.setUndecorated(true);// remove header of frame
        f.getContentPane().setBackground(Color.WHITE);// color of frame
        f.setMinimumSize(new Dimension(350, 400)); // set minimum size
        f.setResizable(true); // allow resizing
        f.setVisible(true);// visibility of frame
    }

    /**
     * Handles the send button click event.
     * Encrypts and sends the message, updates the chat window, and saves to
     * history.
     */
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = username + ": " + text.getText();
            JPanel p2 = formatLabel(out);
            a1.setLayout(new BorderLayout());
            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setOpaque(false);
            // Sent message: align right
            wrapper.add(p2, BorderLayout.LINE_END);
            vertical.add(wrapper);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(CryptoUtils.encrypt(out));
            saveMessageToFile(out); // Save sent message to history
            text.setText("");
            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            // If any error occurs during message sending or UI update, print the stack
            // trace for debugging
            e.printStackTrace();
        }
    }

    /**
     * Saves a message to the client's chat history file.
     * 
     * @param message The message to save.
     */
    private static void saveMessageToFile(String message) {
        try (FileWriter fw = new FileWriter("chat_history_client.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
        } catch (IOException e) {
            // If saving to file fails (e.g., disk full, permission denied), print the stack
            // trace
            e.printStackTrace();
        }
    }

    /**
     * Loads chat history from file and displays it in the chat window with proper
     * alignment.
     */
    private static void loadChatHistory() {
        // Loads chat history from file and displays it in the chat window with proper
        // alignment
        File file = new File("chat_history_client.txt");
        if (!file.exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            a1.removeAll();
            a1.setLayout(new BorderLayout());
            while ((line = br.readLine()) != null) {
                JPanel panel = formatLabel(line);
                JPanel wrapper = new JPanel(new BorderLayout());
                wrapper.setOpaque(false);
                if (line.startsWith(username + ": ")) {
                    // Sent message: align right
                    wrapper.add(panel, BorderLayout.LINE_END);
                } else {
                    // Received message: align left
                    wrapper.add(panel, BorderLayout.LINE_START);
                }
                vertical.add(wrapper);
                vertical.add(Box.createVerticalStrut(15));
            }
            a1.add(vertical, BorderLayout.PAGE_START);
            a1.revalidate();
            a1.repaint();
        } catch (IOException e) {
            // If loading chat history fails (e.g., file not found, permission denied),
            // print the stack trace
            e.printStackTrace();
        }
    }

    /**
     * Formats a message for display in the chat window with a colored background
     * and timestamp.
     * 
     * @param out The message to format.
     * @return A JPanel containing the formatted message.
     */
    public static JPanel formatLabel(String out) {
        // Formats a message for display in the chat window with a colored background
        // and timestamp
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width: 150px; margin-left:10px;\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }

    /*
     * Main method to start the client, load chat history, and handle incoming
     * messages.
     */
    public static void main(String[] args) {
        username = JOptionPane.showInputDialog(null, "Enter your username:");
        new client();
        loadChatHistory(); // Load chat history on startup
        Socket s = null;
        try {
            s = new Socket("127.0.0.1", 6001);
            final DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            // Start a thread to receive messages
            Thread receiveThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        while (true) {
                            String encMsg = din.readUTF();
                            String msg = CryptoUtils.decrypt(encMsg);
                            JPanel panel = formatLabel(msg);
                            JPanel wrapper = new JPanel(new BorderLayout());
                            wrapper.setOpaque(false);
                            // Received message: align left
                            wrapper.add(panel, BorderLayout.LINE_START);
                            vertical.add(wrapper);
                            vertical.add(Box.createVerticalStrut(15));
                            a1.add(vertical, BorderLayout.PAGE_START);
                            saveMessageToFile(msg); // Save received message
                            f.validate();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            din.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            receiveThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}