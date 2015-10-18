package au.jazzyjas84.pocketrf;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Determines the safe distances around an antenna.
 * Reference: Australian Communication Authority Guidelines on the Assessment
 *            of installations against electromagnetic radiation (EMR)
 *            exposure limits (September 2000)
 */
public class DistanceToRadhaz extends AppCompatActivity {
    /**
     * Instance variables
     */
    private float linearAperture = 0f; // longest linear dimension of the radiating antenna in metres
    private float freqMhz = 0f; // the frequency of the radiated field in MHz
    private boolean isCircular = false; // true if the radiating antenna is circular polarized
    private float powerWatts = 0f; // the RF power into the antenna in Watts
    private float onAxisGain = -1234; // the gain of the antenna in dBi
    private float genPubLimit = 0f; // the general public limit in W/m2
    private float occLimit = 0f; // the occupational limit in W/m2
    private float distanceToGenPublic; // the distance to the general public boundary in metres
    private float distanceToOccLimit; // the diatance to the occupational boundary in metres

    private CheckBox chkCircular;
    private Toolbar toolbar;
    private EditText freq_mhz;
    private EditText linear_aperture;
    private TextView txt_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_to_radhaz);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        if(savedInstanceState != null){
            //TODO load bundle
        }
    }

    /**
     * Checks if data is valid. Calculates limits if data is valid.
     * @return  true if data is valid, false otherwise
     */
    private boolean DataOK(){
        // linearAperture must be positive non-zero
        if(linearAperture <= 0){return false;}

        // freqMhz must be positive non-zero
        if(freqMhz <= 0){ return false;}

        // powerWatts must not be negative
        if(powerWatts < 0){return false;}

        // onAxisGain should not equal -1234
        if(onAxisGain == -1234f){return false;}

        // genPubLimit should be calculated
        genPubLimit = QuickTools.getLimit(freqMhz,QuickTools.GEN_PUB_AVG_E)*
                QuickTools.getLimit(freqMhz,QuickTools.GEN_PUB_AVG_H);

        // occLimit should be calculated
        occLimit = QuickTools.getLimit(freqMhz,QuickTools.OCC_AVG_E)*
                QuickTools.getLimit(freqMhz,QuickTools.OCC_AVG_H);

        // genPubLimit must be greater than 0;
        if(genPubLimit <= 0){return false;}

        // occLimit must be greater than 0;
        if(occLimit <= 0){return false;}

        return true;
    }

    private double breakDistance(){
        // check data is OK
        if(DataOK()) {
            // if circular..
            if(isCircular){
                return linearAperture*linearAperture*freqMhz/900.0;
            }
            // if rectangular..
            return linearAperture*linearAperture*freqMhz/600.0;
        }
        return 0.0;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        boolean chkState = chkCircular.isChecked();
        outState.putBoolean("chkState", chkState);
        check_circular(chkCircular);
    }

    public void check_circular(View view){
        isCircular = ((CheckBox) view).isChecked();
}

    public void doCalc(View view){
        //TODO calc methods
        TextView text_view = (TextView)findViewById(R.id.txtResult);
        text_view.setText("Doing things doing things, doing stuff doing things.");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);
    }


}
