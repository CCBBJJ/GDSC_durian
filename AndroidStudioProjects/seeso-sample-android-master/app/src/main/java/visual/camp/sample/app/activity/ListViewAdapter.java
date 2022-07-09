package visual.camp.sample.app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import visual.camp.sample.app.R;

public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> scores;
    private ArrayList<String> nickNames;


    public ListViewAdapter(Context mContext, ArrayList<String> scores, ArrayList<String> nickNames){
        this.mContext = mContext;
        this.scores = scores;
        this.nickNames = nickNames;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rank_item, parent, false);
        }

        TextView txt_rank = (TextView) convertView.findViewById(R.id.txt_rank);
        TextView txt_score = (TextView) convertView.findViewById(R.id.txt_score);
        TextView txt_nickName = (TextView) convertView. findViewById(R.id.txt_nickName);

        txt_rank.setText("" + (position+1));
        txt_score.setText(scores.get(position));
        txt_nickName.setText(nickNames.get(position));

        return convertView;
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int i) {
        return scores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
