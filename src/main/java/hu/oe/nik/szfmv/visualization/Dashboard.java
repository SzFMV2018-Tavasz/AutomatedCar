package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.automatedcar.bus.packets.input.ReadOnlyInputPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.powertrain.ReadOnlyPowertrainPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.reverseradar.ReadOnlyReverseRadarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection.ReadOnlyRoadSignDetectionPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;
import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.ReverseRadarState;
import hu.oe.nik.szfmv.environment.models.RoadSign;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Dashboard
     */
    private final int width = 250;
    private final int height = 700;
    private final int dashboardBoundsX = 770;
    private final int dashboardBoundsY = 0;
    private final int backgroundColor = 0x888888;
    private final int keyEventWhen = 20;

    /**
     * ACC
     */
    private final JPanel accStatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final int accStatePanelX = 0;
    private final int accStatePanelY = 180;
    private final int accStatePanelWidth = 130;
    private final int accStatePanelHeight = 100;
    private int accDecreaseCurrentPressAmount = 0;
    private final int accDecreasePressAmount = 3;
    private boolean accDecreasePressed;

    private final JButton accDistanceButtonMinus = new JButton();
    private final JButton accDistanceButtonPlus = new JButton();
    private final JButton accSpeedButtonMinus = new JButton();
    private final JButton accSpeedButtonPlus = new JButton();

    private final int accLabelSize = 30;
    private final JPanel accDistancePanel = new JPanel();
    private final JPanel accSpeedPanel = new JPanel();
    private final JLabel accDistanceLabel = new JLabel();
    private final JLabel accSpeedLabel = new JLabel();

    private final int accButtonX = 35;
    private final int accButtonY = 255;
    private final int accButtonWidth = 60;
    private final int accButtonHeight = 30;
    private JButton accButton = new JButton();
    private boolean accOn = false;

    /**
     * Road sign
     */
    private final int roadSignPanelX = 130;
    private final int roadSignPanelY = 180;
    private final int roadSignPanelWidth = 110;
    private final int roadSignPanelHeight = 130;
    private final JPanel roadSignPanel = new JPanel();
    private final JLabel roadSignIcon = new JLabel();
    private final JLabel roadSignLabel = new JLabel();

    /**
     * Reverse Radar
     */
    private final int reverseRadarPanelX = 55;
    private final int reverseRadarPanelY = 123;
    private final int reverseRadarPanelWidth = 130;
    private final int reverseRadarPanelHeight = 30;

    private final int reverseRadarLabelNameX = 100;
    private final int reverseRadarLabelNameY = 110;
    private final int reverseRadarLabelNameWidth = 50;
    private final int reverseRadarLabelNameHeight = 10;

    private final JPanel reverseRadarPanel = new JPanel();
    private final JLabel reverseRadarLabelStatus = new JLabel();
    private final JLabel revereRadarLabelDistance = new JLabel();

    /**
     * Gear
     */
    private final int gearLabelX = 100;
    private final int gearLabelY = 155;
    private final int gearLabelWidth = 40;
    private final int gearLabelHeight = 20;
    private final JLabel gearLabel = new JLabel();

    private final int gearValueLabelX = 135;
    private final int gearValueLabelY = 155;
    private final int gearValueLabelWidth = 50;
    private final int gearValueLabelHeight = 20;
    private final JLabel gearValueLabel = new JLabel();

    /**
     * Steering wheel
     */
    private final int wheelTextLabelX = 20;
    private final int wheelTextLabelY = 430;
    private final int wheelTextLabelWidth = 100;
    private final int wheelTextLabelHeight = 20;
    private final JLabel wheelTextLabel = new JLabel();
    private final int wheelValueLabelX = 120;
    private final int wheelValueLabelY = 430;
    private final int wheelValueLabelWidth = 50;
    private final int wheelValueLabelHeight = 20;
    private final JLabel wheelValueLabel = new JLabel();

    /**
     * Lane keeping
     */
    private final int lkaButtonX = 5;
    private final int lkaButtonY = 290;
    private final int lkaButtonWidth = 60;
    private final int lkaButtonHeight = 30;
    private JButton lkaButton = new JButton();
    private boolean lkaOn = false;

    /**
     * Parking pilot
     */
    private final int ppButtonX = 65;
    private final int ppButtonY = 290;
    private final int ppButtonWidth = 60;
    private final int ppButtonHeight = 30;
    private JButton ppButton = new JButton();
    private boolean ppOn = false;

    /**
     * Break & gas
     */
    private final int progressBarsPanelX = 25;
    private final int progressBarsPanelY = 330;
    private final int progressBarsPanelWidth = 200;
    private final int progressBarsPanelHeight = 100;
    private final JPanel progressBarsPanel = new JPanel();
    private final JLabel gasLabel = new JLabel();
    private final JProgressBar gasProgressBar = new JProgressBar();
    private final JLabel breakLabel = new JLabel();
    private final JProgressBar breakProgressBar = new JProgressBar();

    /**
     * Speed & RPM
     */
    private final int speedMeterX = 10;
    private final int speedMeterY = 10;
    private final int tachoMeterX = 130;
    private final int tachoMeterY = 10;
    private final int meterHeight = 100;
    private final int meterWidth = 100;
    private int speedAngle;
    private int rpmAngle;

    private final int speedLabelWidth = 60;
    private final int speedLabelHeight = 24;
    private final int speedLabelX = 30;
    private final int speedLabelY = 70;
    private final double mpsToKmhMultiplier = 3.6;

    private final int rpmLabelWidth = 60;
    private final int rpmLabelHeight = 24;
    private final int rpmLabelX = 150;
    private final int rpmLabelY = 70;

    private final JLabel speedLabel = new JLabel();
    private final JLabel rpmLabel = new JLabel();

    /**
     * Turn signal
     */
    private String indexLeftOff = "index_left_off.png";
    private String indexRightOff = "index_right_off.png";
    private String indexLeftOn = "index_left_on.png";
    private String indexRightOn = "index_right_on.png";
    private boolean leftIndexState = false;
    private boolean rightIndexState = false;
    private final int leftIndexX = 0;
    private final int rightIndexX = 190;
    private final int indexY = 120;
    private final int imageH = 50;
    private final int imageW = 50;

    /**
     * Position
     */
    private final int carPositionPanelX = 25;
    private final int carPositionPanelY = 650;
    private final int getCarPositionPanelWidth = 200;
    private final int getCarPositionPanelHeight = 20;
    private final JLabel carPositionXLabel = new JLabel();
    private final JLabel carPositionYLabel = new JLabel();
    private final JPanel carPositionPanel = new JPanel();

    /**
     * Controls
     */
    private final JTextArea controlsText = new JTextArea();
    private final int controlsX = 20;
    private final int controlsY = 450;
    private final int controlsW = 200;
    private final int controlsH = 200;

    /**
     * Initialize the dashboard
     */
    public Dashboard() {
        initializeDashboard();
    }

    /**
     * Update the displayed values
     *
     * @param powertrainPacket   Contains all the required values coming from the powertrain.
     * @param inputPacket        Contains all the required values coming from input.
     * @param roadSignPacket     Contains all the required values related to the last seen road sign.
     * @param reverseRadarPacket Contains all the required values coming from the reverse radar.
     * @param carX               is the X coordinate of the car object
     * @param carY               is the Y coordinate of the car object
     */
    public void updateDisplayedValues(ReadOnlyInputPacket inputPacket,
                                      ReadOnlyPowertrainPacket powertrainPacket,
                                      ReadOnlyRoadSignDetectionPacket roadSignPacket,
                                      ReadOnlyReverseRadarPacket reverseRadarPacket,
                                      int carX, int carY) {
        if (inputPacket != null) {
            updateProgressBars(inputPacket.getGasPedalPosition(), inputPacket.getBreakPedalPosition());
            updateGear(inputPacket.getGearState());
            updateSteeringWheel(inputPacket.getSteeringWheelPosition());
            updateTurnSignals(inputPacket.getLeftTurnSignalStatus(), inputPacket.getRightTurnSignalStatus());
            updateACC(inputPacket.getACCTargetDistance(), inputPacket.getACCTargetSpeed());
            updateParkingPilotIndicator(inputPacket.getParkingPilotStatus());
            updateAccIndicator(inputPacket.getACCOn());
            updateLaneKeepingIndicator(inputPacket.getLaneKeepingStatus());
        }
        if (powertrainPacket != null) {
            updateAnalogMeters((int) powertrainPacket.getSpeed(), powertrainPacket.getRpm());
            repaint();
        }
        if (roadSignPacket != null && roadSignPacket.getRoadSignToShowOnDashboard() != null) {
            displayRoadSign(roadSignPacket.getRoadSignToShowOnDashboard());
        }
        if (reverseRadarPacket != null) {
            displayReverseRadar(reverseRadarPacket.getActivation(), reverseRadarPacket.getReverseRadarState(),
                    reverseRadarPacket.getDistance());
        }

        updateCarPositionLabel(carX, carY);
    }

    private void displayReverseRadar(Boolean isActive, ReverseRadarState state, double distance) {
        if (isActive) {
            reverseRadarLabelStatus.setText("ON");
            DecimalFormat value = new DecimalFormat("#.#");
            revereRadarLabelDistance.setText(String.format("Distance: " + value.format(distance) + " m"));

            switch (state) {
                case OK:
                    reverseRadarLabelStatus.setForeground(Color.GREEN);
                    break;
                case WARNING:
                    reverseRadarLabelStatus.setForeground(Color.YELLOW);
                    break;
                case DANGER:
                    reverseRadarLabelStatus.setForeground(Color.RED);
                    break;
                default:
                    break;
            }
        } else {
            reverseRadarLabelStatus.setText("OFF");
            reverseRadarLabelStatus.setForeground(Color.BLACK);
            revereRadarLabelDistance.setText("");
        }
    }

    private void distanceDecreasePressed() {
        if (accDecreasePressed && accDecreaseCurrentPressAmount < accDecreasePressAmount) {
            if (accDecreaseCurrentPressAmount % 2 == 0) {
                simulateKeyPress(InputHandler.getACCDISTANCEKEYCODE());
            } else {
                simulateKeyRelease(InputHandler.getACCDISTANCEKEYCODE());
            }
            accDecreaseCurrentPressAmount++;
        } else {
            accDecreaseCurrentPressAmount = 0;
            accDecreasePressed = false;
        }
    }

    /**
     * Updates the progress bars related to gas and break pedals based on their current values.
     *
     * @param gasValue   the gas pedal's current state
     * @param breakValue the break pedal's current state
     */
    private void updateProgressBars(int gasValue, int breakValue) {
        gasProgressBar.setValue(gasValue);
        breakProgressBar.setValue(breakValue);
    }

    /**
     * Updates the gear label based on its current state
     *
     * @param state the gear's current state
     */
    private void updateGear(GearEnum state) {
        gearValueLabel.setText("" + state);
    }

    /**
     * Updates the steering wheel value label with the given amount
     *
     * @param position the steering wheel's position
     */
    private void updateSteeringWheel(double position) {
        String labelText = String.valueOf((int) position);
        wheelValueLabel.setText(labelText);
    }

    /**
     * Updates the turn signals with the given values.
     *
     * @param left  the left turn signal's state
     * @param right the right turn signal's state
     */
    private void updateTurnSignals(boolean left, boolean right) {
        leftIndexState = left;
        rightIndexState = right;
    }

    /**
     * Updates the analog meters
     *
     * @param speed the speed the car is moving at
     * @param rpm   the revolutions per minute
     */
    private void updateAnalogMeters(int speed, int rpm) {
        if (speed < 0) {
            speed = 0;
        }

        if (rpm < 0) {
            rpm = 0;
        }

        speedLabel.setText(speed * mpsToKmhMultiplier + " km/h");
        speedAngle = calculateSpeedometer(speed);

        rpmLabel.setText(rpm + " RPM");
        rpmAngle = calculateTachometer(rpm);
    }

    /**
     * Updates the values related to ACC
     *
     * @param distance the distance set for ACC
     * @param speed    the speed set for ACC
     */
    private void updateACC(double distance, int speed) {
        accDistanceLabel.setText(String.valueOf(distance));
        accSpeedLabel.setText(String.valueOf(speed));
    }

    /**
     * Updates the background color of the PP indicator.
     *
     * @param value whether PP is on or off
     */
    private void updateParkingPilotIndicator(boolean value) {
        ppOn = value;
        updateButtonBackground(ppOn, ppButton);
    }

    /**
     * Updates the background color of the ACC indicator.
     *
     * @param value whether ACC is on or off
     */
    private void updateAccIndicator(boolean value) {
        accOn = value;
        updateButtonBackground(accOn, accButton);
    }

    /**
     * Updates the background color of the LK indicator.
     *
     * @param value whether LK is on or off
     */
    private void updateLaneKeepingIndicator(boolean value) {
        lkaOn = value;
        updateButtonBackground(lkaOn, lkaButton);
    }

    /**
     * Initializes the dashboard components
     */
    private void initializeDashboard() {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBackground(new Color(backgroundColor));
        setBounds(dashboardBoundsX, dashboardBoundsY, width, height);
        initializeSpeedRPMLabels();
        initializeRoadSignPanel();
        initializeProgressBars();
        initializeCarPositionLabel();
        initializeGear();
        initializeSteeringWheel();
        initializeLka();
        initializePp();
        initializeAccStatePanel();
        initializeControlsText();
        initializeReverseRadarPanel();
    }

    /**
     * Initializes the text area that displays the controls.
     */
    private void initializeControlsText() {
        controlsText.setText(
                "Gas: UP\n" +
                        "BREAK : DOWN\n" +
                        "Steering: LEFT, RIGHT\n" +
                        "Gear: W/S\n" +
                        "Index: 0,1\n" +
                        "LK: L\n" +
                        "PP: P\n" +
                        "ACC on: 5\n" +
                        "ACC dist.: T\n" +
                        "ACC speed: +/-\n" +
                        "Collision: 6\n" +
                        "Radar, Camera, Ultrasonic: 7,8,9");
        controlsText.setBounds(controlsX, controlsY, controlsW, controlsH);
        controlsText.setFocusable(false);
        this.add(controlsText);
    }

    /**
     * Initializes the speed and RPM labels on the dashboard
     */
    private void initializeSpeedRPMLabels() {
        rpmLabel.setBounds(rpmLabelX, rpmLabelY, rpmLabelWidth, rpmLabelHeight);
        add(rpmLabel);

        speedLabel.setBounds(speedLabelX, speedLabelY, speedLabelWidth, speedLabelHeight);
        add(speedLabel);
    }

    /**
     * Initializes the ACC-state-panel and the labels to write the values on the dashboard
     */
    private void initializeAccStatePanel() {
        accStatePanel.setBackground(new Color(backgroundColor));
        accSpeedPanel.setBackground(new Color(backgroundColor));
        accDistancePanel.setBackground(new Color(backgroundColor));
        accStatePanel.setBounds(accStatePanelX, accStatePanelY, accStatePanelWidth, accStatePanelHeight);
        accSpeedPanel.setPreferredSize(new Dimension(accLabelSize, accLabelSize));
        accDistancePanel.setPreferredSize(new Dimension(accLabelSize, accLabelSize));
        initializeACCButtons();

        addACCElementsToDashboard();
    }

    /**
     * Sets the text and events for the ACC buttons.
     */
    private void initializeACCButtons() {
        accDistanceButtonMinus.setText("-");
        accDistanceButtonMinus.setFocusable(false);
        accSpeedButtonMinus.setText("-");
        accSpeedButtonMinus.setFocusable(false);
        accDistanceButtonPlus.setText("+");
        accDistanceButtonPlus.setFocusable(false);
        accSpeedButtonPlus.setText("+");
        accSpeedButtonPlus.setFocusable(false);

        accButton.addActionListener(e -> simulateKeyPress(InputHandler.getACCONKEYCODE()));
        accButton.setBounds(accButtonX, accButtonY, accButtonWidth, accButtonHeight);
        accButton.setText("ACC");
        accButton.setFocusable(false);
        add(accButton);

        accDistanceButtonMinus.addActionListener(e -> accDecreasePressed = true);
        accDistanceButtonPlus.addActionListener(e -> simulateKeyPress(InputHandler.getACCDISTANCEKEYCODE()));
        accSpeedButtonMinus.addActionListener(e -> simulateKeyPress(InputHandler.getACCSPEEDDECREMENTKEYCODE()));
        accSpeedButtonPlus.addActionListener(e -> simulateKeyPress(InputHandler.getACCSPEEDINCREMENTKEYCODE()));
    }

    /**
     * Handles all the button presses on the dashboard.
     */
    public void handleButtonPresses() {
        simulateKeyRelease(InputHandler.getACCDISTANCEKEYCODE());
        distanceDecreasePressed();
        simulateKeyRelease(InputHandler.getACCONKEYCODE());
        simulateKeyRelease(InputHandler.getACCSPEEDDECREMENTKEYCODE());
        simulateKeyRelease(InputHandler.getACCSPEEDINCREMENTKEYCODE());
        simulateKeyRelease(InputHandler.getLANEKEEPINGKEYCODE());
        simulateKeyRelease(InputHandler.getPARKINGPILOTEKEYCODE());
    }

    /**
     * Simulates keyboard press.
     *
     * @param keyCode the keycode we want to simulate
     */
    private void simulateKeyPress(int keyCode) {
        KeyEvent keyEvent = new KeyEvent(accButton, 1, keyEventWhen, 1, keyCode, ' ');
        InputHandler.getInstance().keyPressed(keyEvent);
    }

    /**
     * Simulates keyboard release.
     *
     * @param keyCode the keycode we want to simulate
     */
    private void simulateKeyRelease(int keyCode) {
        KeyEvent keyEvent = new KeyEvent(accButton, 1, keyEventWhen, 1, keyCode, ' ');
        InputHandler.getInstance().keyReleased(keyEvent);
    }

    /**
     * Puts the ACC elements on the dashboard.
     */
    private void addACCElementsToDashboard() {
        add(accStatePanel);
        accStatePanel.add(accDistanceButtonMinus);
        accStatePanel.add(accDistancePanel);
        accStatePanel.add(accDistanceButtonPlus);
        accStatePanel.add(accSpeedButtonMinus);
        accStatePanel.add(accSpeedPanel);
        accStatePanel.add(accSpeedButtonPlus);
        accDistancePanel.add(accDistanceLabel);
        accSpeedPanel.add(accSpeedLabel);
    }

    /**
     * Initializes the road sign panel on the dashboard
     */
    private void initializeRoadSignPanel() {
        roadSignPanel.setBounds(roadSignPanelX, roadSignPanelY, roadSignPanelWidth, roadSignPanelHeight);
        roadSignPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        roadSignPanel.add(roadSignLabel);
        roadSignPanel.add(roadSignIcon);
        roadSignLabel.setText("last road sign");
        add(roadSignPanel);
    }

    /**
     * Initializes the reverse radar panel on the dashboard
     */
    private void initializeReverseRadarPanel() {
        reverseRadarPanel.setBounds(reverseRadarPanelX, reverseRadarPanelY,
                reverseRadarPanelWidth, reverseRadarPanelHeight);
        reverseRadarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel reverseRadarLabelName = new JLabel();
        reverseRadarLabelName.setText("Radar");
        reverseRadarLabelName.setBounds(reverseRadarLabelNameX, reverseRadarLabelNameY,
                reverseRadarLabelNameWidth, reverseRadarLabelNameHeight);

        revereRadarLabelDistance.setText("");

        reverseRadarPanel.add(reverseRadarLabelStatus);
        reverseRadarPanel.add(revereRadarLabelDistance);
        reverseRadarPanel.setBackground(new Color(backgroundColor));

        add(reverseRadarPanel);
        add(reverseRadarLabelName);
    }

    /**
     * Initializes the Gear sign panel on the dashboard
     */
    private void initializeGear() {
        gearLabel.setBounds(gearLabelX, gearLabelY, gearLabelWidth, gearLabelHeight);
        gearValueLabel.setBounds(gearValueLabelX, gearValueLabelY, gearValueLabelWidth, gearValueLabelHeight);
        gearLabel.setText("Gear:");
        gearValueLabel.setText("" + GearEnum.P);
        add(gearLabel);
        add(gearValueLabel);
    }

    /**
     * Initializes the Steering wheel sign on the dashboard
     */
    private void initializeSteeringWheel() {
        wheelTextLabel.setBounds(
                wheelTextLabelX,
                wheelTextLabelY,
                wheelTextLabelWidth,
                wheelTextLabelHeight);
        wheelValueLabel.setBounds(
                wheelValueLabelX,
                wheelValueLabelY,
                wheelValueLabelWidth,
                wheelValueLabelHeight);
        wheelTextLabel.setText("steering wheel:");
        wheelValueLabel.setText("0");
        add(wheelTextLabel);
        add(wheelValueLabel);
    }

    /**
     * Updates the given button's background color based on the given boolean value
     *
     * @param buttonValue the boolean value represented by the button
     * @param button      the button we are updating
     */
    private void updateButtonBackground(Boolean buttonValue, JButton button) {
        if (buttonValue) {
            button.setBackground(Color.GREEN);
        } else {
            button.setBackground(new JButton().getBackground());
        }
    }

    /**
     * Initializes the lka sign on the dashboard
     */
    private void initializeLka() {
        lkaButton.addActionListener(e -> simulateKeyPress(InputHandler.getLANEKEEPINGKEYCODE()));
        lkaButton.setBounds(lkaButtonX, lkaButtonY, lkaButtonWidth, lkaButtonHeight);
        lkaButton.setText("LKA");
        lkaButton.setFocusable(false);
        add(lkaButton);
    }

    /**
     * Initializes the pp sign on the dashboard
     */
    private void initializePp() {
        ppButton.addActionListener(e -> simulateKeyPress(InputHandler.getPARKINGPILOTEKEYCODE()));
        ppButton.setBounds(ppButtonX, ppButtonY, ppButtonWidth, ppButtonHeight);
        ppButton.setText("PP");
        ppButton.setFocusable(false);
        add(ppButton);
    }

    /**
     * Displays the given road sign in place of the last seen road sign.
     *
     * @param roadSign The last seen road sign
     */
    private void displayRoadSign(RoadSign roadSign) {
        try {
            File roadSignImageFile = new File(Objects.requireNonNull(getClass().getClassLoader().
                    getResource(roadSign.getImageFileName())).getFile());
            BufferedImage roadSignPicture = ImageIO.read(roadSignImageFile);
            roadSignIcon.setIcon(new ImageIcon(roadSignPicture));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * Initializes the car position label on the dashboard
     */
    private void initializeCarPositionLabel() {
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
     *
     * @param x is the X coordinate of the car
     * @param y is the Y coordinate of the car object
     */
    private void updateCarPositionLabel(int x, int y) {
        carPositionXLabel.setText("X:" + x);
        carPositionYLabel.setText("Y:" + y);
    }

    /**
     * Drawing the Speedometer and the Tachometer.
     *
     * @param g {@link Graphics} object that can draw to the canvas
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawOval(speedMeterX, speedMeterY, meterWidth, meterHeight);
        g.drawOval(tachoMeterX, tachoMeterY, meterWidth, meterHeight);
        g.setColor(Color.RED);

        g.fillArc(speedMeterX, speedMeterY, meterWidth, meterHeight, speedAngle, 2);
        g.fillArc(tachoMeterX, tachoMeterY, meterWidth, meterHeight, rpmAngle, 2);

        drawIndexArrows(g);
    }

    /**
     * Drawing the index arrows
     *
     * @param g {@link Graphics} object that can draw to the canvas
     */
    private void drawIndexArrows(Graphics g) {

        if (leftIndexState) {
            indexDrawTry(g, indexLeftOn, leftIndexX);
        } else {
            indexDrawTry(g, indexLeftOff, leftIndexX);
        }
        if (rightIndexState) {
            indexDrawTry(g, indexRightOn, rightIndexX);
        } else {
            indexDrawTry(g, indexRightOff, rightIndexX);
        }
    }

    /**
     * Drawing the index arrows
     *
     * @param g      {@link Graphics} object that can draw to the canvas
     * @param signal {@link String} index image selector
     * @param indexX {@link int} image X position
     */
    private void indexDrawTry(Graphics g, String signal, int indexX) {
        BufferedImage image;

        try {
            image = ImageIO.read(new File(ClassLoader.getSystemResource(signal).getFile()));
            g.drawImage(image, indexX, indexY, imageW, imageH, this);
        } catch (IOException e) {
            LOGGER.info("Error in turn signal draw - " + e.getMessage());
        }
    }

    /**
     * Map the RPM to a displayable value for the gauge.
     *
     * @param rpm The unmapped input value of the Tachometer's visual display.
     * @return The mapped value between [-75, 255] interval.
     */
    private int calculateTachometer(int rpm) {
        final int minRpmValue = 0;
        final int maxRpmValue = 10000;
        final int minRpmMeter = -75;
        final int maxRpmMeter = 255;
        int newrpm = maxRpmValue - rpm;

        return (newrpm - minRpmValue) * (maxRpmMeter - minRpmMeter) / (maxRpmValue - minRpmValue) + minRpmMeter;
    }

    /**
     * Map the Speed to a displayable value for the gauge.
     *
     * @param speed The unmapped input value of the Speedometer's visual display.
     * @return The mapped value between [-75, 255] interval.
     */
    private int calculateSpeedometer(int speed) {
        final int minSpeedValue = 0;
        final int maxSpeedValue = 500;
        final int minSpeedMeter = -75;
        final int maxSpeedMeter = 255;
        int newspeed = maxSpeedValue - speed;

        return (newspeed - minSpeedValue) * (maxSpeedMeter - minSpeedMeter)
                / (maxSpeedValue - minSpeedValue) + minSpeedMeter;
    }
}
