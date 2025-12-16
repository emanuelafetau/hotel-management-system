
import java.util.ArrayList;
import java.util.List;

public class Guest extends Person {

    private static int guestCounter = 1;

    private final int guestId;
    private boolean checkedIn;
    private final List<String> preferences;

    public Guest(String name, String phoneNumber) {
        super(name, phoneNumber);
        this.guestId = guestCounter++;
        this.checkedIn = false;
        this.preferences = new ArrayList<>();
    }

    public int getGuestId() {
        return guestId;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void checkIn() {
        if (checkedIn) {
            throw new IllegalStateException("Guest is already checked in.");
        }
        checkedIn = true;
    }

    public void checkOut() {
        if (!checkedIn) {
            throw new IllegalStateException("Guest is not checked in.");
        }
        checkedIn = false;
    }

    public void addPreference(String preference) {
        if (preference == null || preference.isBlank()) {
            throw new IllegalArgumentException("Preference cannot be null or empty.");
        }
        preferences.add(preference);
    }

    public List<String> getPreferences() {
        return new ArrayList<>(preferences);
    }

    @Override
    public String toString() {
        return String.format(
            "Guest [ID=%d, Name=%s, Phone=%s, CheckedIn=%s, Preferences=%s]",
            guestId,
            getName(),
            getPhoneNumber(),
            checkedIn ? "Yes" : "No",
            preferences
        );
    }
}

