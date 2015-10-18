package au.jazzyjas84.pocketrf;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Place to store useful reusable code.
 */
public class QuickTools {
    //---- Limit Types ----//
    /**
     * Specifies the general public average limit for electric fields
     * Used by getLimit()
     */
    public static final int GEN_PUB_AVG_E = 0;

    /**
     * Specifies the general public average limit for magnetic fields
     * Used by getLimit()
     */
    public static final int GEN_PUB_AVG_H = 1;

    /**
     * Specifies the general public instantaneous limit for electric fields
     * Used by getLimit()
     */
    public static final int GEN_PUB_INST_E = 2;

    /**
     * Specifies the general public instantaneous limit for magnetic fields
     * Used by getLimit()
     */
    public static final int GEN_PUB_INST_H = 3;

    /**
     * Specifies the occupational average limit for electric fields
     * Used by getLimit()
     */
    public static final int OCC_AVG_E = 4;

    /**
     * Specifies the occupational average limit for magnetic fields
     * Used by getLimit()
     */
    public static final int OCC_AVG_H = 5;

    /**
     * Specifies the occupational instantaneous limit for electric fields
     * Used by getLimit()
     */
    public static final int OCC_INST_E = 6;

    /**
     * Specifies the occupational instantaneous limit for magnetic fields
     * Used by getLimit()
     */
    public static final int OCC_INST_H = 7;


    private static float freqMhz;
    private static int limitType;

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

