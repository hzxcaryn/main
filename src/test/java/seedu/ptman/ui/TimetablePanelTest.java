package seedu.ptman.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.ptman.commons.util.DateUtil.getNextWeekDate;
import static seedu.ptman.commons.util.DateUtil.getPrevWeekDate;
import static seedu.ptman.testutil.EventsUtil.postNow;
import static seedu.ptman.testutil.TypicalEmployees.ALICE;
import static seedu.ptman.testutil.TypicalShifts.MONDAY_AM;
import static seedu.ptman.testutil.TypicalShifts.getTypicalPartTimeManagerWithShiftsWithoutSunday;
import static seedu.ptman.ui.TimetablePanel.TIMETABLE_IMAGE_FILE_FORMAT;
import static seedu.ptman.ui.TimetablePanel.getTimetableAvail;
import static seedu.ptman.ui.TimetablePanel.getTimetableEmployee;
import static seedu.ptman.ui.TimetablePanel.getTimetableFull;
import static seedu.ptman.ui.TimetablePanel.getTimetableOthers;
import static seedu.ptman.ui.TimetablePanel.getTimetableRunningOut;
import static seedu.ptman.ui.testutil.GuiTestAssert.assertEntryDisplaysShift;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;

import javafx.collections.ObservableList;
import seedu.ptman.commons.events.ui.EmployeePanelSelectionChangedEvent;
import seedu.ptman.commons.events.ui.ExportTimetableAsImageAndEmailRequestEvent;
import seedu.ptman.commons.events.ui.ExportTimetableAsImageRequestEvent;
import seedu.ptman.commons.events.ui.TimetableWeekChangeRequestEvent;
import seedu.ptman.logic.Logic;
import seedu.ptman.logic.LogicManager;
import seedu.ptman.model.Model;
import seedu.ptman.model.ModelManager;
import seedu.ptman.model.PartTimeManager;
import seedu.ptman.model.UserPrefs;
import seedu.ptman.model.employee.Email;
import seedu.ptman.model.outlet.OutletInformation;
import seedu.ptman.model.shift.Shift;
import seedu.ptman.model.shift.exceptions.DuplicateShiftException;


//@@author hzxcaryn
public class TimetablePanelTest extends GuiUnitTest {

    private static final PartTimeManager TYPICAL_PTMAN =
            getTypicalPartTimeManagerWithShiftsWithoutSunday();
    private static final ObservableList<Shift> TYPICAL_SHIFTS = TYPICAL_PTMAN.getShiftList();
    private static final OutletInformation TYPICAL_OUTLET = TYPICAL_PTMAN.getOutletInformation();

    private static final String TIMETABLE_IMAGE_FILE_NAME_FIRST_TEST = "Testing1";
    private static final String TIMETABLE_IMAGE_FILE_NAME_SECOND_TEST = "Testing2";
    private static final Email TIMETABLE_IMAGE_EMAIL_TEST = new Email("example@gmail.com");

    private EmployeePanelSelectionChangedEvent employeePanelSelectionChangedEventAliceStub;
    private EmployeePanelSelectionChangedEvent employeePanelSelectionChangedEventNullStub;
    private ExportTimetableAsImageRequestEvent exportTimetableAsImageRequestEventStub;
    private ExportTimetableAsImageAndEmailRequestEvent exportTimetableAsImageAndEmailRequestEventStub;
    private TimetableWeekChangeRequestEvent timetableWeekChangeRequestEventPrevStub;
    private TimetableWeekChangeRequestEvent timetableWeekChangeRequestEventNextStub;
    private TimetableWeekChangeRequestEvent timetableWeekChangeRequestEventInvalidStub;

    private TimetablePanel timetablePanel;
    //private TimetableViewHandle timetableViewHandle;

    private Path testFilePathFirst;
    private Path testFilePathSecond;
    private String testFilePathNameSecond;
    private LocalDate startingDate;

    private Logic logic;

    @Before
    public void setUp() throws DuplicateShiftException {
        // Event stubs
        employeePanelSelectionChangedEventAliceStub =
                new EmployeePanelSelectionChangedEvent(new EmployeeCard(ALICE, 0));
        employeePanelSelectionChangedEventNullStub = new EmployeePanelSelectionChangedEvent(null);

        exportTimetableAsImageRequestEventStub =
                new ExportTimetableAsImageRequestEvent(TIMETABLE_IMAGE_FILE_NAME_FIRST_TEST);
        exportTimetableAsImageAndEmailRequestEventStub = new ExportTimetableAsImageAndEmailRequestEvent(
                TIMETABLE_IMAGE_FILE_NAME_SECOND_TEST, TIMETABLE_IMAGE_EMAIL_TEST);

        timetableWeekChangeRequestEventPrevStub = new TimetableWeekChangeRequestEvent(false, true);
        timetableWeekChangeRequestEventNextStub = new TimetableWeekChangeRequestEvent(true, false);
        timetableWeekChangeRequestEventInvalidStub = new TimetableWeekChangeRequestEvent(true, true);

        testFilePathFirst = Paths.get("." + File.separator + TIMETABLE_IMAGE_FILE_NAME_FIRST_TEST + "."
                + TIMETABLE_IMAGE_FILE_FORMAT);
        testFilePathNameSecond = "." + File.separator + TIMETABLE_IMAGE_FILE_NAME_SECOND_TEST + "."
                + TIMETABLE_IMAGE_FILE_FORMAT;
        testFilePathSecond = Paths.get(testFilePathNameSecond);

        Model model = new ModelManager(TYPICAL_PTMAN, new UserPrefs(), TYPICAL_OUTLET);
        logic = new LogicManager(model);
        logic.setFilteredShiftListToCustomWeek(MONDAY_AM.getDate().getLocalDate());
        timetablePanel = new TimetablePanel(logic);
        //timetableViewHandle = new TimetableViewHandle(timetablePanel.getRoot());
        uiPartRule.setUiPart(timetablePanel);

        startingDate = timetablePanel.getRoot().getDate();
    }

