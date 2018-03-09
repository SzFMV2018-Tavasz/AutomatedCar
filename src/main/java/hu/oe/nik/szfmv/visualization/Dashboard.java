package hu.oe.nik.szfmv.visualization;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    private final int width = 250;
    private final int height = 700;
    private final int backgroundColor = 0x888888;

    private final JPanel accStatePanel=new JPanel();
    private final Color accStatePanelBackgroundColor=Color.white;
    private final int accStatePanelX=25;
    private final int accStatePanelY=400;
    private final int accStatePanelWidth=150;
    private final int accStatePanelHeight=50;

    private final JLabel accTargetDistanceLabel = new JLabel();
    private final JLabel accTargetSpeedLabel = new JLabel();
    private final JLabel accDistanceLabel = new JLabel();
    private final JLabel accSpeedLabel = new JLabel();


    /**
     * Initialize the dashboard
     */
    public Dashboard() {
        InitializeDashboard();
    }

    /**
     * Update the displayed values
     */
    public void UpdateDisplayedValues() {

    }

    private void InitializeDashboard() {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBackground(new Color(backgroundColor));
        setBounds(770, 0, width, height);

        InitializeAccStatePanel();
    }

    /**
     +     * Initializes the ACC-state-panel and the labels to write the values on the dashboard
     +
     */
    private void InitializeAccStatePanel() {
        accStatePanel.setBackground(accStatePanelBackgroundColor);
        accStatePanel.setBounds(
                accStatePanelX,
                accStatePanelY,
                accStatePanelWidth,
                accStatePanelHeight
        );

        accTargetDistanceLabel.setText("Target distance:");
        accDistanceLabel.setText("20");
        accTargetSpeedLabel.setText("Target speed:");
        accSpeedLabel.setText("20");

        add(accStatePanel);
        accStatePanel.add(accTargetDistanceLabel);
        accStatePanel.add(accDistanceLabel);
        accStatePanel.add(accTargetSpeedLabel);
        accStatePanel.add(accSpeedLabel);
    }
}
