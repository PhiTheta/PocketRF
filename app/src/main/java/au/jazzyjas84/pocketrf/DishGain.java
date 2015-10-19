package au.jazzyjas84.pocketrf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DishGain extends AppCompatActivity {
    // Instance variables
    private float dishDiameter = 0f;
    private float freqMhz = 0f;
    private float apertureEfficiency = 0f;
    private float dishGain = 0f;
    private float beamwidth3dB = 0f;
    private float dishAperture = 0f;
    private float wavelengthM = 0f;

    private Toolbar toolbar;
    private EditText freq_mhz;
    private EditText linear_aperture;
    private EditText aperture_efficiency;
    private TextView txt_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_gain);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        freq_mhz = (EditText)findViewById(R.id.input_freq_mhz);
        freq_mhz.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        linear_aperture = (EditText)findViewById(R.id.input_DG_linear_aperture);
        linear_aperture.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        aperture_efficiency = (EditText)findViewById(R.id.input_efficiency);
        aperture_efficiency.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        txt_result = (TextView)findViewById(R.id.txtResult);
    }

    public void doCalc(View view){
        txt_result.setText(Result());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);
    }

    // Methods

    //--Get Set
    /**
     * Sets the diameter of the dish
     * @param diameter the diameter of the dish in metres, must be positive non zero
     */
    public void setDishDiameter(float diameter){
        if(diameter > 0)
            this.dishDiameter = diameter;
    }

    /**
     * Sets the frequency for the evaluation
     * @param freqMhz the frequency in MHz, must be positive non zero
     */
    public void setFreqMhz(float freqMhz){
        if(freqMhz > 0){
            this.freqMhz = freqMhz;
        }
    }

    /**
     * Sets the aperture efficiency value for the evaluation
     * @param ae the aperture efficiency, must be a non zero positive value not greater than 1
     */
    public void setApertureEfficiency(float ae){
        if(ae > 0 && ae <=1){
            this.apertureEfficiency = ae;
        }
    }

    /**
     * the stored gain value
     * @return float representing the dimensionless (linear) gain value
     */
    public float getDishGain(){
        return this.dishGain;
    }

    /**
     * the stored half power beamwidth
     * @return the half power beamwidth, in degrees
     */
    public float getHalfPowerBeamwidth(){
        return this.beamwidth3dB;
    }

    // VerifyValues
    /**
     * Data check to ensure all parameters are valid, and calculates the wavelength and aperture
     * @return  true if all data is OK, false otherwise
     */
    private boolean DataOK(){
        if(dishDiameter <= 0)
            return false;
        if(freqMhz <=0)
            return false;
        if(apertureEfficiency <= 0 || apertureEfficiency > 1)
            return false;
        wavelengthM = QuickTools.wavelengthVacuum(freqMhz);
        DishAperture();
        return true;
    }

    // DishAperture
    /**
     * Calculates the dish aperture from the diameter, assumes the antenna is circular
     */
    private void DishAperture(){
        this.dishAperture = (float)(Math.PI) * this.dishDiameter * this.dishDiameter/4.0f;
    }

    // DishGain()
    /**
     * calculates the gain value from existing data, and sets the dishGain instance value
     */
    private void calcGain(){
        if(DataOK()){
            double linGain = this.apertureEfficiency * 4 * Math.PI * this.dishAperture / (wavelengthM * wavelengthM);
            this.dishGain = 10 * (float)Math.log10(linGain);
        }
    }

    // Beamwidth3dB()
    /**
     * calculates the half power beamwidth from existing data, and sets the beamwidth3dB instance value
     */
    private void calcBeamwidth(){
        if(DataOK()){
            this.beamwidth3dB = 70*this.wavelengthM/this.dishDiameter;
        }
    }

    // Results
    /**
     * Performs all necessary calculations, including data integrity checks
     * @return String statement of the results
     */
    private String Result(){
        // get data
        try{
            freqMhz = Float.parseFloat(freq_mhz.getText().toString());
            dishDiameter = Float.parseFloat(linear_aperture.getText().toString());
            apertureEfficiency = Float.parseFloat(aperture_efficiency.getText().toString());
        }catch(Exception e){
            return e.getMessage();
        }
        // check data
        if(!DataOK())
            return "Bad data";
        calcGain();
        calcBeamwidth();
        return "Gain: " + QuickTools.roundDigits(this.dishGain,1) + " dBi\n" +
                "3dB Beamwidth: " + QuickTools.roundDigits(this.beamwidth3dB,1) +"°";
        //
    }
}
