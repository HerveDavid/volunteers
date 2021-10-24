import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class App {

    public static void main(String[] args) throws IOException {
        List<User> lines = Files.readAllLines(Paths.get("src/main/resources/data.csv"))
            .stream()
            .map(splitCSV)
            .map(createUserFromLine)
            .sorted()
            .collect(toList());

        // Apply dark magic here...
        lines.forEach(System.out::println);
    }

    public static Function<String, String[]> splitCSV = (row) -> row.split(";", -1);

    public static Function<String[], User> createUserFromLine =
            (line) -> new User(line[0], line[1], line[2], line[3], line[4]);

}
