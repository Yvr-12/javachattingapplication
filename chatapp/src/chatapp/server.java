package chatapp;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Server class for the chat application.
 * Handles GUI, message sending/receiving, encryption, and chat history.
 */
public class server implements ActionListener {
    // Text field for user input
    JTextField text;
    // Main chat panel (static for access in static methods)
    static JPanel a1;
    // Vertical box to stack chat messages
    static Box vertical = Box.createVerticalBox();
    // Main application window
    static JFrame f = new JFrame();
    // Username of the server user
    static String username;

    // List to keep track of all client output streams for broadcasting
    static CopyOnWriteArrayList<DataOutputStream> clientOutputs = new CopyOnWriteArrayList<>();

    /**
     * Constructor sets up the GUI for the server chat window.
     */
    server() {
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
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/download.png"));// address of image on panel
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
        JLabel name = new JLabel("Sender");
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
                server.this.actionPerformed(new ActionEvent(text, ActionEvent.ACTION_PERFORMED, null));
            }
        });
        f.add(text);

        // Send button
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);// action needed on pressing the button. mian action in action listener method
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(send);

        // Window settings
        f.setSize(450, 700);// size of frame
        f.setLocation(200, 50);// location of frame
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
            // Send message to all clients
            for (DataOutputStream outStream : clientOutputs) {
                try {
                    outStream.writeUTF(CryptoUtils.encrypt(out));
                } catch (IOException e) {
                    // Handle potential IOException when sending to client
                    e.printStackTrace();
                }
            }
            saveMessageToFile(out); // Save sent message to history
            text.setText("");
            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            // Handle any errors that occur during message sending or UI update
            // Print the stack trace for debugging
            e.printStackTrace();
        }
    }

    /**
     * Saves a message to the server's chat history file.
     * 
     * @param message The message to save.
     */
    private static void saveMessageToFile(String message) {
        try (FileWriter fw = new FileWriter("chat_history_server.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
        } catch (IOException e) {
            // Handle file writing errors (e.g., disk full, permission denied)
            // Print the stack trace for debugging
            e.printStackTrace();
        }
    }

    /**
     * Loads chat history from file and displays it in the chat window with proper
     * alignment.
     */
    private static void loadChatHistory() {
        File file = new File("chat_history_server.txt");
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
            // Handle file reading errors (e.g., file not found, permission denied)
            // Print the stack trace for debugging
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
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");// fixing the width of the
                                                                                             // message box using html
                                                                                             // tag. remember = here
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 15));// padding around the message
        panel.add(output);
        Calendar cal = Calendar.getInstance();// time stamp
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    /**
     * Main method to start the server, load chat history, and handle incoming
     * messages.
     */
    public static void main(String[] args) {
        username = JOptionPane.showInputDialog(null, "Enter your username:");
        new server();
        loadChatHistory();
        ServerSocket skt = null;
        try {
            skt = new ServerSocket(6001);
            while (true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                clientOutputs.add(dout);
                // Start a new thread for each client
                Thread t = new Thread(new ClientHandler(s, din, dout));
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (skt != null)
                    skt.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Thread class to handle each client
    static class ClientHandler implements Runnable {
        Socket socket;
        DataInputStream din;
        DataOutputStream dout;

        public ClientHandler(Socket socket, DataInputStream din, DataOutputStream dout) {
            this.socket = socket;
            this.din = din;
            this.dout = dout;
        }

        public void run() {
            try {
                while (true) {
                    String encMsg = din.readUTF();
                    String msg = CryptoUtils.decrypt(encMsg);
                    // Broadcast to all clients
                    for (DataOutputStream out : clientOutputs) {
                        try {
                            out.writeUTF(CryptoUtils.encrypt(msg));
                        } catch (IOException e) {
                            // Remove disconnected clients
                            clientOutputs.remove(out);
                        }
                    }
                    // Update server GUI
                    JPanel panel = formatLabel(msg);
                    JPanel wrapper = new JPanel(new BorderLayout());
                    wrapper.setOpaque(false);
                    wrapper.add(panel, BorderLayout.LINE_START);
                    vertical.add(wrapper);
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical, BorderLayout.PAGE_START);
                    saveMessageToFile(msg);
                    f.validate();
                }
            } catch (Exception e) {
                // Remove this client's output stream on disconnect
                clientOutputs.remove(dout);
                try {
                    if (din != null)
                        din.close();
                } catch (IOException ex) {
                }
                try {
                    if (dout != null)
                        dout.close();
                } catch (IOException ex) {
                }
                try {
                    if (socket != null)
                        socket.close();
                } catch (IOException ex) {
                }
            }
        }
    }
}