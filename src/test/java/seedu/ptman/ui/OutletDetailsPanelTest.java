package seedu.ptman.ui;

import static org.junit.Assert.assertEquals;
import static seedu.ptman.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.OutletDetailsPanelHandle;
import seedu.ptman.commons.events.ui.OutletInformationChangedEvent;
import seedu.ptman.commons.events.ui.OutletNameChangedEvent;

//@@author hzxcaryn
public class OutletDetailsPanelTest extends GuiUnitTest {
    private OutletInformationChangedEvent outletInformationChangedEventStub;
    private OutletNameChangedEvent outletNameChangedEventStub;

    private OutletDetailsPanel outletDetailsPanel;
    private OutletDetailsPanelHandle outletDetailsPanelHandle;

    @Before
    public void setUp() {
        outletInformationChangedEventStub = new OutletInformationChangedEvent("New Outlet Information");
        outletNameChangedEventStub = new OutletNameChangedEvent("New Outlet Name");
        outletDetailsPanel = new OutletDetailsPanel();

        uiPartRule.setUiPart(outletDetailsPanel);
        outletDetailsPanelHandle = new OutletDetailsPanelHandle(outletDetailsPanel.getRoot());
    }

    @Test
    public void display() {
        // Default outlet name and information
        String expectedDefaultOutletName = "Outlet Name";
        String expectedDefaultOutletInformation = "No outlet information recorded. "
                + "Please add outlet information with the editOutlet command.";
        assertEquals(expectedDefaultOutletInformation, outletDetailsPanelHandle.getOutletInformation());
        assertEquals(expectedDefaultOutletName, outletDetailsPanelHandle.getOutletName());

        // Outlet Information Updated
        postNow(outletInformationChangedEventStub);
        String expectedOutletInformation = "New Outlet Information";
        assertEquals(expectedOutletInformation, outletDetailsPanelHandle.getOutletInformation());

        // Outlet Name Updated
        postNow(outletNameChangedEventStub);
        String expectedOutletName = "New Outlet Name";
        assertEquals(expectedOutletName, outletDetailsPanelHandle.getOutletName());

    }

}
