import java.util.HashMap;

class LRUCache {
    
    private class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
    }
    
    private void addNode(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        
        head.next.prev = node;
        head.next = node;
    }
    
    private void removeNode(DLinkedNode node) {
        DLinkedNode prev = node.prev;
        DLinkedNode next = node.next;
        
        prev.next = next;
        next.prev = prev;
    }
    
    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addNode(node);
    }
    
    private DLinkedNode popTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }
    
    private HashMap<Integer, DLinkedNode> cache = new HashMap<>();
    private int size;
    private int capacity;
    private DLinkedNode head, tail;
    
    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        
        head = new DLinkedNode();
        tail = new DLinkedNode();
        
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        
        moveToHead(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        
        if (node == null) {
            // If the key does not exist, create a new node
            DLinkedNode newNode = new DLinkedNode();
            newNode.key = key;
            newNode.value = value;
            
            // Add the new node to the cache (HashMap and LinkedList)
            cache.put(key, newNode);
            addNode(newNode);
            
            ++size;
            
            // If the cache exceeds its capacity, remove the LRU item
            if (size > capacity) {
                DLinkedNode tail = popTail();
                cache.remove(tail.key);
                --size;
            }
        } else {
            // If the key exists, update the value and move it to the head
            node.value = value;
            moveToHead(node);
        }
    }
    
    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);

        // Initial put operations
        lruCache.put(10, 10);
        lruCache.put(20, 20);

        // First get operation
        System.out.println(lruCache.get(10)); // returns 10

        // This will evict key 20
        lruCache.put(30, 30);

        // Get operation for the evicted key
        System.out.println(lruCache.get(20)); // returns -1 (not found)

        // This will evict key 10
        lruCache.put(40, 40);

        // Get operation for the evicted key
        System.out.println(lruCache.get(10)); // returns -1 (not found)

        // Get operation for existing keys
        System.out.println(lruCache.get(30)); // returns 30
        System.out.println(lruCache.get(40)); // returns 40

        // Adding more keys and checking the state
        lruCache.put(50, 50); // This will evict key 30
        System.out.println(lruCache.get(30)); // returns -1 (not found)
        System.out.println(lruCache.get(40)); // returns 40
        System.out.println(lruCache.get(50)); // returns 50

        // Accessing key 40 to make it recently used
        lruCache.get(40);

        // Adding another key which will evict the least recently used key 50
        lruCache.put(60, 60);
        System.out.println(lruCache.get(50)); // returns -1 (not found)
        System.out.println(lruCache.get(40)); // returns 40
        System.out.println(lruCache.get(60)); // returns 60

      
    }
}
