package au.jazzyjas84.pocketrf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class DishGain extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_gain);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        if(savedInstanceState != null){
            //TODO load bundle
        }
    }

    public void doCalc(View view){
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
