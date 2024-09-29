package me.a8kj.ww.parent.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtils {

    public static <Element> Element pickRandomElement(@NonNull List<Element> elements) {
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("Elements list cannot be empty!");
        }
        final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        int randomElementIndex = threadLocalRandom.nextInt(elements.size());
        
        return elements.get(randomElementIndex);
    }

}
