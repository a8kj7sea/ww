package me.a8kj.ww.parent.entity.schedule.helper;

import me.a8kj.ww.parent.regex.RegexScanner;

/**
 * TimeMatcher is a specialized class that checks if a given text matches a time
 * format
 * in the range of 00:00 to 23:59. It extends the {@link RegexScanner} class to
 * perform the matching based on a regular expression.
 * 
 * <p>
 * The accepted time format is either:
 * </p>
 * <ul>
 * <li>Single digit hour (0-9) with optional minutes.</li>
 * <li>Two-digit hour (10-23) with optional minutes.</li>
 * </ul>
 * <p>
 * For example, the following are valid time strings:
 * </p>
 * <ul>
 * <li>5</li>
 * <li>12</li>
 * <li>8:30</li>
 * <li>23:59</li>
 * </ul>
 * 
 * <p>
 * Note: This class does not validate whether the text is a valid or well-formed
 * time;
 * it only checks if it matches the expected format.
 * </p>
 */
public class TimeMatcher extends RegexScanner {

    /**
     * Constructs a new TimeMatcher object with the specified text to match against
     * the
     * time format.
     *
     * @param text the input string to be checked for matching the time pattern
     */
    public TimeMatcher(String text) {
        super("^(?:[0-9]|1[0-9]|2[0-3])(?::[0-5][0-9])?$", text);
    }
}
