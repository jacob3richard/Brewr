package com.example.richardje1.brewr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class HomePageActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static String[] friends;
    public static String[] soloFriend;

    //ConcurrentHashMap<String, String[]> ch = new ConcurrentHashMap<String, String[]>();

    //BackgroundWorker backgroundWorker;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private FloatingActionButton mFloatingActionButton;
    private ViewPager mViewPager;
    //private LinearLayout mLinearLayout;
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //backgroundWorker = new BackgroundWorker(this);

        setContentView(R.layout.activity_home_page);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            userID = b.getString("a");
        }

        soloFriend = new String[]{ userID };

        FriendWorker fw = new FriendWorker();

        fw.execute(userID);

        SharedPreferences bb = getSharedPreferences("my_prefs", 0);
        String m = bb.getString("FID", "");

        friends = m.split("-");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_activity);


        mFloatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),
                            "make activity", Toast.LENGTH_SHORT).show();

                    //Intent myIntent = new Intent(v.getContext(), HomePageAllActivity.class);
                    Intent myIntent = new Intent(v.getContext(), CreateBrew.class);
                    myIntent.putExtra("a", userID);
                    startActivityForResult(myIntent, 0);
                    finish();


                }

        });

        //toolbar.inflateMenu(R.menu.menu_home_page);
        /**
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                switch(item.getItemId()){
                    case R.id.action_settings:
                        return true;
                    case R.id.add_activity:
                        CreateUser user = new CreateUser();
                        Intent myIntent = new Intent(this.get);
                        startActivityForResult(myIntent, 0);
                        return true;
                }

               return false;
            }
        });
         **/
        setSupportActionBar(toolbar);
        //MenuItem createActivity = (MenuItem) findViewById(R.id.add_activity);
        //onOptionsItemSelected(createActivity);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);

        //getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_activity) {
            //CreateUser cr = new CreateUser();
            //Intent i = new Intent(CreateUser.class);
            //Intent myIntent = new Intent((R.id.add_activity).getContext(), CreateUser.class);
            //startActivityForResult(myIntent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void createUser(MenuItem item){
        CreateUser cr = new CreateUser();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static BrewListFragment newInstance(int sectionNumber) {
            BrewListFragment fragment = null;
            if (sectionNumber == 1) {
                fragment = new BrewListFragment();
                //Bundle args = new Bundle();
                //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                //fragment.setArguments(args);

                fragment.setFriends(friends);
                //fragment.updateUI();
                //fragment.setFriends(friends, soloFriend);
                //fragment.updateUI();
            }
            else if (sectionNumber == 2) {
                fragment = new BrewListFragment();
                //Bundle args = new Bundle();
                //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                //fragment.setArguments(args);
                //fragment.setFriends(soloFriend, friends);

                fragment.setFriends(soloFriend);
            }
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public static synchronized void setFriends(String[] f) {
        friends = Arrays.copyOf(f, f.length);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /**
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            **/
            /*
            switch(position) {
                case 1:
                    return FriendsFragment.newInstance(friends);
                case 2:
                    return SelfFragment.newInstance(soloFriend);
                default:
                    return null;
            }*/
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Friends";
                case 1:
                    return "Self";

            }
            return null;
        }
    }

    class FriendWorker extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/getFollow.php";
            String id = params[0];
            try {
                //String user_name = params[2];
                //String password = params[3];
                URL url = new URL(URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //String friend[] = result.split("-");
            //setFriends(f);
            SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("FID", result);
            edit.commit();

        }

    }

}
