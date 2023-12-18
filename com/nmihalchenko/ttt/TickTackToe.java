package com.nmihalchenko.ttt;

import java.util.*;

public class TickTackToe {
    public static final int ROW_COUNT = 3;
    public static final int COL_COUNT = 3;
    private final List<List<Mark>> field = new ArrayList<>();

    public TickTackToe() {
        for (int i = 0; i < ROW_COUNT; i++) {
            var row = new ArrayList<Mark>();
            for (int j = 0; j < COL_COUNT; j++) {
                row.add(null);
            }
            field.add(row);
        }
    }

    public Optional<Mark> get(int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= field.size() || colIndex < 0 || colIndex >= field.get(0).size()) {
            throw new IllegalArgumentException("Index out of range");
        }

        return Optional.ofNullable(field.get(rowIndex).get(colIndex));
    }

    public boolean put(int rowIndex, int colIndex, Mark mark) {
        if (rowIndex < 0 || rowIndex >= field.size() || colIndex < 0 || colIndex >= field.get(0).size()) {
            throw new IllegalArgumentException("Index out of range");
        }
        if (mark == null) {
            throw new IllegalArgumentException("Mark may not be null");
        }

        boolean result = false;

        if (get(rowIndex, colIndex).isEmpty()) {
            field.get(rowIndex).set(colIndex, mark);
            result = true;
        }

        return result;
    }

    public Optional<Mark> getWinner() {
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COL_COUNT; j++) {
                if (i == j && i == 1) {
                    continue;
                }

                var currentMark = get(i, j).orElseThrow();
                var centerMark = get(1, 1).orElseThrow();
                var oppositeMark = get(mapToOpposite(i), mapToOpposite(j)).orElseThrow();

                if (currentMark.equals(centerMark) && currentMark.equals(oppositeMark)) {
                    return Optional.of(currentMark);
                }
            }
        }

        return Optional.empty();
    }

    private static int mapToOpposite(int coord) {
        return (-(coord - 1)) + 1;
    }
}
