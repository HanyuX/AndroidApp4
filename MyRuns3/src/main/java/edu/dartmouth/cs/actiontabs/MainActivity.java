package edu.dartmouth.cs.actiontabs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import edu.dartmouth.cs.actiontabs.view.SlidingTabLayout;

public class MainActivity extends Activity {
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private ActionTabsViewPagerAdapter myViewPageAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

        // Define SlidingTabLayout (shown at top)
        // and ViewPager (shown at bottom) in the layout.
        // Get their instances.
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // create a fragment list in order.
        fragments = new ArrayList<Fragment>();
        fragments.add(new StartFragment());
        fragments.add(new HistoryFragment());
        fragments.add(new SettingFragment());

        // use FragmentPagerAdapter to bind the slidingTabLayout (tabs with different titles)
        // and ViewPager (different pages of fragment) together.
        myViewPageAdapter =new ActionTabsViewPagerAdapter(getFragmentManager(),
                fragments);
        viewPager.setAdapter(myViewPageAdapter);

        // make sure the tabs are equally spaced.
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
	}

    /** called when the Save button is clicked */
    public void onClickStart(View v) {
        Spinner spinnerInput = (Spinner)findViewById(R.id.spinnerInputType);
        Spinner spinnerActivity = (Spinner)findViewById(R.id.spinnerActivityType);
        String InputType = spinnerInput.getSelectedItem().toString();
        String ActivityType = spinnerActivity.getSelectedItem().toString();
        Intent mIntent;
        switch(InputType) {
            case "Manual Entry":
                mIntent = new Intent(MainActivity.this,
                        ManualEntry.class);
                mIntent.putExtra("InputType", "ManualEntry");
                mIntent.putExtra("ActivityType", ActivityType);
                startActivity(mIntent);
                break;
            case "GPS":
                mIntent = new Intent(MainActivity.this,
                        MapActivity.class);
                startActivity(mIntent);
                break;
            case "Automatic":
                mIntent = new Intent(MainActivity.this,
                        MapActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    /** called when the Sync button is clicked */
    public void onClickSync(View v) {
        Toast.makeText(getApplicationContext(), "Sync",Toast.LENGTH_SHORT).show();
    }
}