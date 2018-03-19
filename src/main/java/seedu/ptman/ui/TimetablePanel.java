package seedu.ptman.ui;

import java.time.Duration;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;
import java.util.ArrayList;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.ptman.commons.core.LogsCenter;
import seedu.ptman.commons.events.model.PartTimeManagerChangedEvent;
import seedu.ptman.model.outlet.Shift;
import seedu.ptman.model.outlet.Timetable;


/**
 * Displays the GUI Timetable.
 */
public class TimetablePanel extends UiPart<Region> {

    private static final String FXML = "TimetableView.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private CalendarView timetableView;

    private ArrayList<Shift> shiftList;
    private Timetable timetable;

    private Calendar timetableAvail;
    private Calendar timetableRunningOut;
    private Calendar timetableFull;

    public TimetablePanel(Timetable timetable) {
        super(FXML);

        this.shiftList = timetable.getShiftsForWeek();
        this.timetable = timetable;

        timetableView = new CalendarView();

        showRelevantViewsOnly();

        updateTimetableView();

        registerAsAnEventHandler(this);
    }

    public CalendarView getRoot() {
        return this.timetableView;
    }

    private void showRelevantViewsOnly() {
        timetableView.showWeekPage();
        timetableView.setShowDeveloperConsole(true); // temp

        timetableView.setShowToday(false);
        timetableView.setShowPrintButton(false);
        timetableView.setShowAddCalendarButton(false);
        timetableView.setShowSearchField(false);
        timetableView.setShowToolBar(false);
        timetableView.setShowPageSwitcher(false);
        timetableView.setShowPageToolBarControls(false);
        timetableView.setShowSearchResultsTray(false);
        timetableView.setShowSourceTray(false);
        timetableView.setShowSourceTrayButton(false);
    }

    private void setTime() {
        timetableView.setToday(LocalDate.now());
        timetableView.setTime(LocalTime.now());
    }

    /**
     * Takes default outlet shifts and set timetable entries based on these shifts
     */
    private void setShifts() {
        for (Shift shift: shiftList) {
            // TODO: Add Time Slot index to each Shift
            LocalDate date = getDateOfShift(shift.getDayOfWeek());
            Interval timeInterval = new Interval(date, shift.getStartTime(), date, shift.getEndTime());
            Entry<String> shiftEntry = new Entry<>("Slots left: " + shift.getSlotsLeft(), timeInterval);
            Calendar entryType = getEntryType(shift);
            entryType.addEntry(shiftEntry);
        }
    }

    /**
     * Converts DayOfWeek into LocalDate for the timetable
     */
    private LocalDate getDateOfShift(DayOfWeek dayOfWeek) {
        LocalDate date = timetable.getMondayDate();
        date.plusDays(dayOfWeek.getValue() - 1);
        return date;
    }

    /**
     * @return the entryType (a Calendar object) for the shift, which reflects
     * the color of the shift in the timetableView.
     */
    private Calendar getEntryType(Shift shift) {
        int ratio = shift.getSlotsLeft() / shift.getCapacity();
        if (ratio > 0.5) {
            return timetableAvail;
        } else if (ratio > 0) {
            return timetableRunningOut;
        } else {
            return timetableFull;
        }
    }

    /**
     * Replaces timetableView with a new timetable with updated shift information
     */
    private void updateTimetableView() {
        setTime();
        timetableView.getCalendarSources().clear();
        CalendarSource calendarSource = new CalendarSource("Shifts");
        addCalendars(calendarSource);

        //timetableAvail.setReadOnly(true);
        //timetableRunningOut.setReadOnly(true);
        //timetableFull.setReadOnly(true);

        setShifts();
        timetableView.getCalendarSources().add(calendarSource);
    }

    /**
     * Adds all relevant Calendars (entryTypes) to its source
     */
    private void addCalendars(CalendarSource calendarSource) {
        // TODO: Improve code quality of this method
        timetableAvail = new Calendar("Available");
        timetableRunningOut = new Calendar("Running Out");
        timetableFull = new Calendar("Full");

        timetableAvail.setStyle(Calendar.Style.STYLE1); // Green
        timetableRunningOut.setStyle(Calendar.Style.STYLE3); // Yellow
        timetableFull.setStyle(Calendar.Style.STYLE5); // Red

        timetableAvail.setLookAheadDuration(Duration.ofDays(7));
        timetableAvail.setLookBackDuration(Duration.ofDays(7));

        calendarSource.getCalendars().addAll(timetableAvail, timetableRunningOut, timetableFull);
    }

    @Subscribe
    private void handleNewShiftEvent(PartTimeManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> updateTimetableView());
    }

}
