package com.johnmartin.social.utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.NonNull;

public class ExtractorUtils {

    private ExtractorUtils() {
    }

    /**
     * Extracts values into a list using the given mapper function.
     *
     * @param <T>
     *            the source type
     * @param <R>
     *            the result type
     * @param items
     *            the list of items
     * @param mapper
     *            function to extract values
     * @return list of extracted values
     */
    public static <T, R> List<R> extractToList(@NonNull List<T> items,
                                               @NonNull Function<? super T, ? extends R> mapper) {
        return items.stream().map(mapper).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Extracts values into a set using the given mapper function.
     *
     * @param <T>
     *            the source type
     * @param <R>
     *            the result type
     * @param items
     *            the list of items
     * @param mapper
     *            function to extract values
     * @return set of extracted values
     */
    public static <T, R> Set<R> extractToSet(@NonNull List<T> items, @NonNull Function<? super T, ? extends R> mapper) {
        return items.stream().map(mapper).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * Extracts values into a map using the given key and value mapper functions.
     *
     * @param <T>
     *            the source type
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param items
     *            the list of items
     * @param keyMapper
     *            function to extract keys
     * @param valueMapper
     *            function to extract values
     * @return map of extracted key-value pairs
     */
    public static <T, K, V> Map<K, V> extractToMap(@NonNull List<T> items,
                                                   @NonNull Function<? super T, ? extends K> keyMapper,
                                                   @NonNull Function<? super T, ? extends V> valueMapper) {
        return items.stream().filter(Objects::nonNull).collect(Collectors.toMap(keyMapper, valueMapper));
    }
}
