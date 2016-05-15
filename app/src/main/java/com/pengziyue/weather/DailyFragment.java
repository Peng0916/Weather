package com.pengziyue.weather;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment {
    private static final String KEY_DATAS = "daily_list_data";
    private static final String TAG = "DailyFragment";
    private Context context = null ;
    private ArrayList<Daily> data = null ;
    private DailyAdapter adapter = null;
    private ListView listView ;

    public DailyFragment() {
        // Required empty public constructor
    }

    public static DailyFragment getInstance(ArrayList<Daily> datas) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATAS,datas);
        DailyFragment fragment = new DailyFragment();
        fragment.setArguments(bundle);
        return fragment ;
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
                (R.layout.fragment_daily, container, false) ;
        listView = (ListView) view.findViewById(R.id.daily_listview);
        data = (ArrayList<Daily>) getArguments().getSerializable(KEY_DATAS);
        adapter = new DailyAdapter(context,data);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}
