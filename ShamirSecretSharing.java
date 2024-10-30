import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShamirSecretSharing {

    public static void main(String[] args) throws IOException {
        // Read the JSON input file
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File("input.json"));

        // Extract the keys
        JsonNode keysNode = rootNode.get("keys");
        int n = keysNode.get("n").asInt();
        int k = keysNode.get("k").asInt();

        // Prepare lists for x and y values
        List<Integer> xValues = new ArrayList<>();
        List<Integer> yValues = new ArrayList<>();

        // Decode the y values
        for (int i = 1; i <= n; i++) {
            JsonNode node = rootNode.get(String.valueOf(i));
            int base = node.get("base").asInt();
            String value = node.get("value").asText();
            int x = i; // x is the key (1, 2, ..., n)
            int y = decodeValue(value, base);
            xValues.add(x);
            yValues.add(y);
        }

        // Calculate the constant term c using Lagrange interpolation
        int c = lagrangeInterpolation(xValues, yValues, 0);
        System.out.println("The constant term c is: " + c);
    }

    // Function to decode a value from a given base to decimal
    private static int decodeValue(String value, int base) {
        int decodedValue = 0;
        int power = 0;
        for (int i = value.length() - 1; i >= 0; i--) {
            char digit = value.charAt(i);
            int digitValue = Character.digit(digit, base);
            decodedValue += digitValue * Math.pow(base, power);
            power++;
        }
        return decodedValue;
    }

    // Function to perform Lagrange interpolation
    private static int lagrangeInterpolation(List<Integer> xValues, List<Integer> yValues, int x) {
        int total = 0;
        int n = xValues.size();

        for (int i = 0; i < n; i++) {
            int term = yValues.get(i);
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    term = term * (x - xValues.get(j)) / (xValues.get(i) - xValues.get(j));
                }
            }
            total += term;
        }
        return total;
    }
}