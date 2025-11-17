package com.rantomah.boilerplate.core.util;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ListUtils {

    public static <T> List<T> toList(Object obj, Class<T> clazz) {
        if (!(obj instanceof List)) {
            throw new IllegalArgumentException("The object passed as a parameter is not a List");
        }
        List<?> rawList = (List) obj;
        return rawList.stream().filter(clazz::isInstance).map(clazz::cast).toList();
    }
}
