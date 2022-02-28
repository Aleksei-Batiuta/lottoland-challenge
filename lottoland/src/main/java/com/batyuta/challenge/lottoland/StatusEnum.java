package com.batyuta.challenge.lottoland;

import lombok.Getter;

/**
 * The status enums.
 *
 * @author Aleksei Batyuta aleksei.batiuta@gmail.com
 */
@Getter
public enum StatusEnum {
    /**
     * the great flag.
     */
    GREAT(Const.GREAT),
    /**
     * the equals flag.
     */
    EQUALS(Const.EQUALS),
    /**
     * the less flag.
     */
    LESS(Const.LESS);

    /**
     * the flag value.
     */
    private final int status;

    /**
     * Default constructor.
     *
     * @param statusValue status value.
     */
    StatusEnum(final int statusValue) {
        this.status = statusValue;
    }

    /**
     * Resolves the status enum by its value.
     *
     * @param status status value.
     * @return status enum.
     * @throws IllegalArgumentException if enum was not found by status value
     */
    public static StatusEnum valueOf(final int status) {
        switch (status) {
            case Const.GREAT:
                return GREAT;
            case Const.EQUALS:
                return EQUALS;
            case Const.LESS:
                return LESS;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * The constant helper.
     */
    public static class Const {
        /**
         * a great value.
         */
        public static final int GREAT = 1;
        /**
         * a equals value.
         */
        public static final int EQUALS = 0;
        /**
         * a less value.
         */
        public static final int LESS = -1;
    }
}
