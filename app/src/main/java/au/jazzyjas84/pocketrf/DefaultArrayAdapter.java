package au.jazzyjas84.pocketrf;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Custom layout adapter for DefaultActivity -> listView
 */
public class DefaultArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] titles;
    private final String[] descriptions;

    static class ViewHolder{
        public TextView title;
        public TextView description;
    }

    public DefaultArrayAdapter(Context context, String[] titles, String[] descriptions){
        super(context,-1,titles);
        this.context = context;
        this.titles = titles;
        this.descriptions = descriptions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View rowView = convertView;
        if(rowView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_view_layout, null);
            holder = new ViewHolder();
            holder.title=(TextView)rowView.findViewById(R.id.list_title);
            TextView description = (TextView)rowView.findViewById(R.id.list_description);
            description.setAlpha(0.33f);
            holder.description = description;
            rowView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.title.setText(titles[position]);
        holder.description.setText(descriptions[position]);

        return rowView;
    }
}
