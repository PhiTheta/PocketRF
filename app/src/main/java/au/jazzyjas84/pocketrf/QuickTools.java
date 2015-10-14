package au.jazzyjas84.pocketrf;

import java.math.BigDecimal;

/**
 * Place to store useful reusable code.
 */
public class QuickTools {

    //---- roundDigits ----//
    /**
     * rounds a floating point number to a specifed number of places, using half-up method
     * @param x  the number to be rounded
     * @param places  the number of places to round to
     * @return float x rounded to n decimal places
     */
    public static float roundDigits(float x, int places){

        BigDecimal bd = new BigDecimal(x);
        bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
