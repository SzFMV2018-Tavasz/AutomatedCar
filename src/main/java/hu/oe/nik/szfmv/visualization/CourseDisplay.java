package hu.oe.nik.szfmv.visualization;

import javax.swing.JPanel;
import java.awt.Color;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {

    public CourseDisplay() {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBounds(0, 0, 720, 700);
        setBackground(new Color(0xEEEEEE));
    }
}
