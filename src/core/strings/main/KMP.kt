
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
    private var radix: Int = 256
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
        val patternLength = pat.length
        dfa = Array(radix) { IntArray(patternLength) }
        dfa[pat[0].toInt()][0] = 1
        var lastState = 0
        var i = 1
        while (i < patternLength) {
            for (j in 0..radix - 1)
                dfa[j][i] = dfa[j][lastState]           // Copy mismatch cases.
            dfa[pat[i].toInt()][i] = i + 1              // Set match case.
            lastState = dfa[pat[i].toInt()][lastState]  // Update restart state.
            i++
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
        var textIndex: Int = 0
        var patternIndex: Int = 0
        while (textIndex < txt.length && patternIndex < pattern.length) {
            patternIndex = dfa[txt[textIndex].toInt()][patternIndex]
            textIndex++
        }
        if (patternIndex == pattern.length) return textIndex - pattern.length       // found
        return -1                                                                   // not found
    }

    /**
     * Returns the index of the first occurrence of the pattern string
     * in the text string.

     * @param  text the text string
     * *
     * @return the index of the first occurrence of the pattern string
     * *         in the text string; -1 if no such match
     */
    companion object StringExtension {
        fun String.KMPSearchIn(text: String): Int {
            val kmp = KMP(this)
            return kmp.search(text)
        }
    }
}