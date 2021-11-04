package com.example.utils;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
public interface CollectionUtils {

    static <E> void removeNull(Collection<E> collection) {
        if (Objects.nonNull(collection)) {
            collection.removeIf(Objects::isNull);
        }
    }
}
