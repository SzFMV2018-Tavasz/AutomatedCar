package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.visualization.Dashboard;

public class DashboardDisplaySystem extends SystemComponent {

    private Dashboard dashboard;

    protected DashboardDisplaySystem(VirtualFunctionBus virtualFunctionBus, Dashboard dashboard) {
        super(virtualFunctionBus);
        this.dashboard = dashboard;
    }

    @Override
    public void loop() {
        dashboard.UpdateDisplayedValues();
    }
}
