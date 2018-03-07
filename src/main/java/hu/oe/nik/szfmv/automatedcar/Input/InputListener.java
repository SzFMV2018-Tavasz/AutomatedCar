package hu.oe.nik.szfmv.automatedcar.Input;



import hu.oe.nik.szfmv.automatedcar.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.bus.Signal;

import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.*;
import java.util.List;

public class InputListener extends SystemComponent implements KeyListener
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
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            brakekeypressed = true;
            gaskeypressed = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_KP_UP && gaskeypressed)
        {
            gaskeypressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN && brakekeypressed)
        {
            brakekeypressed = false;
        }
    }

    private void Addgas()
    {
        if (gaskeypressed && !brakekeypressed && gascalc.getGaspedalvalue() < gascalc.MaxGaspedalValue)
        {
            gascalc.setGaspedalvalue(2);
        }
    }

    private void Brake()
    {
        if (brakekeypressed && !gaskeypressed && gascalc.getGaspedalvalue() > gascalc.MinGaspedalValue)
        {
            gascalc.setGaspedalvalue(-2);
        }
    }

    @Override
    public void loop()
    {
        Addgas();
        Brake();
    }

    @Override
    public void receiveSignal(Signal s)
    {

    }
}
