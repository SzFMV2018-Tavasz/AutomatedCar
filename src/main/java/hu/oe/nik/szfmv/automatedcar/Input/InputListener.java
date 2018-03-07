package hu.oe.nik.szfmv.automatedcar.Input;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;

import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.*;
import java.util.List;

public class InputListener implements KeyListener
{
    private GasBrakePressed gascalc;
    private boolean gaskeypressed;
    private boolean brakekeypressed;

    public InputListener()
    {
       gascalc = new GasBrakePressed();
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_KP_UP)
        {
            gaskeypressed = true;
            brakekeypressed = false;
            gascalc.setGaskeypressed(gaskeypressed);
            gascalc.setBrakekeypressed(brakekeypressed);
            gascalc.Addgas(brakekeypressed, gaskeypressed);
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            brakekeypressed = true;
            gaskeypressed = false;
            gascalc.setGaskeypressed(gaskeypressed);
            gascalc.setBrakekeypressed(brakekeypressed);
            gascalc.Brake(brakekeypressed, gaskeypressed);
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_KP_UP && gaskeypressed)
        {
            gaskeypressed = false;
            gascalc.setGaskeypressed(gaskeypressed);
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN && brakekeypressed)
        {
            brakekeypressed = false;
            gascalc.setBrakekeypressed(brakekeypressed);
        }
    }
}