    /**
     * rounds a double precision floating point number to a specifed number of places, using half-up method
     * @param x  the number to be rounded
     * @param places  the number of places to round to
     * @return double x rounded to n decimal places
     */
    public static double roundDigits(double x, int places){
        BigDecimal bd = new BigDecimal(x);
        bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /**
     * speed of light in a vacuum, x1,000,000 m/s
     */
    public static float speedOfLight = 299.8f;


    /**
     * Converts a power ratio from dB to linear
     * @param dbPower  the dB value to be converted
     * @return float representing the linear value of dbPower
     */
    public static float dbPowerToLinear(float dbPower){
        double linear = Math.pow(10,dbPower/10);
        return (float)linear;
    }

    /**
     * Returns the applicable RF human exposure limit value.
     * @param freqMhz  the frequency in MHz
     * @param limitType  QuickTools limit type
     * @return  the limit value in V/m for E limit types, and in A/m for H limit types
     */
    public static float getLimit(float freqMhz, int limitType){
        QuickTools.freqMhz = freqMhz;
        QuickTools.limitType = limitType;
        switch (limitType){
            case GEN_PUB_AVG_E:
                return generalPublicAverageLimitE();
            case GEN_PUB_AVG_H:
                return generalPublicAverageLimitH();
            case GEN_PUB_INST_E:
                return generalPublicInstantaneousLimitE();
            case GEN_PUB_INST_H:
                return generalPublicInstantaneousLimitH();
            case OCC_AVG_E:
                return occupationalAverageLimitE();
            case OCC_AVG_H:
                return occupationalAverageLimitH();
            case OCC_INST_E:
                return occupationalInstantaneousLimitE();
            case OCC_INST_H:
                return occupationalInstantaneousLimitH();
        }
        return 0f;
    }

    private static float generalPublicAverageLimitE(){
        // ARPANSA general public average electric field limit (REF: ARPANSA RPS3)
        if(freqMhz < 0.1f){generalPublicInstantaneousLimitE();}
        if(freqMhz < .15f){return 86.8f;}
        if(freqMhz < 1f){ return 86.8f;}
        if(freqMhz < 10f){return (float)(86.8/(Math.pow(freqMhz,0.5)));}
        if(freqMhz < 400f){return 27.4f;}
        if(freqMhz < 2000f){return (float)(1.37*Math.pow(freqMhz,0.5));}
        if(freqMhz < 300000f){return 61.4f;}
        return 0f;
    }

    private static float generalPublicInstantaneousLimitE(){
        // ARPANSA general public instantaneous electric field limit (REF: ARPANSA RPS3)
        if(freqMhz < 0.003f){return 0f;}
        if(freqMhz < 0.1f){return 86.8f;}
        if(freqMhz < 0.15f){ return 488*(float)(Math.pow(freqMhz,0.75));}
        if(freqMhz < 1f){ return 488*(float)(Math.pow(freqMhz,0.75));}
        if(freqMhz < 10f){return 488*(float)(Math.pow(freqMhz,0.25));}
        if(freqMhz < 400f){return 868f;}
        if(freqMhz < 2000f){return (float)(43.4*Math.pow(freqMhz,0.5));}
        if(freqMhz < 300000f){return 1941f;}
        return 0f;
    }

    private static float generalPublicAverageLimitH(){
        // ARPANSA general public average magnetic field limit (REF: ARPANSA RPS3)
        if(freqMhz < 0.1f){return generalPublicInstantaneousLimitH();}
        if(freqMhz < .15f){return 4.86f;}
        if(freqMhz < 1f){ return 0.729f/freqMhz;}
        if(freqMhz < 10f){return 0.729f/freqMhz;}
        if(freqMhz < 400f){return 0.0729f;}
        if(freqMhz < 2000f){return (float)(0.00364*Math.pow(freqMhz,0.5));}
        if(freqMhz < 300000f){return 0.163f;}
        return 0f;
    }

    private static float generalPublicInstantaneousLimitH(){
        // ARPANSA general public instantaneous magnetic field limit (REF: ARPANSA RPS3)
        if(freqMhz < 0.003f){return 0f;}
        if(freqMhz < 0.1f){return 4.86f;}
        if(freqMhz < .15f){return 4.86f;}
        if(freqMhz < 1f){ return (float)(3.47/Math.pow(freqMhz,0.178));}
        if(freqMhz < 10f){return (float)(3.47/Math.pow(freqMhz,0.178));}
        if(freqMhz < 400f){return 2.3f;}
        if(freqMhz < 2000f){return (float)(0.115*Math.pow(freqMhz,0.5));}
        if(freqMhz < 300000f){return 5.15f;}
        return 0f;
    }

    private static float occupationalAverageLimitE(){
        // ARPANSA occupational average electric field limit (REF: ARPANSA RPS3)
        if(freqMhz < 0.1f){return occupationalInstantaneousLimitE();}
        if(freqMhz < 1f){ return 614f;}
        if(freqMhz < 10f){return 614/freqMhz;}
        if(freqMhz < 400f){return 61.4f;}
        if(freqMhz < 2000f){return (float)(3.07*Math.pow(freqMhz,0.5));}
        if(freqMhz < 300000f){return 137f;}
        return 0f;
    }

    private static float occupationalInstantaneousLimitE(){
        // ARPANSA occupational instantaneous electric field limit (REF: ARPANSA RPS3)
        if(freqMhz < 0.003f){return 0f;}
        if(freqMhz < 0.065f){return 614f;}
        if(freqMhz < 0.1f){return 614f;}
        if(freqMhz < 1f){ return 3452*(float)(Math.pow(freqMhz,0.75));}
        if(freqMhz < 10f){return 3452/(float)(Math.pow(freqMhz,0.25));}
        if(freqMhz < 400f){return 1941f;}
        if(freqMhz < 2000f){return 97*(float)(Math.pow(freqMhz,0.5));}
        if(freqMhz < 300000f){return 4340f;}
        return 0f;
    }

    private static float occupationalAverageLimitH(){
        // ARPANSA occupational average magnetic field limit (REF: ARPANSA RPS3)
        if(freqMhz < 0.1f){occupationalInstantaneousLimitH();}
        if(freqMhz < 1f){ return 1.63f/freqMhz;}
        if(freqMhz < 10f){return 1.63f/freqMhz;}
        if(freqMhz < 400f){return 0.163f;}
        if(freqMhz < 2000f){return (float)(0.00814*Math.pow(freqMhz,0.5));}
        if(freqMhz < 300000f){return 0.364f;}
        return 0f;
    }

    private static float occupationalInstantaneousLimitH(){
        // ARPANSA occupational instantaneous magnetic field limit (REF: ARPANSA RPS3)
        if(freqMhz < 0.003f){return 0f;}
        if(freqMhz < 0.065f){return 25f;}
        if(freqMhz < .1f){return 1.63f/freqMhz;}
        if(freqMhz < 1f){ return (float)(9.16/Math.pow(freqMhz,0.25));}
        if(freqMhz < 10f){return (float)(9.16/Math.pow(freqMhz,0.25));}
        if(freqMhz < 400f){return 5.15f;}
        if(freqMhz < 2000f){return (float)(0.258*Math.pow(freqMhz,0.5));}
        if(freqMhz < 300000f){return 11.5f;}
        return 0f;
    }

    //---- wavelengthVacuum ----//
    /**
     * used to convert frequency to wavelength, assumes vacuum
     * @return float representing the wavelength in metres, returns zero if frequencyMhz is less than or equal to zero
     */
    public static float wavelengthVacuum(float freqMhz){
        QuickTools.freqMhz = freqMhz;
        if (freqMhz > 0)
            return speedOfLight/freqMhz;
        return 0f;
    }


}
