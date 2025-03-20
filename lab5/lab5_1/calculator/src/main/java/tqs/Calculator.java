package tqs;

public class Calculator {
    private Double value;
    private String operator;
    private Double arg1;
    private Double arg2;

    public void push(int number) {
        if (arg1 == null) {
            arg1 = (double) number;
        } else {
            arg2 = (double) number;
        }
    }

    public void push(String s) {
        if (s.equals("+") || s.equals("-") || s.equals("*")) {
            operator = s;
        }
    }

    public Double value() {
        if (operator != null && arg1 != null && arg2 != null) {
            if (operator.equals("+")) {
                value = arg1 + arg2;
            } else if (operator.equals("-")) {
                value = arg1 - arg2;
            } else if (operator.equals("*")) {
                value = arg1 * arg2;
            }
        }
        return value;
    }
}