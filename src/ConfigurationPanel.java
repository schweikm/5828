import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ConfigurationPanel extends JPanel implements ActionListener {


    //////////////////////
    // PUBLIC INTERFACE //
    //////////////////////


    public ConfigurationPanel() {
        this.setLayout(new GridLayout(10, 2));
        addComponentsToPanel();
    }

    //:MAINTENANCE
    // this action listener is pretty weak but it is good enough for now
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton) {
            // let's make sure we have all of the input we need
            if(myURLTextField.getText().equals("")) {
                myStatusTextField.setText("\"Source URL\" is blank!");
            }
            else if(myDestinationTextField.getText().equals("")) {
                myStatusTextField.setText("\"Destination\" is blank!");
            }
            else {
                // disable changing the number of chunks while downloading
                myChunkComboBox.setEnabled(false);
                myStatusTextField.setText("Download in progress ...");

                // start the download in a new thread to free the GUI
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            for(int i = 10; i <= 100; i += 10) {
                                ProgressPanel.getInstance().updateProgress(0, i);
                                Thread.sleep(100);
                                ProgressPanel.getInstance().updateProgress(1, i);
                                Thread.sleep(150);
                                ProgressPanel.getInstance().updateProgress(2, i);
                                Thread.sleep(200);
                                ProgressPanel.getInstance().updateProgress(3, i);
                                Thread.sleep(100);
                                ProgressPanel.getInstance().updateProgress(4, i);
                                Thread.sleep(300);
                                ProgressPanel.getInstance().updateProgress(5, i);
                                Thread.sleep(100);
                                ProgressPanel.getInstance().updateProgress(6, i);
                                Thread.sleep(250);
                                ProgressPanel.getInstance().updateProgress(7, i);
                            }
                        }
                        catch(Exception ex) {
                            System.err.print("\n\nDownload - Caught Exception:  " + ex.getMessage() + "\n\n");
                            ex.printStackTrace();
                        }
                        
                        // Update the status box
                        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                myStatusTextField.setText("Download complete!");
                            }
                        });
                        
                        // re-enable the combo box
                        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                myChunkComboBox.setEnabled(true);
                            }
                        });
                    }
                }).start();
            }
        }
        else if(e.getSource() instanceof JComboBox) {
            ProgressPanel.getInstance().setSelectedCard(myChunkComboBox.getSelectedIndex());
        }
        else {
            System.err.println("ConfigurationPanel:  I don't know what action just happened!");
        }
    }


    /////////////////////////
    // PROTECTED INTERFACE //
    /////////////////////////


    ///////////////////////
    // PRIVATE INTERFACE //
    ///////////////////////


    private void addComponentsToPanel() {
        //
        // URL FIELD
        //
        final JLabel urlLabel = new JLabel("Source URL");
        this.add(urlLabel);
        this.add(myURLTextField);


        //
        // DESTINATION FIELD
        //
        final JLabel destinationLabel = new JLabel("Destination");
        this.add(destinationLabel);
        this.add(myDestinationTextField);


        //
        // NUMBER OF CORES
        //
        final JLabel numCoresLabel = new JLabel("Number of CPU Cores");
        this.add(numCoresLabel);

        final int numCores = Runtime.getRuntime().availableProcessors();
        final JTextField numCoresTextField = new JTextField(((Integer)numCores).toString());
        numCoresTextField.setEditable(false);
        this.add(numCoresTextField);


        //
        // NUMBER OF CHUNKS
        //
        final JLabel numChunksLabel = new JLabel("Number of Chunks");
        this.add(numChunksLabel);

        myChunkComboBox.addItem(1);
        myChunkComboBox.addItem(2);
        myChunkComboBox.addItem(4);
        myChunkComboBox.addItem(8);
        myChunkComboBox.setSelectedIndex(0);
        myChunkComboBox.addActionListener(this);
        this.add(myChunkComboBox);

        //
        // DOWNLOAD BUTTON
        //
        final JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(this);
        this.add(downloadButton);


        //
        // STATUS FIELD
        //
        myStatusTextField.setText("System Ready");
        myStatusTextField.setEditable(false);
        myStatusTextField.setHorizontalAlignment(JTextField.CENTER);
        this.add(myStatusTextField);


        // this makes the widgets arrange correctly
        JLabel filler = new JLabel("");
        this.add(filler);
    }


    /////////////////////
    // PRIVATE MEMBERS //
    /////////////////////


    // need these components for the action listener
    private final JTextField myURLTextField = new JTextField();
    private final JTextField myDestinationTextField = new JTextField();
    private final JComboBox<Integer> myChunkComboBox = new JComboBox<Integer>();
    private final JTextField myStatusTextField = new JTextField();

    private static final long serialVersionUID = 1L;
}
