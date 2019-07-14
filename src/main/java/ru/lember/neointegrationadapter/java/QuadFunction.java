package ru.lember.neointegrationadapter.java;

public interface QuadFunction<F, S, T, T2, R> {

	R apply(final F first, final S second, final T third, final T2 fourth);
}
