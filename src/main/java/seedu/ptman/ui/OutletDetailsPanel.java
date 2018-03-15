package seedu.ptman.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.ptman.commons.core.LogsCenter;
import seedu.ptman.commons.events.ui.OutletInformationChangedEvent;
import seedu.ptman.commons.events.ui.OutletNameChangedEvent;

/**
 * The Outlet Panel of the App, which displays the Outlet name and details
 */
public class OutletDetailsPanel extends UiPart<Region> {

    public static final String OUTLET_NAME_INITIAL = "Outlet Name";
    public static final String OUTLET_INFO_INITIAL =
            "No outlet information recorded. Please add outlet information with the editOutlet command.";

    private static final String FXML = "OutletDetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Label outletNamePanelHeader;

    @FXML
    private Label outletInformation;


    public OutletDetailsPanel() {
        super(FXML);
        outletInformation.setWrapText(true);
        setOutletInformation(OUTLET_INFO_INITIAL);
        setOutletName(OUTLET_NAME_INITIAL);

        registerAsAnEventHandler(this);
    }

    private void setOutletName(String name) {
        outletNamePanelHeader.setText(name);
    }

    private void setOutletInformation(String information) {
        outletInformation.setText(information);
    }

    @Subscribe
    private void handleOutletInformationChangedEvent(OutletInformationChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> setOutletInformation(event.information));
    }

    @Subscribe
    private void handleOutletNameChangedEvent(OutletNameChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> setOutletName(event.message));
    }
}
