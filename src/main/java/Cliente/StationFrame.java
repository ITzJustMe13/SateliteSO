/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Diogo
 */
public class StationFrame extends JFrame{
    
    private Station station;
    private JTextArea textArea;
    
    public StationFrame() throws IOException{
        initComponents();
        station = new Station(4001);
        station.doConnections();
        
    }
    
    private void initComponents(){
         //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Station Frame");

        // Create a JTextArea
        textArea = new JTextArea();
        textArea.setEditable(false);

        // Create a JScrollPane to add the JTextArea with scrolling
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Create a panel to hold the text area
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(scrollPane);

        JButton nameButton = new JButton("GetName");
        //nameButton.addActionListener(e -> performRequestName());

        JButton coordsButton = new JButton("GetCoords");
        //nameButton.addActionListener(e -> performRequestCoords());

        JButton speedButton = new JButton("GetSpeed");
        //nameButton.addActionListener(e -> performRequestSpeed());

        JButton tempButton = new JButton("GetTemp");
        //nameButton.addActionListener(e -> performRequestTemp());

        JButton allButton = new JButton("GetAll");
        
        JButton countButton = new JButton("Count");

        allButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRequestAll();
                
            }
        });

        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRequestName();
               
            }
        });

        coordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRequestCoords();
                
            }
        });

        speedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRequestSpeed();
                
            }
        });

        tempButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRequestTemp();
                
            }
        });
        
        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRequestCount();
                
            }
        });
        
        
        // Add the button to the panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(nameButton);
        buttonPanel.add(coordsButton);
        buttonPanel.add(speedButton);
        buttonPanel.add(tempButton);
        buttonPanel.add(countButton);
        buttonPanel.add(allButton);

// Add the button panel to the main panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the panel to the frame
        add(mainPanel);

        // Set the size of the frame
        setSize(600, 500);

        // Center the frame on the screen
        setLocationRelativeTo(null);
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    
    private void performRequestAll(){
        try{
            String response = station.requestAll();
            updateText(response);
        } catch (IOException ex){
            System.out.println(ex);
        }
    }
    private  void performRequestName(){
        try{
            String response = station.requestName();
            updateText(response);
        } catch (IOException ex){
            System.out.println(ex);
        }
    }
    private void performRequestCoords(){
        try{
            String response = station.requestCoords();
            updateText(response);
        } catch (IOException ex){
            System.out.println(ex);
        }
 
    }
    private void performRequestSpeed() {
        try{
            String response = station.requestSpeed();
            updateText(response);
        } catch (IOException ex){
            System.out.println(ex);
        }
    }
    private void performRequestTemp(){
        try{
            String response = station.requestTemperature();
            updateText(response);
        } catch (IOException ex){
            System.out.println(ex);
        }

    }
    
    private void performRequestCount(){
        try{
            String response = station.requestCount();
            updateText(response);
        } catch (IOException ex){
            System.out.println(ex);
        }
    }

    public void updateText(String newText) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(newText + "\n");
        });
    }

    public static void main(String[] args) {
        try {
            StationFrame frame = new StationFrame();
            frame.setVisible(true);
        } catch (IOException ex) {
            System.out.println("Erro ao conectar com o servidor");
        }
    }
    
    
}
