package Guests; 

import java.util.ArrayList;

public class Guest extends Person {

    private static int guestCounter = 1;

    private int guestId;
    private boolean checkedIn;
    private ArrayList<String> preferences;

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
        if (!checkedIn) {
            checkedIn = true;
            System.out.println(name + " has checked in.");
        } else {
            System.out.println(name + " is already checked in.");
        }
    }

    public void checkOut() {
        if (checkedIn) {
            checkedIn = false;
            System.out.println(name + " has checked out.");
        } else {
            System.out.println(name + " is not checked in.");
        }
    }

    public void addPreference(String preference) {
        if (preference == null || preference.isBlank()) {
            throw new IllegalArgumentException("Preference cannot be empty.");
        }
        preferences.add(preference);
    }

    public ArrayList<String> getPreferences() {
        return new ArrayList<>(preferences);
    }

    public void viewDetails() {
        System.out.println("========= GUEST DETAILS =========");
        System.out.println("ID: " + guestId);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Checked In: " + (checkedIn ? "Yes" : "No"));
        System.out.println("Preferences: " + (preferences.isEmpty() ? "None" : preferences));
        System.out.println("=================================");
    }

    @Override
    public String toString() {
        return "Guest " + guestId + ": " + name + " (" + phoneNumber + ")";
    }
}

