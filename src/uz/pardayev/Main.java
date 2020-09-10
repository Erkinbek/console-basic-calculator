package uz.pardayev;

import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        process(line);
    }

    public static void process(String line) throws Exception {
        String a, operator, b;

        if (line.length() < 2) {
            throw new Exception("Allowed enter all data to single line");
        }

        try {
            String[] arr = line.split(" ");
            a = arr[0];
            operator = arr[1];
            b = arr[2];
        } catch (Exception e) {
            throw new Exception("Please enter data with empty space");
        }

        if(!isOp(operator)) {
            throw new Exception("Sorry, but i can support only mathematics basic operators, maybe i will later upgrade");
        }

        int x, y;
        boolean isRoman = false;
        boolean isPositive = true;

        try {
            x = Integer.parseInt(a);
            y = Integer.parseInt(b);
        } catch (Exception e) {
            try {
                x = romanToDecimal(a);
                y = romanToDecimal(b);
                isRoman = true;
            } catch (Exception e1) {
                throw new Exception("Please enter same type data");
            }
        }

        isInRange(x);
        isInRange(y);

        int result = calculate(x, y, operator);

        if (result < 0 && isRoman) {
            result = result * (-1);
            isPositive = false;
        }
        System.out.println(isRoman ? (isPositive ? toRoman(result) : "(-)" + toRoman(result)) : result);
    }


    public static int calculate(int x, int y, String operator) {
        switch (operator) {
            case "+" : return x + y;
            case "-" : return x - y;
            case "*" : return x * y;
            case "/" : return x / y;
        }
        return 0;
    }

    private static void isInRange(int a) throws Exception {
        if (a < 1 || a > 10) {
            throw new Exception("Params can in range [1; 10]");
        }
    }

    private static boolean isOp(String c) {
        switch (c) {
            case "-":
            case "+":
            case "*":
            case "/":
                return true;
        }
        return false;
    }


    public static int romanToDecimal(String romanNumber) {
        int decimal = 0;
        int lastNumber = 0;
        String romanNumeral = romanNumber.toUpperCase();
        for (int x = romanNumeral.length() - 1; x >= 0 ; x--) {
            char convertToDecimal = romanNumeral.charAt(x);

            switch (convertToDecimal) {
                case 'M':
                    decimal = processDecimal(1000, lastNumber, decimal);
                    lastNumber = 1000;
                    break;

                case 'D':
                    decimal = processDecimal(500, lastNumber, decimal);
                    lastNumber = 500;
                    break;

                case 'C':
                    decimal = processDecimal(100, lastNumber, decimal);
                    lastNumber = 100;
                    break;

                case 'L':
                    decimal = processDecimal(50, lastNumber, decimal);
                    lastNumber = 50;
                    break;

                case 'X':
                    decimal = processDecimal(10, lastNumber, decimal);
                    lastNumber = 10;
                    break;

                case 'V':
                    decimal = processDecimal(5, lastNumber, decimal);
                    lastNumber = 5;
                    break;

                case 'I':
                    decimal = processDecimal(1, lastNumber, decimal);
                    lastNumber = 1;
                    break;
            }
        }
        return decimal;
    }

    public static int processDecimal(int decimal, int lastNumber, int lastDecimal) {
        if (lastNumber > decimal) {
            return lastDecimal - decimal;
        } else {
            return lastDecimal + decimal;
        }
    }

    public static String toRoman(int number) {
        if (number == 0) {
            return "0";
        }
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }
}
