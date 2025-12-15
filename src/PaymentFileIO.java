import java.io.*;
import java.util.*;

public class PaymentFileIO {

    public static List<Payment> loadPayments(String filename, List<Reservation> reservations) {
        List<Payment> payments = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.equals("---")) continue;

                if (line.equals("CARD")) {
                    String cardNumber = br.readLine().trim();
                    String cardOwner = br.readLine().trim();
                    String expiryDate = br.readLine().trim();
                    String cvv = br.readLine().trim();
                    double amount = Double.parseDouble(br.readLine().trim());
                    int reservationId = Integer.parseInt(br.readLine().trim());

                    Reservation r = findReservation(reservations, reservationId);
                    if (r != null) {
                        Payment p = new PayByCard(r.getInvoice(), amount, cardNumber, cardOwner, expiryDate, cvv);
                        p.processPayment();
                        payments.add(p);
                    }

                } else if (line.equals("CASH")) {
                    double amountReceived = Double.parseDouble(br.readLine().trim());
                    double amount = Double.parseDouble(br.readLine().trim());
                    int reservationId = Integer.parseInt(br.readLine().trim());

                    Reservation r = findReservation(reservations, reservationId);
                    if (r != null) {
                        Payment p = new PayByCash(r.getInvoice(), amount, amountReceived);
                        p.processPayment();
                        payments.add(p);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading payment file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing payment data: " + e.getMessage());
        }

        return payments;
    }

    private static Reservation findReservation(List<Reservation> reservations, int reservationId) {
        for (Reservation r : reservations) {
            if (r.getReservationId() == reservationId) return r;
        }
        return null;
    }
}
