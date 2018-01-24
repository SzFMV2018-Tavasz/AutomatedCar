package hu.oe.nik.szfmv.visualization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;


public class Gui extends JFrame {

    private final SimpleAttributeSet redStyle = new SimpleAttributeSet();
    private final SimpleAttributeSet whiteStyle = new SimpleAttributeSet();
    private static final Logger LOGGER = LogManager.getLogger();

    public Gui() {
        StyleConstants.setForeground(redStyle, new Color(0xFF6262));
        StyleConstants.setForeground(whiteStyle, new Color(0xEDEDED));

        init();
    }

    private void init() {
        setTitle("AutomatedCar");
        setLocation(0, 0); // default is 0,0 (top left corner)
        addWindowListener(new GuiAdapter());
        setPreferredSize(new Dimension(1020,700)); // inner size is 1020,700
        setResizable(false);
        pack();

        // Icon downloaded from: http://www.iconarchive.com/show/toolbar-2-icons-by-shlyapnikova/car-icon.html
        // and available under the licence of: https://creativecommons.org/licenses/by/4.0/
        ImageIcon carIcon = new ImageIcon(ClassLoader.getSystemResource("car-icon.png"));
        setIconImage(carIcon.getImage());

        // Not using any layout manager, but fixed coordinates
        setLayout(null);

        add(new CourseDisplay());

        Dashboard dashboard = new Dashboard();
        add(dashboard);

        JTextPane logViewer = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(logViewer);
        logViewer.setEditable(false);
        logViewer.setBackground(new Color(0x44444444));


        scrollPane.setBounds(0, 425, 250, 275);
        scrollPane.setWheelScrollingEnabled(true);
        dashboard.add(scrollPane);
        StyledDocument doc = logViewer.getStyledDocument();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        try {
            for (int i=0;i<20;i++)
            doc.insertString(doc.getLength(), "message" + "\n" + String.valueOf(i), whiteStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        setVisible(true);

    }

//    public void addToLogViewer(String message) {
//        try {
//            doc.insertString(doc.getLength(), message + "\n", redStyle);
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        }
//    }
}
