package hu.oe.nik.szfmv.automatedcar.Input;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.Timer;


public class GasBrakePressed
{
    public static final int MaxGaspedalValue = 100;
    public static final int MinGaspedalValue = 0;
    private int gaspedalvalue;
    private Timer timer;
    private boolean gaskeypressed;
    private boolean brakekeypressed;


    public GasBrakePressed()
    {
        gaspedalvalue = 0;
        timer = new Timer();
    }

    public int getGaspedalvalue()
    {
        return gaspedalvalue;
    }

    public void setGaskeypressed(boolean gaskeypressed)
    {
        this.gaskeypressed = gaskeypressed;
    }

    public void setBrakekeypressed(boolean brakekeypressed)
    {
        this.brakekeypressed = brakekeypressed;
    }

    public void setGaspedalvalue(int value)
    {
        if (gaspedalvalue + value <= MaxGaspedalValue && gaspedalvalue + value >= MinGaspedalValue)
        {
            gaspedalvalue += value;
        }
    }

    public void Addgas(boolean brakekeypressed, boolean gaskeypressed)
    {
        TimerTask t = new TimerTask()
        {
            @Override
            public void run()
            {
                if (gaskeypressed && !brakekeypressed && getGaspedalvalue() < MaxGaspedalValue)
                {
                    setGaspedalvalue(2);
                }
                else
                {
                    this.cancel();
                }
            }
        };

        timer.scheduleAtFixedRate(t,50,20);
    }

    public void Brake(boolean brakekeypressed, boolean gaskeypressed)
    {
        TimerTask t = new TimerTask()
        {
            @Override
            public void run()
            {
                if (brakekeypressed && !gaskeypressed && getGaspedalvalue() > MinGaspedalValue)
                {
                    setGaspedalvalue(-2);
                }
                else
                {
                    this.cancel();
                }
            }
        };

        timer.scheduleAtFixedRate(t,50,20);
    }
}
