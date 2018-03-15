package seedu.ptman.ui;

//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.util.ArrayList;

//import com.calendarfx.model.Calendar;
//import com.calendarfx.model.CalendarSource;
//import com.calendarfx.model.Entry;
//import com.calendarfx.model.Interval;
//import com.calendarfx.view.CalendarView;

import java.util.logging.Logger;

//import com.google.common.eventbus.Subscribe;

//import javafx.application.Platform;
//import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.ptman.commons.core.LogsCenter;
//import seedu.ptman.commons.events.ui.OutletInformationChangedEvent;


/**
 * Displays the GUI Timetable.
 */
public class TimetableView extends UiPart<Region> {

    private static final String FXML = "TimetableView.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private TimetableView timetableView;

    public TimetableView() {
        super(FXML);

        registerAsAnEventHandler(this);
    }



}
