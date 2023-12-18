package com.nmihalchenko.ttt;

public class Mark {
    public static final Mark X = new Mark(1);
    public static final Mark O = new Mark(-1);
    private final int kind;

    private Mark(int kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        if (equals(X)) {
            return "X";
        } else {
            return "O";
        }
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(kind);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Mark && ((Mark)obj).kind == kind;
    }
}
