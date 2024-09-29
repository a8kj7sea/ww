package me.a8kj.ww.parent.entity.schedule.helper;

import me.a8kj.ww.parent.regex.RegexScanner;

public class TimeMatcher extends RegexScanner {

    public TimeMatcher(String text) {
        super("^(?:[0-9]|1[0-9]|2[0-3])(?::[0-5][0-9])?$", text);
    }
}
