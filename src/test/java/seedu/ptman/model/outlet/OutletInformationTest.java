package seedu.ptman.model.outlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Test;

import seedu.ptman.model.Password;
import seedu.ptman.testutil.Assert;

public class OutletInformationTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletContact outletContact = new OutletContact("91234567");
        OutletEmail outletEmail = new OutletEmail("outlet@gmail.com");
        Password password = new Password();
        Assert.assertThrows(NullPointerException.class, () -> new OutletInformation(null, operatingHours,
                outletContact, outletEmail, password));
    }

    @Test
    public void constructor_nullOperatingHours_throwsNullPointerException() {
        OutletName name = new OutletName("outlet");
        OutletContact outletContact = new OutletContact("91234567");
        OutletEmail outletEmail = new OutletEmail("outlet@gmail.com");
        Password password = new Password();
        Assert.assertThrows(NullPointerException.class, () -> new OutletInformation(name,
                null, outletContact, outletEmail, password));
    }

    @Test
    public void constructor_nullOutletContact_throwsNullPointerException() {
        OutletName name = new OutletName("outlet");
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletEmail outletEmail = new OutletEmail("outlet@gmail.com");
        Password password = new Password();
        Assert.assertThrows(NullPointerException.class, () -> new OutletInformation(name,
                operatingHours, null, outletEmail, password));
    }

    @Test
    public void constructor_nullOutletEmail_throwsNullPointerException() {
        OutletName name = new OutletName("outlet");
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletContact outletContact = new OutletContact("91234567");
        Password password = new Password();
        Assert.assertThrows(NullPointerException.class, () -> new OutletInformation(name, operatingHours,
                outletContact, null, password));
    }

    @Test
    public void equals_sameOutletInformation_returnsTrue() {
        OutletName name = new OutletName("outlet");
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletContact outletContact = new OutletContact("91234567");
        OutletEmail outletEmail = new OutletEmail("outlet@gmail.com");
        Password password = new Password();
        OutletInformation outlet = new OutletInformation(name, operatingHours, outletContact, outletEmail, password);
        OutletInformation other = new OutletInformation(name, operatingHours, outletContact, outletEmail, password);
        assertTrue(outlet.equals(other));
    }

    @Test
    public void hashCode_sameObject_returnsTrue() {
        Password masterPassword = new Password();
        OutletName name = new OutletName("outlet");
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletContact outletContact = new OutletContact("91234567");
        OutletEmail outletEmail = new OutletEmail("outlet@gmail.com");
        OutletInformation outlet = new OutletInformation(name, operatingHours, outletContact, outletEmail,
                masterPassword);
        assertEquals(outlet.hashCode(), Objects.hash(name, masterPassword, operatingHours, outletContact, outletEmail));
    }

    @Test
    public void toString_validInput_returnsTrue() {
        OutletName name = new OutletName("outlet");
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletContact outletContact = new OutletContact("91234567");
        OutletEmail outletEmail = new OutletEmail("outlet@gmail.com");
        Password password = new Password();
        OutletInformation outlet = new OutletInformation(name, operatingHours, outletContact, outletEmail, password);
        String expected = "Operating Hour: 09:00-22:00 Contact: 91234567 "
                + "Email: outlet@gmail.com";
        assertEquals(outlet.toString(), expected);
    }
}
