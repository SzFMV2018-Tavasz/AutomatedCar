package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;


public class SensorsVisualizer extends SystemComponent {

    private InputHandler inputHandler;
    private InputPacket inputPacket;
    private boolean radarVizualizerPressed;
    private boolean cameraVizualizerPressed;
    private boolean ultrasonicVizualizerPressed;
    private boolean shapeVizualizerPressed;
    private boolean radarPressed;
    private boolean cameraPressed;
    private boolean ultraPressed;
    private boolean shapePressed;

    /**
     * @param virtualFunctionBus VirtualFunctuonBus parameter
     */
    public SensorsVisualizer(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        inputPacket = InputPacket.getInstance();
        virtualFunctionBus.inputPacket = inputPacket;
        inputHandler = InputHandler.getInstance();
        radarVizualizerPressed = false;
        cameraVizualizerPressed = false;
        ultrasonicVizualizerPressed = false;
        shapeVizualizerPressed = false;
    }

    /**
     * if not pressed switch the booleans to false
     */
    private void notPressed() {
        if (!inputHandler.isRadarTestPressed()){
            radarPressed = false;
        }

        if (!inputHandler.isCameraTestPressed()){
            cameraPressed = false;
        }

        if (!inputHandler.isUltrasonicTestPressed()){
            ultraPressed = false;
        }

        if (!inputHandler.isShapeBorderTestPressed()){
            shapePressed = false;
        }
    }

    @Override
    public void loop() {
        if (inputHandler.isRadarTestPressed() && !radarPressed) {
            radarVizualizerPressed = !radarVizualizerPressed;
            inputPacket.setRadarVizualizerState(radarVizualizerPressed);
            radarPressed = true;
        }

        if (inputHandler.isCameraTestPressed() && !cameraPressed) {
            cameraVizualizerPressed = !cameraVizualizerPressed;
            inputPacket.setCameraVizualizerState(cameraVizualizerPressed);
            cameraPressed = true;
        }

        if (inputHandler.isUltrasonicTestPressed() && !ultraPressed) {
            ultrasonicVizualizerPressed = !ultrasonicVizualizerPressed;
            inputPacket.setUltrasonicVizualizerState(ultrasonicVizualizerPressed);
            ultraPressed = true;
        }

        if (inputHandler.isShapeBorderTestPressed() && !shapePressed) {
            shapeVizualizerPressed = !shapeVizualizerPressed;
            inputPacket.setShapeBorderVizualizerState(shapeVizualizerPressed);
            shapePressed = true;
        }

        notPressed();
    }
}

