
import java.util.NoSuchElementException
import java.util.ArrayList



/**
 * An implementation of a priority queue backed by a Fibonacci heap,
 * as described by Fredman and Tarjan.  Fibonacci heaps are interesting
 * theoretically because they have asymptotically good runtime guarantees
 * for many operations.  In particular, insert, peek, and decrease-key all
 * run in amortized O(1) time.  dequeueMin and delete each run in amortized
 * O(lg n) time.  This allows algorithms that rely heavily on decrease-key
 * to gain significant performance boosts.  For example, Dijkstra's algorithm
 * for single-source shortest paths can be shown to run in O(m + n lg n) using
 * a Fibonacci heap, compared to O(m lg n) using a standard binary or binomial
 * heap.
 *
 * @author Vasyl Antoniuk (@toniukan).
 * @since 0.1
 * @param E the type of the elements which the FibonacciHeap will contain
 */

class FibonacciHeap<E> : AbstractHeap<E> {
    /* In order for all of the Fibonacci heap operations to complete in O(1),
     * clients need to have O(1) access to any element in the heap.  We make
     * this work by having each insertion operation produce a handle to the
     * node in the tree.  In actuality, this handle is the node itself, but
     * we guard against external modification by marking the internal fields
     * private.
     */
    class Entry<E> (elem: E, priority: Double)  {
        var mDegree : Int = 0           // Number of children
        var mIsMarked : Boolean = false // Whether this node is marked

        var mNext: Entry<E> = this             // Next and previous elements in the list
        var mPrev: Entry<E> = this

        var mParent: Entry<E>? = null   // Parent in the tree, if any.

        var mChild: Entry<E>? = null    // Child node, if any.

        var mElem : E = elem
        var mPriority : Double = priority

        /**
         * Returns the element represented by this heap entry.

         * @return The element represented by this heap entry.
         */
        fun getValue() = this.mElem
    }

    /* Pointer to the minimum element in the heap. */
    private var mMin: Entry<E>? = null

    /* Cached size of the heap, so we don't have to recompute this explicitly. */
    override var size = 0

    /**
     * Inserts the specified element into the Fibonacci heap with the specified
     * priority.  Its priority must be a valid double, so you cannot set the
     * priority to NaN.

     * @param value The value to insert.
     * *
     * @param priority Its priority, which must be valid.
     * *
     * @return An Entry representing that element in the tree.
     */
    fun enqueue(value: E, priority: Double): Entry<E> {
        checkPriority(priority)

        /* Create the entry object, which is a circularly-linked list of length
         * one.
         */
        val result = Entry(value, priority)

        /* Merge this singleton list with the tree list. */
        mMin = mergeLists(mMin, result)

        /* Increase the size of the heap; we just added something. */
        ++size

        /* Return the reference to the new element. */
        return result
    }

    override fun add(element: E) {
        enqueue(element, .0)
    }

    /**
     * Returns an Entry object corresponding to the minimum element of the
     * Fibonacci heap, throwing a NoSuchElementException if the heap is
     * empty.

     * @return The smallest element of the heap.
     * *
     * @throws NoSuchElementException If the heap is empty.
     */
    override fun peek(): E {
        if (isEmpty())
            throw NoSuchElementException("Heap is empty.")
        return mMin!!.getValue()
    }

    /**
     * Returns whether the heap is empty.

     * @return Whether the heap is empty.
     */
    override fun isEmpty(): Boolean {
        return mMin == null
    }

    /**
     * Returns the number of elements in the heap.

     * @return The number of elements in the heap.
     */
    fun size() = size

