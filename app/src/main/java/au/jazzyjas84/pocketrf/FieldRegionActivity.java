package au.jazzyjas84.pocketrf;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Calculates the near and far field distances from a radiating antenna. Field regions
 * are defined in AS/NZS 2772.2:2011 Appendix B.
 */
public class FieldRegionActivity extends AppCompatActivity {

    /**
     * Instance variables
     */
    private float linearAperture = 0f; // longest linear dimension of the radiating antenna in metres
    private float freqMhz = 0f; // the frequency of the radiated field in MHz
    private boolean isCircular = false; // true if the radiating antenna is circular polarized
    private float reactiveDistance = 0f; // max distance to the reactive near field boundary, measured in metres
    private float radiatingDistance = 0f; // max distance to the radiating near field (fresnel) boundary, measured in metres

    private CheckBox chkCircular;
    private Toolbar toolbar;
    private EditText freq_mhz;
    private EditText linear_aperture;
    private TextView txt_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_region);

        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        chkCircular = (CheckBox)findViewById(R.id.chkCircular);
        if(savedInstanceState != null){
            chkCircular.setChecked(savedInstanceState.getBoolean("chkState"));
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        freq_mhz = (EditText)findViewById(R.id.input_freq_mhz);
        freq_mhz.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        linear_aperture = (EditText)findViewById(R.id.input_linear_aperture);
        linear_aperture.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        txt_result = (TextView)findViewById(R.id.txtResult);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        boolean chkState = chkCircular.isChecked();
        outState.putBoolean("chkState",chkState);
        check_circular(chkCircular);
    }

    public void check_circular(View view){
        boolean checked = ((CheckBox) view).isChecked();
        setIsCircular(checked);
    }

    public void doCalc(View view){
        try{
            freqMhz = Float.parseFloat(freq_mhz.getText().toString());
        }
        catch (Exception e){
            freqMhz = 0f;
        }
        try{
            linearAperture = Float.parseFloat(linear_aperture.getText().toString());
        }
        catch (Exception e){
            linearAperture = 0f;
        }
        String result = "Reactive near field: " + resultRegionI() + "\n\n" +
                "Radiating near field: " + resultRegionII() + "\n\n" +
                "Far field: " + resultRegionIII();
        txt_result.setText(result);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);
    }

    //---- setLinearAperture ----//
    /**
     * set the linearAperture value used in the calculation
     * @param linearDimension  the longest linear dimension of the radiating antenna measured in metres, must be greater than 0
     * @return true if the value of linearDimension is a valid positive number, false otherwise
     */
    public boolean setLinearAperture(float linearDimension){

        if(linearDimension > 0){
            this.linearAperture = linearDimension;
            return true;
        }
        return false;
    }

//---- setFreqMhz ----//
    /**
     * set the freqMhz value used in the calculation
     * @param freqMhz  the frequency of the radiated field in MHz, must be greater than 0
     * @return true if the value of freqMhz is a valid positive number, false otherwise
     */
    public boolean setFreqMhz(float freqMhz){
        if(freqMhz > 0){
            this.freqMhz = freqMhz;
            return true;
        }
        return false;
    }

//---- setIsCircular ----//
    /**
     * sets the value for whether or not the radiating antenna should be treated as a circularly polarized antenna
     * @param isCircular  value indicating whether or not the antenna is circularly polarized
     */
    public void setIsCircular(boolean isCircular){
        this.isCircular = isCircular;
    }

//---- getLinearAperture ----//
    /**
     * gets the linearAperture value stored
     * @return float representing the longest linear dimension of the radiating antenna, measured in metres. Default value is 0.0f
     */
    public float getLinearAperture(){
        return linearAperture;
    }

//---- getFreqMhz ----//
    /**
     * gets the frequency value stored
     * @return float representing the frequency of the radiated field, measured in MHz. Default value is 0.0f
     */
    public float getFreqMhz(){
        return freqMhz;
    }

//---- getIsCircular ----//
    /**
     * gets whether or not the object represented is circularly polarized
     * @return boolean returns true if the object represented is circularly polarized. Default value is false
     */
    public boolean getIsCircular(){
        return isCircular;
    }

//---- getReactiveDistance ----//
    /**
     * gets the distance to the reactive near field boundary stored
     * @return float representing the distance to the reactive near field boundary, measured in metres. Default value is 0.0f
     */
    public float getReactiveDistance(){
        return reactiveDistance;
    }

//---- getRadiatingDistance ----//
    /**
     * gets the distance to the radiating near field boundary stored
     * @return float representing the distance to the radiating near field boundary, measured in metres. Default value is 0.0f
     */
    public float getRadiatingDistance(){
        return radiatingDistance;
    }

//---- ReactiveNearDistance ----//
    /**
     * used to calculate and store the upper bound of the reactive near field region in metres.
     * Formula is from Table B1 Appendix B AS/NZS 2772.2:2011
     * @return true if lambda > 0 , else false
     */
    private boolean ReactiveNearDistance(){
        float lambda = QuickTools.wavelengthVacuum(freqMhz);
        if(lambda > 0){
            if(isCircular){
                reactiveDistance=linearAperture*linearAperture/(4*lambda);
            }else{
                reactiveDistance = lambda/4;
            }
            return true;
        }
        return false;
    }

//---- RadiatingNearDistance ----//
    /**
     * used to calculate and store the upper bound of the radiating near field region in metres.
     * Formula is from Table B1 Appendix B AS/NZS 2772.2:2011
     * @return true if lambda > 0 , else false
     */
    private boolean RadiatingNearDistance(){
        float lambda = QuickTools.wavelengthVacuum(freqMhz);
        if(lambda > 0){
            radiatingDistance=(lambda/4) + 2*linearAperture*linearAperture/lambda;
            return true;
        }
        return false;
    }

//---- resultRegionI ----//
    /**
     * displays the reactive near field (Region I) bounds
     * @return string describing the region I bounds in metres
     */
    public String resultRegionI(){
        boolean ok = ReactiveNearDistance();
        if(!ok){return "bad input";}
        reactiveDistance = QuickTools.roundDigits(reactiveDistance,2);
        return "0m to " + reactiveDistance + "m";
    }

//---- resultRegionII ----//
    /**
     * displays the radiating near field (Region II) bounds
     * @return string describing the region II bounds in metres
     */
    public String resultRegionII(){
        boolean ok = ReactiveNearDistance();
        if(!ok){return "bad input";}
        reactiveDistance = QuickTools.roundDigits(reactiveDistance,2);
        ok = RadiatingNearDistance();
        if(!ok){return "bad input";}
        radiatingDistance = QuickTools.roundDigits(radiatingDistance,2);
        return reactiveDistance + "m to " + radiatingDistance + "m";
    }

//---- resultRegionIII ----//
    /**
     * displays the far field (Region III) bounds
     * @return string describing the region III bounds in metres
     */
    public String resultRegionIII(){
        boolean ok = RadiatingNearDistance();
        if(!ok){return "bad input";}
        radiatingDistance = QuickTools.roundDigits(radiatingDistance, 2);
        return "greater than " + radiatingDistance + "m";
    }
}
