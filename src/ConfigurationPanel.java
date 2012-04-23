import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.lang.InterruptedException;

import java.util.ArrayList;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ConfigurationPanel extends JPanel implements ActionListener {


    //////////////////////
    // PUBLIC INTERFACE //
    //////////////////////


    public static ConfigurationPanel getInstance() {
        return instance;
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
                // disallow changes while downloading
                myChunkComboBox.setEnabled(false);
                myDownloadButton.setEnabled(false);
                myStatusTextField.setText("Download in progress ...");

                // start the download in a new thread to free the GUI
                new Thread(new Runnable() {
                    public void run() {
                        // This is where the real download code will go
                        test_asyncProgressBar();
                        ConfigurationPanel.getInstance().enableButtons();
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

    public void enableButtons() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                myChunkComboBox.setEnabled(true);
                myStatusTextField.setText("Download complete!");
                myDownloadButton.setEnabled(true);
            }
        });
    }


    /////////////////////////
    // PROTECTED INTERFACE //
    /////////////////////////


    ///////////////////////
    // PRIVATE INTERFACE //
    ///////////////////////


    private ConfigurationPanel() {
        this.setLayout(new GridLayout(10, 2));
        addComponentsToPanel();
    }

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
        myDownloadButton.addActionListener(this);
        this.add(myDownloadButton);


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
    
    private void test_asyncProgressBar() {
        try {
            final ArrayList<Callable<Integer>> taskList = new ArrayList<Callable<Integer>>();

            for(int i = 0; i < 8; i++) {
                final int index = i;
                taskList.add(new Callable<Integer>() {
                    public Integer call() {
                        try {
                            for(int i = 10; i <= 100; i += 10) {
                                ProgressPanel.getInstance().updateProgress(index, i);
                                Thread.sleep((index + 1) * 100);
                            }
                        }
                        catch(InterruptedException iex) {
                            System.err.println("Thread:  " + Thread.currentThread().getName() +
                                               "caught exception:  " + iex.getMessage());
                        }
                        return 0;
                    }
                });
            }

            final ExecutorService service = Executors.newFixedThreadPool(8);
            service.invokeAll(taskList);
            service.shutdown();
        }
        catch(Exception ex) {
            System.err.print("\n\nDownload - Caught Exception:  " + ex.getMessage() + "\n\n");
            ex.printStackTrace();
        }
    }


    /////////////////////
    // PRIVATE MEMBERS //
    /////////////////////


    // Singleton instance
    private static final ConfigurationPanel instance = new ConfigurationPanel();

    // need these components for the action listener
    private final JTextField myURLTextField = new JTextField();
    private final JTextField myDestinationTextField = new JTextField();
    private final JComboBox<Integer> myChunkComboBox = new JComboBox<Integer>();
    private final JTextField myStatusTextField = new JTextField();
    private final JButton myDownloadButton = new JButton("Download");

    private static final long serialVersionUID = 1L;
}
