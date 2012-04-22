import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ProgressPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    
    ProgressPanel() {
        JButton button = new JButton("LOL");
        button.addActionListener(this);
        
        //Put everything together.
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.add(button);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("ProgressPanel:  Action Performed!");
    }
}