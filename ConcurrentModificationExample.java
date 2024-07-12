import java.util.ArrayList;
import java.util.Iterator;

public class ConcurrentModificationExample {

    public static void main(String[] args) {
        // Efficient time complexity (O(n)) for ArrayList creation
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }

        // Iterate using iterator
        Iterator<Integer> iterator = numbers.iterator();

        // Attempt to modify the list while iterating (causes exception)
        while (iterator.hasNext()) {
            int number = iterator.next();
            if (number % 2 == 0) {
                numbers.remove(number); // This will throw ConcurrentModificationException
            }
            System.out.println(number);
        }
    }
}
