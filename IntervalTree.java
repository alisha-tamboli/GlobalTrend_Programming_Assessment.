import java.util.ArrayList;
import java.util.List;

class Interval {
    int start;
    int end;

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

class IntervalNode {
    Interval interval;
    int maxEnd;
    IntervalNode left;
    IntervalNode right;

    public IntervalNode(Interval interval) {
        this.interval = interval;
        this.maxEnd = interval.end;
        this.left = null;
        this.right = null;
    }
}

public class IntervalTree {
    private IntervalNode root;

    public IntervalTree() {
        this.root = null;
    }

    public void insertInterval(int start, int end) {
        Interval interval = new Interval(start, end);
        this.root = insert(this.root, interval);
    }

    private IntervalNode insert(IntervalNode node, Interval interval) {
        if (node == null) {
            return new IntervalNode(interval);
        }

        // Insert in BST manner based on start of intervals
        if (interval.start < node.interval.start) {
            node.left = insert(node.left, interval);
        } else {
            node.right = insert(node.right, interval);
        }

        // Update maxEnd in current node
        if (node.maxEnd < interval.end) {
            node.maxEnd = interval.end;
        }

        return node;
    }

    public void deleteInterval(int start, int end) {
        Interval interval = new Interval(start, end);
        this.root = delete(this.root, interval);
    }

    private IntervalNode delete(IntervalNode node, Interval interval) {
        if (node == null) {
            return null;
        }

        // Perform BST delete
        if (interval.start < node.interval.start) {
            node.left = delete(node.left, interval);
        } else if (interval.start > node.interval.start) {
            node.right = delete(node.right, interval);
        } else {
            // Found the node to delete
            if (interval.end == node.interval.end) {
                if (node.left == null) {
                    return node.right;
                } else if (node.right == null) {
                    return node.left;
                }

                // Node with two children, get the inorder successor (smallest in the right
                // subtree)
                IntervalNode successor = findMin(node.right);
                node.interval = successor.interval;

                // Delete the inorder successor
                node.right = delete(node.right, successor.interval);
            } else {
                node.right = delete(node.right, interval);
            }
        }

        // Update maxEnd in current node
        if (node != null) {
            node.maxEnd = Math.max(node.interval.end, getMaxEnd(node.right));
        }

        return node;
    }

    private IntervalNode findMin(IntervalNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private int getMaxEnd(IntervalNode node) {
        return node == null ? Integer.MIN_VALUE : node.maxEnd;
    }

    public List<Interval> findOverlappingIntervals(int start, int end) {
        List<Interval> result = new ArrayList<>();
        findOverlappingIntervals(root, start, end, result);
        return result;
    }

    private void findOverlappingIntervals(IntervalNode node, int start, int end, List<Interval> result) {
        if (node == null) {
            return;
        }

        // Check if node interval overlaps with [start, end]
        if (node.interval.start <= end && node.interval.end >= start) {
            result.add(node.interval);
        }

        // Recursively search in left and right subtrees if necessary
        if (node.left != null && node.left.maxEnd >= start) {
            findOverlappingIntervals(node.left, start, end, result);
        }
        if (node.right != null && node.right.interval.start <= end) {
            findOverlappingIntervals(node.right, start, end, result);
        }
    }

    public static void main(String[] args) {
        IntervalTree intervalTree = new IntervalTree();

        // Insert intervals
        intervalTree.insertInterval(15, 20);
        intervalTree.insertInterval(10, 30);
        intervalTree.insertInterval(17, 19);
        intervalTree.insertInterval(5, 20);
        intervalTree.insertInterval(12, 15);
        intervalTree.insertInterval(30, 40);

        // Find overlapping intervals with [14, 16]
        List<Interval> overlappingIntervals = intervalTree.findOverlappingIntervals(14, 16);
        System.out.println("Overlapping Intervals with [14, 16]:");
        for (Interval interval : overlappingIntervals) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }

        // Delete interval [12, 15]
        intervalTree.deleteInterval(12, 15);

        // Find overlapping intervals with [14, 16] again
        overlappingIntervals = intervalTree.findOverlappingIntervals(14, 16);
        System.out.println("Overlapping Intervals with [14, 16] after deletion of [12, 15]:");
        for (Interval interval : overlappingIntervals) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }
    }
}
