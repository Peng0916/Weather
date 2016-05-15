package com.pengziyue.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PengYue on 2016/4/26.
 */
public class HourAdapter extends BaseAdapter {
    private Context context  = null ;
    private List<Hour> data  = null ;
    private LayoutInflater inflater = null ;

    public HourAdapter(Context context, List<Hour> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Hour getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent) {
        ViewHorder viewHorder = null ;
        if(convertView==null){
            convertView = inflater
                    .inflate(R.layout.hour_item,parent,false);
            viewHorder = new ViewHorder(convertView);
            convertView.setTag(viewHorder);
        }else {
            viewHorder = (ViewHorder) convertView.getTag();
        }
        viewHorder.bindView(data.get(position));
        return convertView;
    }



    static class ViewHorder {
        TextView tvDate,tvTmp ;

        public ViewHorder(View view) {
            tvDate = (TextView) view.findViewById(R.id.hour_tvdate);
            tvTmp = (TextView) view.findViewById(R.id.hour_tvtmp);
        }
        private void bindView(Hour hour) {
            tvDate.setText(hour.getDate());
            tvTmp.setText(hour.getTmp());
        }
    }
}
