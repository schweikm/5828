import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurationPanel extends JPanel {
    ConfigurationPanel() {
        // layout
        this.setLayout(new GridLayout(10, 2));

        addComponentsToPanel();
    }
    
    private void addComponentsToPanel() {
        //
        // URL FIELD
        //
        JLabel urlLabel = new JLabel("Source URL");
        this.add(urlLabel);

        JTextField urlTextField = new JTextField();
        this.add(urlTextField);
        
        //
        // DESTINATION FIELD
        //
        JLabel destinationLabel = new JLabel("Destination");
        this.add(destinationLabel);

        JTextField destinationTextField = new JTextField();
        this.add(destinationTextField);
        
        //
        // NUMBER OF CORES
        //
        JLabel numCoresLabel = new JLabel("Number of CPU Cores");
        this.add(numCoresLabel);

        JTextField numCoresTextField = new JTextField();
        this.add(numCoresTextField);
        
        //
        // NUMBER OF CHUNKS
        //
        JLabel numChunksLabel = new JLabel("Number of Chunks");
        this.add(numChunksLabel);
        
        Integer[] numChunks = {1, 2, 4, 8};
        JComboBox<Integer> numChunksList = new JComboBox<Integer>(numChunks);
        numChunksList.setSelectedIndex(0);
        this.add(numChunksList);
        
        //
        // DOWNLOAD BUTTON
        //
        JLabel filler1 = new JLabel("");
        this.add(filler1);
        
        JButton downloadButton = new JButton("Download");
        
        downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked.");
            }
        });
        
        this.add(downloadButton);
        
        JLabel filler2 = new JLabel("");
        this.add(filler2);
    }

    private static final long serialVersionUID = 1L;
}