    /**
     * Given two Fibonacci heaps, returns a new Fibonacci heap that contains
     * all of the elements of the two heaps.  Each of the input heaps is
     * destructively modified by having all its elements removed.  You can
     * continue to use those heaps, but be aware that they will be empty
     * after this call completes.

     * @param one The first Fibonacci heap to merge.
     * *
     * @param two The second Fibonacci heap to merge.
     * *
     * @return A new FibonacciHeap containing all of the elements of both heaps.
     */
    fun merge(one: FibonacciHeap<E>, two: FibonacciHeap<E>): FibonacciHeap<E> {
        /* Create a new FibonacciHeap to hold the result. */
        val result = FibonacciHeap<E>()

        /* Merge the two Fibonacci heap root lists together.  This helper function
         * also computes the min of the two lists, so we can store the result in
         * the mMin field of the new heap.
         */
        result.mMin = mergeLists(one.mMin, two.mMin)

        /* The size of the new heap is the sum of the sizes of the input heaps. */
        result.size = one.size + two.size

        /* Clear the old heaps. */
        two.size = 0
        one.size = two.size
        one.mMin = null
        two.mMin = null

        /* Return the newly-merged heap. */
        return result
    }

    /**
     * Dequeues and returns the minimum element of the Fibonacci heap.  If the
     * heap is empty, this throws a NoSuchElementException.

     * @return The smallest element of the Fibonacci heap.
     * *
     * @throws NoSuchElementException If the heap is empty.
     */
    override fun remove(): E {
        /* Check for whether we're empty. */
        if (isEmpty())
            throw NoSuchElementException("Heap is empty.")

        /* Otherwise, we're about to lose an element, so decrement the number of
         * entries in this heap.
         */
        --size

        /* Grab the minimum element so we know what to return. */
        val minElem = mMin

        /* Now, we need to get rid of this element from the list of roots.  There
         * are two cases to consider.  First, if this is the only element in the
         * list of roots, we set the list of roots to be null by clearing mMin.
         * Otherwise, if it's not null, then we write the elements next to the
         * min element around the min element to remove it, then arbitrarily
         * reassign the min.
         */
        if (mMin!!.mNext === mMin) { // Case one
            mMin = null
        } else { // Case two
            mMin!!.mPrev.mNext = mMin!!.mNext
            mMin!!.mNext.mPrev = mMin!!.mPrev
            mMin = mMin?.mNext // Arbitrary element of the root list.
        }

        /* Next, clear the parent fields of all of the min element's children,
         * since they're about to become roots.  Because the elements are
         * stored in a circular list, the traversal is a bit complex.
         */
        if (minElem?.mChild != null) {
            /* Keep track of the first visited node. */
            var curr = minElem.mChild
            do {
                curr?.mParent = null

                /* Walk to the next node, then stop if this is the node we
                 * started at.
                 */
                curr = curr?.mNext
            } while (curr !== minElem.mChild)
        }

        /* Next, splice the children of the root node into the topmost list,
         * then set mMin to point somewhere in that list.
         */
        mMin = mergeLists(mMin, minElem?.mChild)

        /* If there are no entries left, we're done. */
        if (mMin == null) return minElem!!.getValue()

        /* Next, we need to coalsce all of the roots so that there is only one
         * tree of each degree.  To track trees of each size, we allocate an
         * ArrayList where the entry at position i is either null or the
         * unique tree of degree i.
         */
        val treeTable = ArrayList<Entry<E>?>()

        /* We need to traverse the entire list, but since we're going to be
         * messing around with it we have to be careful not to break our
         * traversal order mid-stream.  One major challenge is how to detect
         * whether we're visiting the same node twice.  To do this, we'll
         * spent a bit of overhead adding all of the nodes to a list, and
         * then will visit each element of this list in order.
         */
        val toVisit = ArrayList<Entry<E>?>()

        /* To add everything, we'll iterate across the elements until we
         * find the first element twice.  We check this by looping while the
         * list is empty or while the current element isn't the first element
         * of that list.
         */
        var curr = mMin
        while (toVisit.isEmpty() || toVisit[0] !== curr) {
            toVisit.add(curr)
            curr = curr?.mNext
        }

        /* Traverse this list and perform the appropriate unioning steps. */
        for (tmp in toVisit) {
            /* Keep merging until a match arises. */
            var curr = tmp
            while (true) {
                /* Ensure that the list is long enough to hold an element of this
                 * degree.
                 */
                while (curr!!.mDegree >= treeTable.size)
                    treeTable.add(null)

                /* If nothing's here, we're can record that this tree has this size
                 * and are done processing.
                 */
                if (treeTable[curr.mDegree] == null) {
                    treeTable[curr.mDegree] = curr
                    break
                }

                /* Otherwise, merge with what's there. */
                val other = treeTable[curr.mDegree]
                treeTable.set(curr.mDegree, null) // Clear the slot

                /* Determine which of the two trees has the smaller root, storing
                 * the two tree accordingly.
                 */
                val min = if (other!!.mPriority < curr.mPriority) other else curr
                val max = if (other.mPriority < curr.mPriority) curr else other

                /* Break max out of the root list, then merge it into min's child
                 * list.
                 */
                max.mNext.mPrev = max.mPrev
                max.mPrev.mNext = max.mNext

                /* Make it a singleton so that we can merge it. */
                max.mPrev = max
                max.mNext = max.mPrev
                min.mChild = mergeLists(min.mChild, max)

                /* Reparent max appropriately. */
                max.mParent = min

                /* Clear max's mark, since it can now lose another child. */
                max.mIsMarked = false

                /* Increase min's degree; it now has another child. */
                ++min.mDegree

                /* Continue merging this tree. */
                curr = min
            }

            /* Update the global min based on this node.  Note that we compare
             * for <= instead of < here.  That's because if we just did a
             * reparent operation that merged two different trees of equal
             * priority, we need to make sure that the min pointer points to
             * the root-level one.
             */
            if (curr!!.mPriority <= mMin!!.mPriority) mMin = curr
        }
        return minElem!!.getValue()
    }

