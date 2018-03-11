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
     * Car Position Panel
     */
    private final int carPositionPanelX = 25;
    private final int carPositionPanelY = 500;
    private final  int getCarPositionPanelWidth = 200;
    private final  int getCarPositionPanelHeight = 200;

    private final JLabel carPositionXLabel = new JLabel();
    private final JLabel carPositionYLabel = new JLabel();

    private final JPanel carPositionPanel = new JPanel();


    /**
     * Initialize the dashboard
     */
    public Dashboard() {
        initializeDashboard();
    }

    /**
     * Update the displayed values
     * @param inputPacket Contains all the required values coming from input.
     * @param carX is the X coordinate of the car object
     * @param carY is the Y coordinate of the car object
     */
    public void updateDisplayedValues(ReadOnlyInputPacket inputPacket, int carX, int carY) {
        gasProgressBar.setValue(inputPacket.getGasPedalPosition());
        breakProgressBar.setValue(inputPacket.getBreakPedalPosition());
        updateCarPositionLabel(carX, carY);
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
        initCarPositionLabel();
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

    /**
    *  Initializes the car position label on the dashboard
    */
    private void initCarPositionLabel() {
        carPositionPanel.setBounds(carPositionPanelX, carPositionPanelY,
                getCarPositionPanelWidth, getCarPositionPanelHeight);
        carPositionPanel.setBackground(new Color(backgroundColor));

        carPositionXLabel.setText("X:");
        carPositionYLabel.setText("Y:");
        carPositionPanel.setLayout(new GridLayout(1, 2));

        carPositionPanel.add(carPositionXLabel);
        carPositionPanel.add(carPositionYLabel);
        add(carPositionPanel);
    }

    /**
     * Update the coordinate labels in the position panel
     * @param x is the X coordinate of the car
     * @param y is the Y coordinate of the car object
     */
    private void updateCarPositionLabel(int x, int y) {
        carPositionXLabel.setText("X:" + x);
        carPositionYLabel.setText("Y:" + y);
    }
}
