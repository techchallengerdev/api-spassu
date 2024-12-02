package com.br.spassu.api.application.mapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseMapper {
    protected <T, S> List<T> mapList(Collection<S> collection, Function<S, T> mapper) {
        if (collection == null) {
            return new ArrayList<>();
        }
        return collection.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    protected <T> Set<T> mapSet(Collection<Optional<T>> collection, Function<T, T> mapper) {
        if (collection == null) {
            return new HashSet<>();
        }
        return collection.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(mapper)
                .collect(Collectors.toSet());
    }

    protected <K, V, T, S> Map<K, V> mapMap(Map<T, S> map, Function<T, K> keyMapper, Function<S, V> valueMapper) {
        if (map == null) {
            return new HashMap<>();
        }
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> keyMapper.apply(e.getKey()),
                        e -> valueMapper.apply(e.getValue())
                ));
    }
}