    /**
     * Decreases the key of the specified element to the new priority.  If the
     * new priority is greater than the old priority, this function throws an
     * IllegalArgumentException.  The new priority must be a finite double,
     * so you cannot set the priority to be NaN, or +/- infinity.  Doing
     * so also throws an IllegalArgumentException.

     * It is assumed that the entry belongs in this heap.  For efficiency
     * reasons, this is not checked at runtime.

     * @param entry The element whose priority should be decreased.
     * *
     * @param newPriority The new priority to associate with this entry.
     * *
     * @throws IllegalArgumentException If the new priority exceeds the old
     * *         priority, or if the argument is not a finite double.
     */
    fun decreaseKey(entry: Entry<E>, newPriority: Double) {
        checkPriority(newPriority)
        if (newPriority > entry.mPriority)
            throw IllegalArgumentException("New priority exceeds old.")

        /* Forward this to a helper function. */
        decreaseKeyUnchecked(entry, newPriority)
    }

    /**
     * Utility function which, given a user-specified priority, checks whether
     * it's a valid double and throws an IllegalArgumentException otherwise.

     * @param priority The user's specified priority.
     * *
     * @throws IllegalArgumentException If it is not valid.
     */
    private fun checkPriority(priority: Double) {
        if (Double.NaN == priority)
            throw IllegalArgumentException(priority.toString() + " is invalid.")
    }

