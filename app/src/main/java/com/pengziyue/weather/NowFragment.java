package com.pengziyue.weather;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowFragment extends Fragment {
    private static final String KEY_DATA = "get_Data";
    private Context context = null;
    private TextView tvCity,tvNumb,tvState;
//    private static Now nowData = null ;

    public NowFragment() {
        // Required empty public constructor
    }

    public static NowFragment getInstance(Now now) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATA,now);
        NowFragment nowFragment = new NowFragment();
        nowFragment.setArguments(bundle);
        return nowFragment ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate
                (R.layout.fragment_now, container, false) ;
        tvCity = (TextView) view.findViewById(R.id.now_tvcity);
        tvNumb = (TextView) view.findViewById(R.id.now_tvnumb);
        tvState = (TextView) view.findViewById(R.id.now_tvstate);

        Bundle bundle = getArguments();
        Now now = (Now) bundle.getSerializable(KEY_DATA);
        tvCity.setText(now.getCity());
        tvNumb.setText(now.getTmp());
        tvState.setText(now.getCond()
                +"    "+now.getWind());
        return view;
    }

}