    @Test
    public void display() {
        // Default timetable view: Displays current week view
        assertNotNull(timetablePanel.getRoot());
        assertEquals(timetablePanel.getRoot().getSelectedPage(), timetablePanel.getRoot().getWeekPage());
        assertEquals(startingDate, timetablePanel.getRoot().getDate());

        // Default timetable view: Displays all shifts
        List<Entry> defaultEntries = getTimetableEntries();
        for (int i = 0; i < TYPICAL_SHIFTS.size(); i++) {
            Shift expectedShift = TYPICAL_SHIFTS.get(i);
            Entry actualEntry = defaultEntries.get(i);
            assertEntryDisplaysShift(expectedShift, actualEntry, i + 1);
        }

        // Associated shifts of employee highlighted
        postNow(employeePanelSelectionChangedEventAliceStub);
        List<Entry> entriesAfterSelectionEventAlice = getTimetableEntries();
        for (int i = 0; i < TYPICAL_SHIFTS.size(); i++) {
            Shift expectedShift = TYPICAL_SHIFTS.get(i);
            Entry actualEntry = entriesAfterSelectionEventAlice.get(i);
            assertEntryDisplaysShift(expectedShift, actualEntry, i + 1);
        }

        // Load back to default timetable view: Displays current week view
        postNow(employeePanelSelectionChangedEventNullStub);
        List<Entry> entriesAfterSelectionEventNull = getTimetableEntries();
        for (int i = 0; i < logic.getFilteredShiftList().size(); i++) {
            Shift expectedShift = TYPICAL_SHIFTS.get(i);
            Entry actualEntry = entriesAfterSelectionEventNull.get(i);
            assertEntryDisplaysShift(expectedShift, actualEntry, i + 1);
        }
    }

    @Test
    public void timetablePanel_handleTimetableWeekChangeRequestEvent() {
        postNow(timetableWeekChangeRequestEventNextStub);
        assertEquals(getNextWeekDate(startingDate), timetablePanel.getRoot().getDate());

        postNow(timetableWeekChangeRequestEventPrevStub);
        assertEquals(startingDate, timetablePanel.getRoot().getDate());

        postNow(timetableWeekChangeRequestEventInvalidStub);
        assertEquals(startingDate, timetablePanel.getRoot().getDate());

        postNow(timetableWeekChangeRequestEventPrevStub);
        assertEquals(getPrevWeekDate(startingDate), timetablePanel.getRoot().getDate());
    }

    @Test
    public void timetablePanel_handleExportTimetableAsImageRequestEvent() {
        // Snapshot taken when export command called
        postNow(exportTimetableAsImageRequestEventStub);
        assertTrue(Files.exists(testFilePathFirst) && Files.isRegularFile(testFilePathFirst));
    }

    @Test
    public void timetablePanel_handleExportTimetableAsImageAndEmailRequestEvent() {
        // Snapshot taken when export and email command called: Emailed file is not saved locally
        File testFileSecond = new File(testFilePathNameSecond);
        postNow(exportTimetableAsImageAndEmailRequestEventStub);
        assertFalse(Files.exists(testFilePathSecond));
        assertFalse(testFileSecond.exists());
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(testFilePathFirst);
            Files.deleteIfExists(testFilePathSecond);
        } catch (IOException e) {
            throw new AssertionError("Error deleting test files.");
        }
    }

    private List<Entry<?>> getEntriesForEntryType(Calendar entryType) {
        Map<LocalDate, List<Entry<?>>> entryMap = entryType.findEntries(
                LocalDate.of(2018, 3, 19).minusDays(7), LocalDate.of(2018, 3, 19).plusDays(7), ZoneId.systemDefault());
        List<Entry<?>> entryList = entryMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return entryList;
    }

    private List<Entry> getTimetableEntries() {
        List<Entry<?>> availEntries = getEntriesForEntryType(getTimetableAvail());
        List<Entry<?>> runningOutEntries = getEntriesForEntryType(getTimetableRunningOut());
        List<Entry<?>> fullEntries = getEntriesForEntryType(getTimetableFull());
        List<Entry<?>> employeeEntries = getEntriesForEntryType(getTimetableEmployee());
        List<Entry<?>> othersEntries = getEntriesForEntryType(getTimetableOthers());

        ArrayList<Entry> entries = new ArrayList<>(Stream.of(
                availEntries, runningOutEntries, fullEntries, employeeEntries, othersEntries)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));

        Collections.sort(entries, Comparator.comparing(Entry::getStartAsLocalDateTime));
        return entries;
    }

}