    /**
     * Utility function which, given two pointers into disjoint circularly-
     * linked lists, merges the two lists together into one circularly-linked
     * list in O(1) time.  Because the lists may be empty, the return value
     * is the only pointer that's guaranteed to be to an element of the
     * resulting list.

     * This function assumes that one and two are the minimum elements of the
     * lists they are in, and returns a pointer to whichever is smaller.  If
     * this condition does not hold, the return value is some arbitrary pointer
     * into the doubly-linked list.

     * @param one A pointer into one of the two linked lists.
     * *
     * @param two A pointer into the other of the two linked lists.
     * *
     * @return A pointer to the smallest element of the resulting list.
     */
    private fun <E> mergeLists(one: Entry<E>?, two: Entry<E>?): Entry<E>? {
        /* There are four cases depending on whether the lists are null or not.
         * We consider each separately.
         */
        if (one == null && two == null) { // Both null, resulting list is null.
            return null
        } else if (one != null && two == null) { // Two is null, result is one.
            return one
        } else if (one == null && two != null) { // One is null, result is two.
            return two
        } else { // Both non-null; actually do the splice.
            /* This is actually not as easy as it seems.  The idea is that we'll
             * have two lists that look like this:
             *
             * +----+     +----+     +----+
             * |    |--N->|one |--N->|    |
             * |    |<-P--|    |<-P--|    |
             * +----+     +----+     +----+
             *
             *
             * +----+     +----+     +----+
             * |    |--N->|two |--N->|    |
             * |    |<-P--|    |<-P--|    |
             * +----+     +----+     +----+
             *
             * And we want to relink everything to get
             *
             * +----+     +----+     +----+---+
             * |    |--N->|one |     |    |   |
             * |    |<-P--|    |     |    |<+ |
             * +----+     +----+<-\  +----+ | |
             *                  \  P        | |
             *                   N  \       N |
             * +----+     +----+  \->+----+ | |
             * |    |--N->|two |     |    | | |
             * |    |<-P--|    |     |    | | P
             * +----+     +----+     +----+ | |
             *              ^ |             | |
             *              | +-------------+ |
             *              +-----------------+
             *
             */
            val oneNext = one!!.mNext // Cache this since we're about to overwrite it.
            one.mNext = two!!.mNext
            one.mNext.mPrev = one
            two.mNext = oneNext
            two.mNext.mPrev = two

            /* Return a pointer to whichever's smaller. */
            return if (one.mPriority < two.mPriority) one else two
        }
    }

    /**
     * Decreases the key of a node in the tree without doing any checking to ensure
     * that the new priority is valid.

     * @param entry The node whose key should be decreased.
     * *
     * @param priority The node's new priority.
     */
    private fun decreaseKeyUnchecked(entry: Entry<E>, priority: Double) {
        /* First, change the node's priority. */
        entry.mPriority = priority

        /* If the node no longer has a higher priority than its parent, cut it.
         * Note that this also means that if we try to run a delete operation
         * that decreases the key to -infinity, it's guaranteed to cut the node
         * from its parent.
         */
        if (entry.mParent != null && entry.mPriority <= entry.mParent!!.mPriority)
            cutNode(entry)

        /* If our new value is the new min, mark it as such.  Note that if we
         * ended up decreasing the key in a way that ties the current minimum
         * priority, this will change the min accordingly.
         */
        if (entry.mPriority <= mMin!!.mPriority)
            mMin = entry
    }

    /**
     * Cuts a node from its parent.  If the parent was already marked, recursively
     * cuts that node from its parent as well.

     * @param entry The node to cut from its parent.
     */
    private fun cutNode(entry: Entry<E>) {
        /* Begin by clearing the node's mark, since we just cut it. */
        entry.mIsMarked = false

        /* Base case: If the node has no parent, we're done. */
        if (entry.mParent == null) return

        /* Rewire the node's siblings around it, if it has any siblings. */
        if (entry.mNext !== entry) { // Has siblings
            entry.mNext.mPrev = entry.mPrev
            entry.mPrev.mNext = entry.mNext
        }

        /* If the node is the one identified by its parent as its child,
         * we need to rewrite that pointer to point to some arbitrary other
         * child.
         */
        if (entry.mParent!!.mChild === entry) {
            /* If there are any other children, pick one of them arbitrarily. */
            if (entry.mNext !== entry) {
                entry.mParent!!.mChild = entry.mNext
            } else {
                entry.mParent!!.mChild = null
            }/* Otherwise, there aren't any children left and we should clear the
             * pointer and drop the node's degree.
             */
        }

        /* Decrease the degree of the parent, since it just lost a child. */
        --entry.mParent!!.mDegree

        /* Splice this tree into the root list by converting it to a singleton
         * and invoking the merge subroutine.
         */
        entry.mNext = entry
        entry.mPrev = entry.mNext
        mMin = mergeLists(mMin, entry)

        /* Mark the parent and recursively cut it if it's already been
         * marked.
         */
        if (entry.mParent!!.mIsMarked)
            cutNode(entry.mParent!!)
        else
            entry.mParent!!.mIsMarked = true

        /* Clear the relocated node's parent; it's now a root. */
        entry.mParent = null
    }

    override fun clear() {
        size = 0
        mMin = null
    }
}