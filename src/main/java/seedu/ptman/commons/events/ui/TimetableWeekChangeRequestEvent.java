package seedu.ptman.commons.events.ui;

import seedu.ptman.commons.events.BaseEvent;

/**
 * Indicates a request to change the timetable view to the next or previous week.
 */
public class TimetableWeekChangeRequestEvent extends BaseEvent {
    /**
     * This represents the different
     */
    public enum WeekChangeRequest {
        NEXT,
        PREVIOUS,
        CURRENT
    }

    private WeekChangeRequest request;

    public TimetableWeekChangeRequestEvent(WeekChangeRequest request) {
        this.request = request;
    }

    public WeekChangeRequest getRequest() {
        return request;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
