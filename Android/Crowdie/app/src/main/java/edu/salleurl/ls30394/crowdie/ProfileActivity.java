package edu.salleurl.ls30394.crowdie;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.CheckBox;

import com.google.gson.Gson;

import edu.salleurl.ls30394.crowdie.Fragments.SupporterFragment;
import edu.salleurl.ls30394.crowdie.Fragments.VictimFragment;
import edu.salleurl.ls30394.crowdie.model.User;

public class ProfileActivity extends AppCompatActivity {

    private CheckBox cbFirstAid;

    private CheckBox cbLifeGuard;

    private CheckBox cbCPR;

    private CheckBox cbMental;

    private CheckBox cbHeartIssues;

    private CheckBox cbPhysicallyDisabled;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initWidgets();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void initWidgets() {

        cbCPR = (CheckBox)findViewById(R.id.profile_hasCPRPractice);
        cbFirstAid = (CheckBox)findViewById(R.id.profile_has1stAidPractice);
        cbLifeGuard = (CheckBox)findViewById(R.id.profile_isLifeGuard);

        cbHeartIssues = (CheckBox)findViewById(R.id.profile_hasCardioProblems);
        cbMental = (CheckBox)findViewById(R.id.profile_hasMentalHealthIssues);
        cbPhysicallyDisabled = (CheckBox)findViewById(R.id.profile_isPhysicallyDisabled);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_profile_done:
                retrieveUserForm();
                return true;
            default:
                return true;
        }
    }

    private void retrieveUserForm() {

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        User user = new User();

        user.setCanPerformCPR(cbCPR.isChecked());
        user.setFirstAidSkills(cbFirstAid.isChecked());
        user.setLifeGuard(cbLifeGuard.isChecked());

        user.setHasCardioVascularIssues(cbHeartIssues.isChecked());
        user.setHasMentalHealthIssues(cbMental.isChecked());
        user.setPhysicallyDisabled(cbPhysicallyDisabled.isChecked());

        user.setUserName(preferences.getString("UserName", ""));

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        Log.d("USER:", userJson);

        editor.putString("UserData", userJson);
        editor.apply();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){

                case 0:
                    SupporterFragment supporterTab = new SupporterFragment();
                    return supporterTab;

                case 1:
                    VictimFragment victimTab = new VictimFragment();
                    return victimTab;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SUPPORTER";
                case 1:
                    return "VICTIM";
            }
            return null;
        }
    }

}
