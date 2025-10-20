package queue;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Comparator;

/**
 * A minimal priority queue implementation using a binary min-heap.
 * Elements are ordered according to the provided comparator or natural ordering.
 *
 * @param <E> the type of elements held in this queue
 */
class PriorityQueueImplementation<E> {

    private final ArrayList<E> heap = new ArrayList<>();
    private final Comparator<? super E> comparator;

    /**
     * Constructs an empty priority queue with natural ordering.
     * Elements must implement Comparable.
     */
    public PriorityQueueImplementation() {
        this.comparator = null;
    }

    /**
     * Constructs an empty priority queue with the specified comparator.
     *
     * @param comparator the comparator to determine priority
     */
    public PriorityQueueImplementation(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Adds an element to the priority queue.
     *
     * @param e the element to add
     * @throws NullPointerException if the element is null
     */
    public void add(E e) {
        if (e == null) throw new NullPointerException("Null elements not allowed");
        heap.add(e);
        siftUp(heap.size() - 1);
    }

    /**
     * Retrieves, but does not remove, the element with highest priority.
     *
     * @return the top element
     * @throws NoSuchElementException if the queue is empty
     */
    public E peek() {
        if (heap.isEmpty()) throw new NoSuchElementException("Queue is empty");
        return heap.get(0);
    }

    /**
     * Removes and returns the element with highest priority.
     *
     * @return the removed element
     * @throws NoSuchElementException if the queue is empty
     */
    public E poll() {
        if (heap.isEmpty()) throw new NoSuchElementException("Queue is empty");
        E result = heap.get(0);
        E last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return result;
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the size of the queue
     */
    public int size() {
        return heap.size();
    }

    /**
     * Returns {@code true} if the queue contains no elements.
     *
     * @return {@code true} if the queue is empty
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Removes all elements from the queue.
     */
    public void clear() {
        heap.clear();
    }

    // Internal helper methods

    private void siftUp(int index) {
        E value = heap.get(index);
        while (index > 0) {
            int parent = (index - 1) / 2;
            E parentValue = heap.get(parent);
            if (compare(value, parentValue) >= 0) break;
            heap.set(index, parentValue);
            index = parent;
        }
        heap.set(index, value);
    }

    private void siftDown(int index) {
        int size = heap.size();
        E value = heap.get(index);
        while (index < size / 2) {
            int left = 2 * index + 1;
            int right = left + 1;
            int smallest = left;

            if (right < size && compare(heap.get(right), heap.get(left)) < 0) {
                smallest = right;
            }

            if (compare(value, heap.get(smallest)) <= 0) break;

            heap.set(index, heap.get(smallest));
            index = smallest;
        }
        heap.set(index, value);
    }

    @SuppressWarnings("unchecked")
    private int compare(E a, E b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return ((Comparable<? super E>) a).compareTo(b);
    }
}
