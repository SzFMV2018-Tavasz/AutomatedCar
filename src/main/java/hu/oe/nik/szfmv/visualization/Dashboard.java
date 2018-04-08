package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;
import hu.oe.nik.szfmv.environment.models.RoadSign;
import javax.imageio.ImageIO;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.ReadOnlyInputPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger();

    private final int width = 250;
    private final int height = 700;
    private final int dashboardBoundsX = 770;
    private final int dashboardBoundsY = 0;
    private final int backgroundColor = 0x888888;

    private final JPanel accStatePanel = new JPanel();
    private final int accStatePanelX = -15;
    private final int accStatePanelY = 300;
    private final int accStatePanelWidth = 150;
    private final int accStatePanelHeight = 50;

    private final JLabel accTargetDistanceLabel = new JLabel();
    private final JLabel accTargetSpeedLabel = new JLabel();
    private final JLabel accDistanceLabel = new JLabel();
    private final JLabel accSpeedLabel = new JLabel();

    private final int roadSignPanelX = 120;
    private final int roadSignPanelY = 280;
    private final int roadSignPanelWidth = 115;
    private final int roadSignPanelHeight = 115;
    private final JPanel roadSignPanel = new JPanel();
    private final JLabel roadSignIcon = new JLabel();
    private final JLabel roadSignLabel = new JLabel();

    private final int gearSignLabelX = 100;
    private final int gearSignLabelY = 160;
    private final int gearSignLabelWidth = 40;
    private final int gearSignLabelHeight = 20;
    private final JLabel gearSignLabel = new JLabel();
    private final int gearLabelX = 135;
    private final int gearLabelY = 160;
    private final int gearLabelWidth = 50;
    private final int gearLabelHeight = 20;
    private final JLabel gearLabel = new JLabel();

    private final int wheelSignLabelX = 10;
    private final int wheelSignLabelY = 500;
    private final int wheelSignLabelWidth = 100;
    private final int wheelSignLabelHeight = 20;
    private final JLabel wheelSignLabel = new JLabel();
    private final int wheelLabelX = 110;
    private final int wheelLabelY = 500;
    private final int wheelLabelWidth = 50;
    private final int wheelLabelHeight = 20;
    private final JLabel wheelLabel = new JLabel();

    private final int progressBarsPanelX = 25;
    private final int progressBarsPanelY = 400;
    private final int progressBarsPanelWidth = 200;
    private final int progressBarsPanelHeight = 100;
    private final JPanel progressBarsPanel = new JPanel();
    private final JLabel gasLabel = new JLabel();
    private final JProgressBar gasProgressBar = new JProgressBar();
    private final JLabel breakLabel = new JLabel();
    private final JProgressBar breakProgressBar = new JProgressBar();

    private final int speedMeterX = 10;
    private final int speedMeterY = 50;
    private final int tachoMeterX = 130;
    private final int tachoMeterY = 50;
    private final int meterHeight = 100;
    private final int meterWidth = 100;

    private int speedAngle;
    private int rpmAngle;

    private String indexleftoff = "index_left_off.png";
    private String indexrightoff = "index_right_off.png";
    private String indexlefton = "index_left_on.png";
    private String indexrighton = "index_right_on.png";
    boolean leftIndexState = false;
    boolean rightIndexState = false;
    private final int leftIndexX = 10;
    private final int rightIndexX = 185;
    private final int indexY = 160;
    private  final int imageH = 50;
    private  final int imageW = 50;

    /**
     * Instructions
     */
    private  final JLabel brakeLabel=new JLabel();
    private  final int brakeLabelX=10;
    private  final int brakeLabelY=520;
    private  final int brakeLabelWidth=100;
    private  final int brakeLabelHeight=20;

    private  final JLabel acceleratorLabel=new JLabel();
    private  final int acceleratorLabelX=0;
    private  final int acceleratorLabelY=0;
    private  final int acceleratorLabelWidth=0;
    private  final int acceleratorLabelHeight=0;

    private  final JLabel leftLabel=new JLabel();
    private  final int leftLabelX=0;
    private  final int leftLabelY=0;
    private  final int leftLabelWidth=0;
    private  final int leftLabelHeight=0;

    private  final JLabel rightLabel=new JLabel();
    private  final int rightLabelX=0;
    private  final int rightLabelY=0;
    private  final int rightLabelWidth=0;
    private  final int rightLabelHeight=0;

    private  final JLabel indexLeftLabel=new JLabel();
    private  final int indexLeftLabelX=0;
    private  final int indexLeftLabelY=0;
    private  final int indexLeftLabelWidth=0;
    private  final int indexLeftLabelHeight=0;

    private  final JLabel indexRightLabel=new JLabel();
    private  final int indexRightLabelX=0;
    private  final int indexRightLabelY=0;
    private  final int indexRightLabelWidth=0;
    private  final int indexRightLabelHeight=0;

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
        gearLabel.setText("" + inputPacket.getGearState());
        wheelLabel.setText("" + inputPacket.getSteeringWheelPosition());

        speedAngle = calculateSpeedometer(0);
        rpmAngle = calculateTachometer(0);

        leftIndexState = inputPacket.getLeftTurnSignalStatus();
        rightIndexState = inputPacket.getRightTurnSignalStatus();
        repaint();

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

        initializeRoadSignPanel();
        initializeProgressBars();

        initializeGear();
        initializeSteeringWheel();
        //test value for display until updateDisplayValues method is implemented
        accDistanceLabel.setText("20");

        //test value for display until updateDisplayValues method is implemented
        accSpeedLabel.setText("20");
        initializeAccStatePanel();
    }

    /**
     +     * Initializes the ACC-state-panel and the labels to write the values on the dashboard
     */
    private void initializeAccStatePanel() {
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
     * Initializes the Gear sign panel on the dashboard
     */
    private void initializeGear() {
        gearSignLabel.setBounds(gearSignLabelX, gearSignLabelY, gearSignLabelWidth, gearSignLabelHeight);
        gearLabel.setBounds(gearLabelX, gearLabelY, gearLabelWidth, gearLabelHeight);
        gearSignLabel.setText("Gear:");
        gearLabel.setText("" + GearEnum.P);
        add(gearSignLabel);
        add(gearLabel);
    }

    /**
     *
     */
    private void initializeInstructionsLabels()
    {
        brakeLabel.setBounds(brakeLabelX,brakeLabelY,brakeLabelWidth,brakeLabelHeight);
        brakeLabel.setText("");

        acceleratorLabel.setBounds(acceleratorLabelX,acceleratorLabelY,acceleratorLabelWidth,acceleratorLabelHeight);
        acceleratorLabel.setText("");

        leftLabel.setBounds(leftIndexX,leftLabelY,leftLabelWidth,leftLabelHeight);
        leftLabel.setText("");

        rightLabel.setBounds(rightIndexX,rightLabelY,rightLabelWidth,rightLabelHeight);
        rightLabel.setText("");

        indexLeftLabel.setBounds(indexLeftLabelX,indexLeftLabelY,indexLeftLabelWidth,indexLeftLabelHeight);
        indexLeftLabel.setText("");

        indexRightLabel.setBounds(indexRightLabelX,indexLeftLabelY,indexLeftLabelWidth,indexLeftLabelHeight);
        indexRightLabel.setText("");

        add(breakLabel);
        add(acceleratorLabel);
        add(leftLabel);
        add(rightLabel);
        add(indexLeftLabel);
        add(indexRightLabel);
    }

    /**
     * Initializes the Steering wheel sign on the dashboard
     */
    private void initializeSteeringWheel() {
        wheelSignLabel.setBounds(wheelSignLabelX, wheelSignLabelY, wheelSignLabelWidth, wheelSignLabelHeight);
        wheelLabel.setBounds(wheelLabelX, wheelLabelY, wheelLabelWidth, wheelLabelHeight);
        wheelSignLabel.setText("steereng wheel:");
        wheelLabel.setText("0");
        add(wheelSignLabel);
        add(wheelLabel);
    }

    /**
     * Displays the given road sign in place of the last seen road sign.
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
            indexDrawTry(g, indexlefton, leftIndexX);
        } else {
            indexDrawTry(g, indexleftoff, leftIndexX);
        }
        if (rightIndexState) {
            indexDrawTry(g, indexrighton, rightIndexX);
        } else {
            indexDrawTry(g, indexrightoff, rightIndexX);
        }
    }

    /**
     * Drawing the index arrows
     *
     * @param g {@link Graphics} object that can draw to the canvas
     * @param signal {@link String} index image selector
     * @param indexX {@link int} image X position
     */
    private void indexDrawTry(Graphics g, String signal, int indexX)
    {
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
     * @param rpm   The unmapped input value of the Tachometer's visual display.
     *
     * @return      The mapped value between [-75, 255] interval.
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
     * @param speed     The unmapped input value of the Speedometer's visual display.
     *
     * @return          The mapped value between [-75, 255] interval.
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
