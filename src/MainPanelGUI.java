import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class MainPanelGUI {
    public static void main(String [] args) {    
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainPanelGUI().createAndShowGUI();
            }
        });

        /*
        ParrallelDownloader p = new ParrallelDownloader();
        String url = "http://87.media.v4.skyrock.net/music/87c/c67/87cc6741833cac7eaeda396895b355fc.mp3";
        String outputFile = "somebodyThatIUsedToKnow.mp3";
        int fileSize = 4903752;

        try {
            p.download(url, outputFile, fileSize);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
*/
    }

    public MainPanelGUI() {
        progressPanel = new ProgressPanel();
        configPanel = new ConfigurationPanel(progressPanel);

        //Create a JPanel, and add the ConversionPanels to it.
        mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        mainPane.setOpaque(true);
        mainPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        mainPane.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPane.add(configPanel);
        mainPane.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPane.add(progressPanel);
        mainPane.add(Box.createGlue());
    }

    private void initLookAndFeel() {
        String lookAndFeel = null;

        if (LOOKANDFEEL != null) {
            if (LOOKANDFEEL.equals("Metal")) {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("System")) {
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("Motif")) {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            } else if (LOOKANDFEEL.equals("GTK+")) { //new in 1.4.2
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            } else {
                System.err.println("Unexpected value of LOOKANDFEEL specified: "
                                   + LOOKANDFEEL);
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }

            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:"
                                   + lookAndFeel);
                System.err.println("Did you include the L&F library in the class path?");
                System.err.println("Using the default look and feel.");
            } catch (UnsupportedLookAndFeelException e) {
                System.err.println("Can't use the specified look and feel ("
                                   + lookAndFeel
                                   + ") on this platform.");
                System.err.println("Using the default look and feel.");
            } catch (Exception e) {
                System.err.println("Couldn't get specified look and feel ("
                                   + lookAndFeel
                                   + "), for some reason.");
                System.err.println("Using the default look and feel.");
                e.printStackTrace();
            }
        }
    }

    private void createAndShowGUI() {
        //Set the look and feel.
        initLookAndFeel();

        //Create and set up the window.
        JFrame frame = new JFrame("ParallelFileDownloader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 700));

        //Create and set up the content pane.
        MainPanelGUI converter = new MainPanelGUI();
        converter.mainPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(converter.mainPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // Swing components
    private ConfigurationPanel configPanel;
    private ProgressPanel progressPanel;
    private JPanel mainPane;

    //Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    private final static String LOOKANDFEEL = "Metal";
}