package ru.lember.neointegrationadapter.java;

public interface TriFunction<F, S, T, R> {

	R apply(final F first, final S second, final T third);
}
