package au.jazzyjas84.pocketrf;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DefaultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO fix this
                Snackbar.make(view, "I'm not just a peanut", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        addListItems();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Start FieldRegions activity
     */
    private void showFieldRegions(){
        Intent intent = new Intent(this,FieldRegionActivity.class);
        startActivity(intent);
    }

    /**
     * Start DishGain activity
     */
    private void showDishGain(){
        Intent intent = new Intent(this,DishGain.class);
        startActivity(intent);
    }

    /**
     * Add list items to ListView
     */
    private void addListItems(){

        final ListView listView = (ListView)findViewById(R.id.listView);
        final String[] titles = getResources().getStringArray(R.array.list_view_titles);
        final String[] descriptions = getResources().getStringArray(R.array.list_view_descriptions);

        ArrayAdapter<String> adapter = new DefaultArrayAdapter(getApplicationContext(),titles,descriptions);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        showFieldRegions();
                        break;
                    case 1:
                        showDishGain();
                        break;
                }

            }
        });

      }
}
