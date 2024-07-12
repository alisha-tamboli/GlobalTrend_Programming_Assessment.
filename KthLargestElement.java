import java.util.Random;

public class KthLargestElement {

    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        return quickSelect(nums, 0, nums.length - 1, nums.length - k); // Finding (n-k)th smallest
    }

    private int quickSelect(int[] nums, int left, int right, int k) {
        if (left == right) {
            return nums[left];
        }

        // Randomly select pivot element and place it at the rightmost position
        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);
        swap(nums, pivotIndex, right);

        // Partition the array around the pivot
        int partitionIndex = partition(nums, left, right);

        // Determine which partition to recurse into
        if (k < partitionIndex) {
            return quickSelect(nums, left, partitionIndex - 1, k);
        } else if (k > partitionIndex) {
            return quickSelect(nums, partitionIndex + 1, right, k);
        } else {
            return nums[partitionIndex];
        }
    }

    private int partition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int i = left - 1; // Index of smaller element

        for (int j = left; j < right; j++) {
            if (nums[j] <= pivot) {
                i++;
                swap(nums, i, j);
            }
        }

        swap(nums, i + 1, right); // Move pivot to its correct position
        return i + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        KthLargestElement solution = new KthLargestElement();

        // Example usage
        int[] nums1 = { 3, 2, 1, 5, 6, 4 };
        int k1 = 2;
        System.out.println("2nd largest element in nums1: " + solution.findKthLargest(nums1, k1)); // Output: 5

        int[] nums2 = { 3, 2, 1, 5, 6, 4 };
        int k2 = 4;
        System.out.println("4th largest element in nums2: " + solution.findKthLargest(nums2, k2)); // Output: 3
    }
}
