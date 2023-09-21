package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.trie.TrieMap;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ArrayStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private ListNode front;

    public class ListNode {
        public K key;
        public V value;
        public ListNode next;

        public ListNode(K key, V value) {
            this(key, value, null);
        }

        public ListNode(K key, V value, ListNode next) {
            this.key = key;
            this.next = next;
            this.value = value;
        }
    }

    @Override
    public V insert(K key, V value) {
        if (front == null) { // empty
            front = new ListNode(key, value, front);
            size++;
            return null;
        } else {
            if (front.key.equals(key)) { // front case
                V oldValue = front.value;
                front.value = value;
                return oldValue;
            } else {
                ListNode curr = front;
                while (curr.next != null) {
                    if (curr.next.key.equals(key)) {
                        ListNode moveToFront = curr.next;
                        curr.next = curr.next.next;
                        moveToFront.next = front;
                        V oldValue = front.value;
                        front.value = value;
                        return oldValue;
                    }
                    curr = curr.next;
                }

                // not found
                front = new ListNode(key, value, front);
                size++;
                return null;
            }
        }
    }

    @Override
    public V find(K key) {
        if (front == null) {
            return null;
        } else {
            if (front.key.equals(key)) {
                return front.value;
            } else {
                ListNode curr = front;
                while (curr.next != null) {
                    if (curr.next.key.equals(key)) {
                        ListNode moveToFront = curr.next;
                        curr.next = curr.next.next;
                        moveToFront.next = front;
                        front = moveToFront;
                        return front.value;
                    }
                    curr = curr.next;
                }
                return null;
            }
        }
    }


    @Override
    public Iterator<Item<K, V>> iterator() {
        return new FrontIterator();
    }

    private class FrontIterator extends SimpleIterator<Item<K, V>> {
        private MoveToFrontList.ListNode curr;

        public FrontIterator() {
            if (MoveToFrontList.this.front != null) {
                this.curr = MoveToFrontList.this.front;
            }
        }

        @Override
        public boolean hasNext() {
            return this.curr != null;
        }

        @Override
        public Item<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            K key = (K)this.curr.key;
            V value = (V)this.curr.value;
            Item<K, V> toReturn = new Item<K, V>(key, value);
            this.curr = this.curr.next;

            return toReturn;
        }
    }
}
