package hu.oe.nik.szfmv.visualization;

import javax.swing.JPanel;
import java.awt.Color;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    public Dashboard() {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBackground(new Color(0x888888));
        setBounds(770, 0, 250, 700);

    }

}
