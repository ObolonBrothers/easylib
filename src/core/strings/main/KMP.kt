
/**
 *  The KMP class finds the first occurrence of a pattern string in a text string.
 *
 *  This implementation uses a version of the Knuth-Morris-Pratt substring search
 *  algorithm. The version takes time as space proportional to
 *  N + M R in the worst case, where N is the length of the text string,
 *  M is the length of the pattern, and R is the alphabet size.

 * @author Vasyl Antoniuk (@toniukan).
 * @since 0.1
 */

class KMP {
    private var R: Int = 256                        // the radix
    private var dfa: Array<IntArray>                // the KMP automoton

    private var pattern: String                     // pattern string

    /**
     * Preprocesses the pattern string.

     * @param pat the pattern string
     */
    constructor(pat: String) {
        if (pat.isEmpty())
            throw IllegalArgumentException("The parameter should be a non-empty string")
        pattern = pat

        // build DFA from pattern
        val m = pat.length
        dfa = Array(R) { IntArray(m) }
        dfa[pat[0].toInt()][0] = 1
        var x = 0
        var j = 1
        while (j < m) {
            for (c in 0..R - 1)
                dfa[c][j] = dfa[c][x]           // Copy mismatch cases.
            dfa[pat[j].toInt()][j] = j + 1      // Set match case.
            x = dfa[pat[j].toInt()][x]          // Update restart state.
            j++
        }
    }

    /**
     * Returns the index of the first occurrence of the pattern string
     * in the text string.

     * @param  txt the text string
     * *
     * @return the index of the first occurrence of the pattern string
     * *         in the text string; -1 if no such match
     */
    fun search(txt: String): Int {

        // simulate operation of DFA on text
        val m = pattern.length
        val n = txt.length
        var i: Int = 0
        var j: Int = 0
        while (i < n && j < m) {
            j = dfa[txt[i].toInt()][j]
            i++
        }
        if (j == m) return i - m    // found
        return -1                   // not found
    }
}