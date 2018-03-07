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
    private static int gaspedalvalue;

    public GasBrakePressed()
    {
        gaspedalvalue = 0;
    }

    public static int getGaspedalvalue()
    {
        return gaspedalvalue;
    }

    public static void setGaspedalvalue(int value)
    {
        if (gaspedalvalue + value <= MaxGaspedalValue && gaspedalvalue + value >= MinGaspedalValue)
        {
            gaspedalvalue += value;
        }
    }
}
