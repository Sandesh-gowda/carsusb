package sample.com.carusb.utils;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StringUtils {
    private HashMap<Point, String> map = new HashMap<Point, String>();
    private int maxRow = 0;
    private int maxColumn = 0;

    public StringUtils() {

    }

    public String uppercaseFirstLetterofSentence(String sourceString) {
        String source = sourceString;
        StringBuffer res = new StringBuffer();

        String[] strArr = source.split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(str).append(" ");
        }
        return res.toString().trim();
    }

    public void add(int row, int column, String string) {
        map.put(new Point(row, column), string);
        maxRow = Math.max(row, maxRow);
        maxColumn = Math.max(column, maxColumn);
    }

    // Convert data into 2D String Array
    public String[][] toArray() {
        String[][] result = new String[maxRow + 1][maxColumn + 1];
        for (int row = 0; row <= maxRow; ++row)
            for (int column = 0; column <= maxColumn; ++column) {
                Point p = new Point(row, column);
                result[row][column] = map.containsKey(p) ? map.get(p) : "";
            }
        return result;
    }

    public String stringArraytoString(String[][] st) {
        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (int i = 0; i < st.length; i++) {

            for (int j = 0; j < st[i].length; j++) {
                sb.append(delim);
                delim = "";
                sb.append(st[i][j]);

            }
            delim = ",";

        }
        Log.v("Str", sb.toString());
        return sb.toString();
    }

    public String[][] stringtoStringArray(String st) {
        String[][] g = new String[][]{st.split(",")};
        Log.v("Len", "" + g.length);
        for (int i = 0; i < g.length; i++) {
            for (int k = 0; k < g[i].length; k++) {
                Log.v("Strng", g[i][k]);
            }
        }
        return g;
    }

    // Convert ArrayList into String
    public static String convertToString(ArrayList<String> list) {

        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String s : list) {
            sb.append(delim);
            sb.append(s);
            ;
            delim = ", ";
        }
        return sb.toString();
    }

    // Convert Strings into ArrayList
    public static ArrayList<String> convertToArray(String string) {

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(string
                .split(", ")));
        return list;
    }


}
