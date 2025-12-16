
import java.util.ArrayList;

public class Guest extends Person {

    private static int guestCounter = 1;
    private final int guestId;
    private String preferences;

    public Guest(String name, String phoneNumber, String preferences) {
        super(name, phoneNumber);
        this.guestId = guestCounter++;
        this.preferences = preferences;
    }

    public int getGuestId() {
        return guestId;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Guest)) return false;
        Guest other = (Guest) obj;
        return guestId == other.guestId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(guestId);
    }

    @Override
    public String toString() {
        return "Guest #" + guestId + " - " + super.toString() +
               (preferences != null ? " | Preferences: " + preferences : "");
    }
}
