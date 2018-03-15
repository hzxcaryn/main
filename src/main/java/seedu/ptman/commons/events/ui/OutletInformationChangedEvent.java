package seedu.ptman.commons.events.ui;

import seedu.ptman.commons.events.BaseEvent;

/**
 * Represents an information change in the Outlet Information
 */
public class OutletInformationChangedEvent extends BaseEvent {

    public final String information;

    public OutletInformationChangedEvent(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
