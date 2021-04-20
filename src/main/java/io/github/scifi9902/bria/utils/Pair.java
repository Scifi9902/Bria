package io.github.scifi9902.bria.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Pair<K, V> {

    private K key;

    private V value;

}
