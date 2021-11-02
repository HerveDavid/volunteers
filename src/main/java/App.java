import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class App {

    public static void main(String[] args) throws IOException {
        List<User> lines = Files.readAllLines(Paths.get("src/main/resources/data.csv"))
            .stream()
            .map(splitCSV)
            .map(createUserFromLine)
            .sorted(Collections.reverseOrder())
            .collect(toList());

        HashMap<Integer, User> usersDict = new HashMap<>();
        for(User user: lines){
            if(!(usersDict.containsKey(user.id))){
                usersDict.put(user.id, user);
            }
        }

        lines.forEach(System.out::println);
        System.out.println("Nombre d'itération : " + lines.stream().count());
/*

        String crunchifyCSVFile = "src/main/resources/data.csv";

        BufferedReader crunchifyBufferReader = null;
        String crunchifyLine = "";
        int i = 0;
        HashSet<String> crunchifyAllLines = new HashSet<>();

        try {
            crunchifyBufferReader = new BufferedReader(new FileReader(crunchifyCSVFile));
            while ((crunchifyLine = crunchifyBufferReader.readLine()) != null) {
                if (crunchifyAllLines.add(crunchifyLine)) {
                    crunchifyLog(crunchifyLine);
                    i++;
                } else if (!crunchifyIsNullOrEmpty(crunchifyLine)) {
                    crunchifyLog("--------------- Ligne sautee : " + crunchifyLine);
                }
            }
            crunchifyLog("Nombre d'itération : " + i );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (crunchifyBufferReader != null) {
                try {
                    crunchifyBufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/

    }
/*

    public static boolean crunchifyIsNullOrEmpty(String crunchifyString) {

        if (crunchifyString != null && !crunchifyString.trim().isEmpty())
            return false;
        return true;
    }

    // Simple method for system outs
    private static void crunchifyLog(String s) {

        System.out.println(s);
    }
*/

    public static Function<String, String[]> splitCSV = (row) -> row.split(";", -1);

    public static Function<String[], User> createUserFromLine =
            (line) -> new User(line[0], line[1], line[2], line[3], line[4]);

}
