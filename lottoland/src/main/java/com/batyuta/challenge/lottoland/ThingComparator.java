package com.batyuta.challenge.lottoland;

import java.util.Comparator;

import static com.batyuta.challenge.lottoland.StatusEnum.Const.EQUALS;
import static com.batyuta.challenge.lottoland.StatusEnum.Const.LESS;

/**
 * Things comparator.
 *
 * @author Aleksei Batiuta
 */
public class ThingComparator implements Comparator<ThingEnum> {

    /**
     * Compares two things.
     *
     * @param o1 the first thing
     * @param o2 the second thing
     * @return {@link StatusEnum.Const#EQUALS} if equals,
     * {@link StatusEnum.Const#LESS} if less and
     * {@link StatusEnum.Const#GREAT} if this is great of compared value
     */
    @Override
    public int compare(final ThingEnum o1, final ThingEnum o2) {
        if (o1 != null) {
            // the first is NOT null
            return o1.compareToEnum(o2);
        } else {
            if (o2 == null) {
                // both are null
                return EQUALS;
            } else {
                // the first is null but the second in NOT null
                return LESS;
            }
        }
    }
}
