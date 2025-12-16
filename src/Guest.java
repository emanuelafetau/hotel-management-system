package Guests; 

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Objects;

public class Guest extends Person {

    private static int guestCounter = 1;
    private static final ArrayList<Guest> allGuests = new ArrayList<>();

    private final int guestId;
    private boolean checkedIn;
    private final ArrayList<String> preferences;

    public Guest(String name, String phoneNumber) {
        super(name, phoneNumber);
        this.guestId = guestCounter++;
        this.checkedIn = false;
        this.preferences = new ArrayList<>();
        allGuests.add(this);
    }


    public static ArrayList<Guest> viewAllGuests() {
        return new ArrayList<>(allGuests);
    }

    public static Guest findGuestById(int guestId) {
        for (Guest g : allGuests) {
            if (g.guestId == guestId) return g;
        }
        return null;
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
            throw new IllegalStateException("Guest is not currently checked in.");
        }
        checkedIn = false;
    }


    public ArrayList<String> getPreferences() {
        return new ArrayList<>(preferences);
    }

    public void addPreference(String preference) {
        if (preference == null || preference.isBlank()) {
            throw new IllegalArgumentException("Preference cannot be empty.");
        }
        preferences.add(preference);
    }

    public boolean removePreference(String preference) {
        return preferences.remove(preference);
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
        return Objects.hash(guestId);
    }

    public void viewDetails() {
        System.out.println("========== Guest Details ==========");
        System.out.println("Guest ID: " + guestId);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Checked In: " + (checkedIn ? "Yes" : "No"));
        System.out.println("Preferences: " +
                (preferences.isEmpty() ? "None" : preferences));
        System.out.println("===================================");
    }

    @Override
    public String toString() {
        return "Guest " + guestId + " - " + super.toString();
    }
}
