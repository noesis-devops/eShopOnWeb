package commons;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Generator {

    /**
     * Gerar um endereço de e-mail aleatório.
     *
     * @return email aleatório.
     */
    public static String email() {
        int randomDigit = new Random().nextInt(1000);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return "test." + timestamp + "." + String.valueOf(randomDigit) + "@mailinator.com";
    }

    /**
     * Gerar um nome completo aleatório combinando um primeiro nome e sobrenome aleatórios.
     *
     * @return nome completo randomico.
     */
    public static String fullName() {
        return firstName() + " " + lastName();
    }

    /**
     * Gera um primeiro nome aleatório de uma lista predefinida.
     *
     * @return primeiro nome randomico.
     */
    public static String firstName() {
        String[] first_names = {
                "George", "John", "Thomas", "James", "Andrew", "Martin", "William", "Zachary", "Millard",
                "Franklin", "Abraham", "Ulysses", "Rutherford", "Chester", "Grover", "Benjamin", "Theodore",
                "Woodrow", "Warren", "Calvin", "Herbert", "Harry", "Dwight", "Lyndon", "Richard", "Gerald",
                "Jimmy", "Ronald", "Bill", "Barack", "Donald", "Joe"
        };
        int random = new Random().nextInt(first_names.length);
        return first_names[random];
    }

    /**
     * Gera um sobrenome aleatório de uma lista predefinida.
     *
     * @return sobrenome randomico.
     */
    public static String lastName() {
        String[] last_names = {
                "Washington", "Adams", "Jefferson", "Madison", "Monroe", "Jackson", "Van Buren", "Harrison",
                "Tyler", "Polk", "Taylor", "Fillmore", "Pierce", "Buchanan", "Lincoln", "Johnson", "Grant",
                "Hayes", "Garfield", "Arthur", "Cleveland", "McKinley", "Roosevelt", "Taft", "Wilson",
                "Harding", "Coolidge", "Hoover", "Truman", "Eisenhower", "Kennedy", "Johnson", "Nixon",
                "Ford", "Carter", "Reagan", "Bush", "Clinton", "Obama", "Trump", "Biden"
        };
        int random = new Random().nextInt(last_names.length);
        return last_names[random];
    }

    public static String currentTimeMillis() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddHHmmss");
        return LocalDateTime.now().format(formatter);
    }
}
