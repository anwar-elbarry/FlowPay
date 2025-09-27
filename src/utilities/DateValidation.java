package utilities;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateValidation {
    private static final Scanner sc = new Scanner(System.in);

    public static LocalDate askDate(String message) {
        while (true) {
            System.out.println(message);
            String input = sc.nextLine();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Format invalide. Réessayez (ex: 2025-09-23).");
            }
        }
    }

    public static int compareDates(LocalDate d1, LocalDate d2) {
        return d1.compareTo(d2);
    }
}
