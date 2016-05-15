package com.pengziyue.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;



import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HourFragment extends Fragment {
    private static final String KEY_DATA = "hour_list";
    private static final String TAG = "HourFragment";
    private Context context = null ;
    private ArrayList<Hour> data = null ;
    private ListView listView ;

    public HourFragment() {
    }

    public static HourFragment getInstance(ArrayList<Hour> data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATA,data);
        HourFragment hourFragment = new HourFragment();
        hourFragment.setArguments(bundle);
        return hourFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext() ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate
                (R.layout.fragment_hour, container, false);
        listView = (ListView) view.findViewById(R.id.hour_listview);
        data = (ArrayList<Hour>) getArguments().getSerializable(KEY_DATA);
        for(Hour h : data){
            Log.d(TAG,"Hour DATA:"+h.toString());
        }
        HourAdapter adapter = new HourAdapter(context, data);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

}
