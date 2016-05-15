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
public class DailyAdapter extends BaseAdapter {
    private Context context = null;
    private List<Daily> data = null;
    private LayoutInflater inflater = null;

    public DailyAdapter(Context context, List<Daily> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Daily getItem(int position) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.daily_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bindView(data.get(position));
        return convertView;
    }

    static class ViewHolder {
        TextView tvDate, tvMax, tvMin;

        public ViewHolder(View view) {
            tvDate = (TextView) view.findViewById(R.id.daily_tvdate);
            tvMax = (TextView) view.findViewById(R.id.daily_tvmax);
            tvMin = (TextView) view.findViewById(R.id.daily_tvmin);
        }

        public void bindView(Daily daily) {
            tvDate.setText(daily.getDate());
            tvMax.setText(daily.getMax());
            tvMin.setText(daily.getMin());
        }
    }
}
