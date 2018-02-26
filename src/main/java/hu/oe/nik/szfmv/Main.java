package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.common.ConfigProvider;
import hu.oe.nik.szfmv.visualization.Gui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {

//        SwingUtilities.invokeLater(() -> {
//            Gui ex = new Gui();
//            ex.setVisible(true);
//        });
        Gui gui = new Gui();

        // log the current debug mode in config
        LOGGER.info(ConfigProvider.provide().getBoolean("general.debug"));

    }
}
