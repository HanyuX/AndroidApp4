package edu.dartmouth.cs.actiontabs;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuehanyu on 4/5/16.
 */
public class HistoryFragment extends Fragment{

    private ListView listview;
    private List<databaseItem> list;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.history_layout, container, false);

        list = new ArrayList<databaseItem>();
        databaseItem item = new databaseItem("1","2015", "19:00", 1, 2, 3, 4, "haha", "123", "Standing");
        list.add(item);
        listview = (ListView) view.findViewById(R.id.datalist);
        listview.setAdapter(new MyAdapter(getActivity(), list));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", list.get(position).ID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    class MyAdapter extends BaseAdapter {
        Context context;
        List<databaseItem> list;
        private LayoutInflater layoutInflater;
        public MyAdapter(Context context, List<databaseItem> list) {
            this.context = context;
            this.list = list;
            layoutInflater = LayoutInflater.from(this.context);
        }
        /*
         * return the number of the items in the list
         */
        @Override
        public int getCount() {
            return list.size();
        }

        /*
         * return the item object;
         */
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        /*
         * return the id of the selected item
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /*
         * return the image view
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_historylayout, null);
            }

            TextView textview1 = (TextView) convertView.findViewById(R.id.method_time);
            TextView textview2 = (TextView) convertView.findViewById(R.id.mile_time);

            textview1.setText("Manual Entry: " + list.get(position).ActivityType + "," + list.get(position).Date);
            textview2.setText(list.get(position).Distance + "Miles, " + list.get(position).Duration + "Mins");
            return convertView;
        }
    }
}