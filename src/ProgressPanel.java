import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class ProgressPanel extends JPanel {
    ProgressPanel() {
        //Create and set up the content pane.
        addComponentToPanel();
    }

    public void addComponentToPanel() {
        //
        // CARD 1 - 1 CHUNK
        //
        JPanel card1 = new JPanel();
        card1.setLayout(new GridLayout(8, 8));

        JProgressBar bar1_1 = new JProgressBar();
        bar1_1.setPreferredSize(new Dimension(600, 40));

        card1.add(bar1_1);


        //
        // CARD 2 - 2 CHUNKS
        //
        JPanel card2 = new JPanel();
        card2.setLayout(new GridLayout(8, 8));

        JProgressBar bar2_1 = new JProgressBar();
        bar2_1.setPreferredSize(new Dimension(600, 40));

        JProgressBar bar2_2 = new JProgressBar();
        bar2_2.setPreferredSize(new Dimension(600, 40));

        card2.add(bar2_1);
        card2.add(bar2_2);


        //
        // CARD 3 - 4 CHUNKS
        //
        JPanel card3 = new JPanel();
        card3.setLayout(new GridLayout(8, 8));

        JProgressBar bar4_1 = new JProgressBar();
        bar4_1.setPreferredSize(new Dimension(600, 40));

        JProgressBar bar4_2 = new JProgressBar();
        bar4_2.setPreferredSize(new Dimension(600, 40));

        JProgressBar bar4_3 = new JProgressBar();
        bar4_3.setPreferredSize(new Dimension(600, 40));

        JProgressBar bar4_4 = new JProgressBar();
        bar4_4.setPreferredSize(new Dimension(600, 40));

        card3.add(bar4_1);
        card3.add(bar4_2);
        card3.add(bar4_3);
        card3.add(bar4_4);


        //
        // CARD 4 - 8 CHUNKS
        //
        JPanel card4 = new JPanel();
        card4.setLayout(new GridLayout(8, 8));

        JProgressBar bar8_1 = new JProgressBar();
        bar8_1.setPreferredSize(new Dimension(600, 40));

        JProgressBar bar8_2 = new JProgressBar();
        bar8_2.setPreferredSize(new Dimension(600, 40));

        JProgressBar bar8_3 = new JProgressBar();
        bar8_3.setPreferredSize(new Dimension(600, 40));

        JProgressBar bar8_4 = new JProgressBar();
        bar8_4.setPreferredSize(new Dimension(600, 40));

        JProgressBar bar8_5 = new JProgressBar();
        bar8_5.setPreferredSize(new Dimension(600, 40));

        JProgressBar bar8_6 = new JProgressBar();
        bar8_6.setPreferredSize(new Dimension(600, 40));

        JProgressBar bar8_7 = new JProgressBar();
        bar8_7.setPreferredSize(new Dimension(600, 40));

        JProgressBar bar8_8 = new JProgressBar();
        bar8_8.setPreferredSize(new Dimension(600, 40));

        card4.add(bar8_1);
        card4.add(bar8_2);
        card4.add(bar8_3);
        card4.add(bar8_4);
        card4.add(bar8_5);
        card4.add(bar8_6);
        card4.add(bar8_7);
        card4.add(bar8_8);


        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, CARD_CHUNK1);
        cards.add(card2, CARD_CHUNK2);
        cards.add(card3, CARD_CHUNK4);
        cards.add(card4, CARD_CHUNK8);

        // and add the container to the panel
        this.add(cards, BorderLayout.CENTER);
    }

    //:MAINTENANCE
    // I realize that the ConfigurationPanel and the ProgressPanel
    // are too tightly coupled but it can be fixed later
    public void setSelectedCard(final int numChunks) {
        CardLayout cl = (CardLayout)(cards.getLayout());

        if(0 == numChunks) {
            cl.first(cards);
        }

        if(1 == numChunks) {
            cl.first(cards);
            cl.next(cards);
        }

        if(2 == numChunks) {
            cl.last(cards);
            cl.previous(cards);
        }

        if(3 == numChunks) {
            cl.last(cards);
        }
    }

    private static final long serialVersionUID = 1L;
    JPanel cards; //a panel that uses CardLayout
    final static String CARD_CHUNK1 = "Download with 1 chunk";
    final static String CARD_CHUNK2 = "Download with 2 chunks";
    final static String CARD_CHUNK4 = "Download with 4 chunks";
    final static String CARD_CHUNK8 = "Download with 8 chunks";
}