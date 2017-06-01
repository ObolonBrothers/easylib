@file:Suppress("DEPRECATED_IDENTITY_EQUALS")
@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")

/**
 * Hashing table in Kotlin implementation.
 * For current version (0.2) remove operation is unsupported.
 * Will enable it in next versions.
 *
 * @author is Alex Syrotenko (@flystyle)
 * @since 0.2
 * @see Entry
 * @see MutableMap
 * Created on 06.05.17.
 */

class HashMap<K, V> : MutableMap<K, V> {

    /**
     * Entry in hash table entity implementation.
     * It needs for saving the key-value pair in table.
     *
     * @author is Alex Syrotenko (@flystyle)
     * @since 0.2
     * @see HashMap
     * @see MutableMap
     * Created on 06.05.17.
     */

    open class Entry<K, V>
    (override val key : K, override var value: V, val next : Entry<K, V>?, val hash : Int) : MutableMap.MutableEntry<K,V> {

        init {
            var modCount = 0
        }

        override fun setValue(newValue: V) : V {
            value = newValue
            return newValue
        }

        override fun toString(): String {
            return "($key = $value)"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Entry<*, *>) return false

            if (key != other.key) return false
            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            var result = key?.hashCode() ?: 0
            result = 31 * result + (value?.hashCode() ?: 0)
            return result
        }
    }

    private var hashingKey = -1
    private val loadFactor = 0.8f
    private val startCapacity = 128
    private var capacity = startCapacity

    // Hash table
    private var array: Array<Entry<K, V>?> = arrayOfNulls(startCapacity)

    override var size: Int = 0

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> = HashSet()

    override val keys: MutableSet<K> = HashSet()

    override val values: MutableCollection<V> = ArrayList()

    override fun clear() {
        array.fill(null)
        this.size = 0
    }

    /**
     * It put the associated by the key value to the map.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.2
     * @param key identifier for map
     * @param value valuable data object
     * @see HashMap
     */

    override fun put(key : K, value : V) : V {
        val hash = hash(key!!.hashCode())
        var i = indexFor(hash, array.size)
        var entry = array[i]
        while (entry != null) {
            var k  = entry.key
            if (hash(entry.hashCode()) == hash && key === k) {
              val oldValue = entry.value
              entry.setValue(value)
              return oldValue
            }
            entry = entry.next
        }

        addEntry(hash, key, value, i)
        keys.add(key)
        values.add(value)
        return value
    }

    /**
     *
     * Adds the entry by hash to entry set and table.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.2
     * @param key identifier for map
     * @param value valuable data object
     * @see HashMap
     */

    private fun addEntry(hash : Int, key : K, value : V, bucketIndex : Int) {
        val entry = array[bucketIndex];
        array[bucketIndex] = Entry(key, value, entry, hash)
        if (size++ >= loadFactor * capacity)
            increase()
    }

    /**
     * Method that increase size of entries array.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.2
     * @see HashMap
     */

    private fun increase() {
        val temp = this.array

        val length = getLargerSize(array.size)
        this.capacity *= 2
        this.array = arrayOfNulls(this.capacity)

        for (p in temp)
            if (p != null)
                this.put(p.key, p.value)
    }

    /**
     * Method that decrease size of entries array.
     * Will be use while remove() will works.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.3
     * @see HashMap
     */

    private fun decrease() {
        val temp = this.array

        val length = getSmallerSize(array.size)
        this.array = arrayOfNulls(length)

        temp.filterNotNull()
            .forEach { this.put(it.key, it.value) }
    }

    /**
     * Method that doubles the input.
     * It`s for creating new doubled array of entries.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.2
     * @see HashMap
     */
    private fun getLargerSize(input: Int): Int {
        return input shl 1
    }

    /**
     * Method that divide size of table by two through the following input.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.2
     * @see HashMap
     */

    private fun getSmallerSize(input: Int): Int {
        return input shr 1 shr 1
    }


    /**
     * The hashing function. Converts the key into an integer.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.2
     * @param int to create a hash for.
     * @return Integer which represents the key.
     */

    private fun hash(int: Int): Int {
        var h = int

        h = h xor (h.ushr(20) xor h.ushr(12))
        return h xor h.ushr(7) xor h.ushr(4)
    }

    override fun putAll(from: Map<out K, V>) {
        from.keys.map { key -> this.put(key, from[key]!!) }
    }

    /**
     * Unsupported operation for actual version.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.2
     * @param key is needed to detect which element will be removed.
     * @return value with nullable type.
     */

    override fun remove(key: K): V? {
        throw UnsupportedOperationException()
    }

    override fun containsKey(key: K): Boolean {
        return keys.contains(key)
    }

    override fun containsValue(value: V): Boolean {
        return values.contains(value)
    }

    /**
     * @return index of element by it`s hash.
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.2
     * @see HashMap
     */

    private fun indexFor(h: Int, length: Int): Int {
        return h and length - 1
    }

    /**
     * Getter of value by key. Also supports [] operator.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.2
     * @param key is needed to detect which element will be chosen.
     * @return value with nullable type.
     */

    override operator fun get(key: K): V? {
        val hash = hash(key!!.hashCode())
        var i = indexFor(hash, array.size)
        var entry = array[i]
        while (entry != null) {
            var k  = entry.key
            if (entry.hash == hash && key == k)
                return entry.value

            entry = entry.next
        }
        return null
    }

    override fun isEmpty(): Boolean {
        return size === 0
    }
}