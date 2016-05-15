package com.pengziyue.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ActionBar actionBar;
    private DataLoaderTask task = null;
    private MyFragmentAdapter fAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        FragmentManager fm = getSupportFragmentManager();
        fAdapter = new MyFragmentAdapter(fm);
        viewPager.setAdapter(fAdapter);
        tabLayout.setupWithViewPager(viewPager);
        updateJson();
    }

    /**
     *  Fragment分页用的适配器
     */
    class MyFragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = null;
        private List<String> stringList = null;
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<>();
            stringList = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position){
            return stringList.get(position);
        }

        public void createFragment(
                Fragment fragment,
                String string){
            fragmentList.add(fragment);
            stringList.add(string);
        }

        public void createFragment(
                Fragment fragment,
                @StringRes int resid){
            fragmentList.add(fragment);
            stringList.add(getResources().getString(resid));
        }
    }

    private void updateJson() {

        try {
            // 读取JSON文件到字符串中
            InputStream is = getAssets().open("data.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String json = "";
            String line = null;
            while ((line = br.readLine()) != null) {
                json += line;
            }

//             传入异步任务进行处理
            task = new DataLoaderTask();
            task.execute(json);
//            task.doInBackground(json);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    class DataLoaderTask extends AsyncTask<String,String,Void> {

        private Now now = null ;
        private ArrayList<Hour> hourList = null ;
        private ArrayList<Daily> dailyList = null ;

        @Override
        protected Void doInBackground(String... params) {
            String result = "" ;
            try {
                // 把JSON字符串转换为JSON对象
                JSONObject jo = new JSONObject(params[0]);
                JSONArray jaHourly = jo.getJSONArray("hourly");
                JSONArray jaDaily = jo.getJSONArray("daily");

                JSONObject joNow = jo.getJSONObject("now");
                String city = joNow.getString("city");
                String cond = joNow.getString("cond");
                String tmp = joNow.getString("tmp");
                String wind = joNow.getString("wind");

                now = new Now(city,cond,tmp,wind);

                hourList = new ArrayList<>();
                for(int i=0;i<jaHourly.length();i++){
                    JSONObject joHour = jaHourly.getJSONObject(i);
                    String hourDate = joHour.getString("date");
                    String hourTmp = joHour.getString("tmp");
                    Hour hour = new Hour(hourDate,hourTmp);
                    hourList.add(hour);
                }

                dailyList = new ArrayList<>();
                for(int i=0;i<jaDaily.length();i++){
                    JSONObject joDaily = jaDaily.getJSONObject(i);
                    String dailyDate = joDaily.getString("date");
                    String dailyMax = joDaily.getString("max");
                    String dailyMin = joDaily.getString("min");
                    Daily daily = new Daily(dailyDate,dailyMax,dailyMin);
                    Log.d(TAG,"-----"+daily.toString());
                    dailyList.add(daily);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            fAdapter.createFragment(NowFragment.getInstance(now),"now");
            fAdapter.createFragment(HourFragment.getInstance(hourList),"hour");
            fAdapter.createFragment(DailyFragment.getInstance(dailyList),"daily");
            fAdapter.notifyDataSetChanged();
        }
    }
}
