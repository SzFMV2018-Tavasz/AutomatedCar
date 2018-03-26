package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import com.sun.media.jfxmedia.logging.Logger;
import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class SensorsVisualizer extends SystemComponent {

    private InputHandler inputHandler;
    private InputPacket inputPacket;
    private boolean radarVizualizerPressed;
    private boolean cameraVizualizerPressed;
    private boolean ultrasonicVizualizerPressed;

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
    }

    @Override
    public void loop() {
        if (inputHandler.isRadarTestPressed()) {
            inputPacket.setRadarVizualizerState(state((radarVizualizerPressed)));
        }
        if (inputHandler.isCameraTestPressed()) {
            inputPacket.setCameraVizualizerState(state(cameraVizualizerPressed));
        }
        if (inputHandler.isUltrasonicTestPressed()) {
            inputPacket.setUltrasonicVizualizerState(state(ultrasonicVizualizerPressed));
        }
    }

    private boolean state(boolean pressed) {
        boolean state = false;
        if (!pressed) {
            state = true;
        } else {
            state = false;
        }
        return state;
    }


}

