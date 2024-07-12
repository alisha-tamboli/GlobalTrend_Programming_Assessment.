public class ContainerWithMostWater {

    public int maxArea(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            int minHeight = Math.min(height[left], height[right]);
            int currentArea = minHeight * (right - left);
            maxArea = Math.max(maxArea, currentArea);

            // Move the pointer pointing to the shorter line inward
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        ContainerWithMostWater solution = new ContainerWithMostWater();

        // Example usage
        int[] heights1 = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
        System.out.println("Max area for heights 1: " + solution.maxArea(heights1)); // Output: 49

        int[] heights2 = { 1, 1 };
        System.out.println("Max area for heights 2: " + solution.maxArea(heights2)); // Output: 1

        int[] heights3 = { 4, 3, 2, 1, 4 };
        System.out.println("Max area for heights 3: " + solution.maxArea(heights3)); // Output: 16

        int[] heights4 = { 1, 2, 1 };
        System.out.println("Max area for heights 4: " + solution.maxArea(heights4)); // Output: 2
    }
}
