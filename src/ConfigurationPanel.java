import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ConfigurationPanel extends JPanel implements ActionListener {
    ConfigurationPanel(ProgressPanel progressPanel) {
        myProgressPanel = progressPanel;

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

        final int numCores = Runtime.getRuntime().availableProcessors();
        JTextField numCoresTextField = new JTextField(((Integer)numCores).toString());
        numCoresTextField.setEditable(false);
        this.add(numCoresTextField);


        //
        // NUMBER OF CHUNKS
        //
        JLabel numChunksLabel = new JLabel("Number of Chunks");
        this.add(numChunksLabel);

        Integer[] numChunks = {1, 2, 4, 8};
        JComboBox<Integer> numChunksList = new JComboBox<Integer>(numChunks);
        numChunksList.setSelectedIndex(0);
        numChunksList.addActionListener(this);
        this.add(numChunksList);

        //
        // DOWNLOAD BUTTON
        //
        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(this);
        this.add(downloadButton);


        //
        // STATUS FIELD
        //
        JTextField statusTextField = new JTextField("System Ready");
        statusTextField.setEditable(false);
        this.add(statusTextField);


        // this makes the widgets arrange correctly
        JLabel filler = new JLabel("");
        this.add(filler);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton) {
            System.out.println("Download button clicked");
        }

        if(e.getSource() instanceof JComboBox) {
            //:MAINTENANCE
            // Apparently there isn't a good way to "fix" this warning
            // so we can suppress it because we know it is safe
            @SuppressWarnings("unchecked")

            JComboBox<Integer> box = (JComboBox<Integer>)e.getSource();
            myProgressPanel.setSelectedCard(box.getSelectedIndex());
        }
    }

    private ProgressPanel myProgressPanel;
    private static final long serialVersionUID = 1L;
}