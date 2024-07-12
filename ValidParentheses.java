import java.util.Stack;

public class ValidParentheses {

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if ((ch == ')' && top != '(') ||
                        (ch == '}' && top != '{') ||
                        (ch == ']' && top != '[')) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ValidParentheses validator = new ValidParentheses();

        String str1 = "()";
        String str2 = "()[]{}";
        String str3 = "(]";
        String str4 = "([)]";
        String str5 = "{[]}";

        System.out.println("String " + str1 + " is valid: " + validator.isValid(str1));
        System.out.println("String " + str2 + " is valid: " + validator.isValid(str2));
        System.out.println("String " + str3 + " is valid: " + validator.isValid(str3));
        System.out.println("String " + str4 + " is valid: " + validator.isValid(str4));
        System.out.println("String " + str5 + " is valid: " + validator.isValid(str5));
    }
}
