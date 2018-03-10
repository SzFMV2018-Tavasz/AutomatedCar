package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.automatedcar.bus.packets.input.ReadOnlyInputPacket;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    private final int width = 250;
    private final int height = 700;
    private final int dashboardBoundsX = 770;
    private final int dashboardBoundsY = 0;
    private final int backgroundColor = 0x888888;

    private final JPanel accStatePanel=new JPanel();
    private final int accStatePanelX = 50;
    private final int accStatePanelY = 200;
    private final int accStatePanelWidth = 150;
    private final int accStatePanelHeight = 50;

    private final JLabel accTargetDistanceLabel = new JLabel();
    private final JLabel accTargetSpeedLabel = new JLabel();
    private final JLabel accDistanceLabel = new JLabel();
    private final JLabel accSpeedLabel = new JLabel();

    private final int progressBarsPanelX = 25;
    private final int progressBarsPanelY = 400;
    private final int progressBarsPanelWidth = 200;
    private final int progressBarsPanelHeight = 100;

    private final JPanel progressBarsPanel = new JPanel();

    private final JLabel gasLabel = new JLabel();
    private final JProgressBar gasProgressBar = new JProgressBar();

    private final JLabel breakLabel = new JLabel();
    private final JProgressBar breakProgressBar = new JProgressBar();

    /**
     * Initialize the dashboard
     */
    public Dashboard() {
        initializeDashboard();
    }

    /**
     * Update the displayed values
     * @param inputPacket Contains all the required values coming from input.
     */
    public void updateDisplayedValues(ReadOnlyInputPacket inputPacket) {
        gasProgressBar.setValue(inputPacket.getGasPedalPosition());
        breakProgressBar.setValue(inputPacket.getBreakPedalPosition());

        //test value for display until updateDisplayValues method is implemented
        accDistanceLabel.setText("20");

        //test value for display until updateDisplayValues method is implemented
        accSpeedLabel.setText("20");

        accDistanceLabel.setText(String.valueOf(inputPacket.getACCTargetDistance()));
        accSpeedLabel.setText(String.valueOf(inputPacket.getACCTargetSpeed()));
    }

    /**
     * Initializes the dashboard components
     */
    private void initializeDashboard() {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBackground(new Color(backgroundColor));

        setBounds(dashboardBoundsX, dashboardBoundsY, width, height);

        initializeProgressBars();
        InitializeAccStatePanel();
    }

    /**
     +     * Initializes the ACC-state-panel and the labels to write the values on the dashboard
     */
    private void InitializeAccStatePanel() {
        accStatePanel.setBackground(new Color(backgroundColor));
        accStatePanel.setBounds(
                accStatePanelX,
                accStatePanelY,
                accStatePanelWidth,
                accStatePanelHeight
        );

        accTargetDistanceLabel.setText("Target distance:");
        accTargetSpeedLabel.setText("Target speed:");

        add(accStatePanel);
        accStatePanel.add(accTargetDistanceLabel);
        accStatePanel.add(accDistanceLabel);
        accStatePanel.add(accTargetSpeedLabel);
        accStatePanel.add(accSpeedLabel);
    }

    /**
     * Initializes the progress bars on the dashboard
     */
    private void initializeProgressBars() {
        progressBarsPanel.setBackground(new Color(backgroundColor));
        progressBarsPanel.setBounds(
                progressBarsPanelX,
                progressBarsPanelY,
                progressBarsPanelWidth,
                progressBarsPanelHeight);

        gasLabel.setText("gas pedal");
        breakLabel.setText("break pedal");
        gasProgressBar.setStringPainted(true);
        breakProgressBar.setStringPainted(true);

        add(progressBarsPanel);
        progressBarsPanel.add(gasLabel);
        progressBarsPanel.add(gasProgressBar);
        progressBarsPanel.add(breakLabel);
        progressBarsPanel.add(breakProgressBar);
    }
}
