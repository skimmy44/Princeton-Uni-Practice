package tool;

import java.util.ArrayList;
import java.util.Arrays;

public class StringUtility {

    //TODO deliverable 9
    //improve cohesion
    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }
}
