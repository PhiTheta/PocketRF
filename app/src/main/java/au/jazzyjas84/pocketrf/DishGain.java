package au.jazzyjas84.pocketrf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class DishGain extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText freq_mhz;
    private EditText linear_aperture;
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

        linear_aperture = (EditText)findViewById(R.id.input_linear_aperture);
        linear_aperture.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        txt_result = (TextView)findViewById(R.id.txtResult);
    }

    public void doCalc(View view){
        // TODO: calc methods
        txt_result.setText("Doing things doing things, doing stuff doing things.");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);
    }
}
