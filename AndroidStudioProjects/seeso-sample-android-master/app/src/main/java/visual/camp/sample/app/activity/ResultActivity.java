package visual.camp.sample.app.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import visual.camp.sample.app.R;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ResultActivity extends AppCompatActivity {
    TextView txt_nickName, txt_score;
    private ListView v_listR;
    private ArrayList<String> scores;
    private ArrayList<String> nickNames;
    private ArrayList<String> test;
    private ListViewAdapter mListViewAdapter;
    private int count = 1;
    Comparator<Integer> comparator = Comparator.reverseOrder();
    private Map<Integer, String> nN_score = new TreeMap<Integer, String>(comparator);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        v_listR = (ListView) findViewById(R.id.v_listR);
        txt_nickName = (TextView) findViewById(R.id.txt_nickName);
        txt_score = (TextView) findViewById(R.id.txt_score);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras(); // bundle을 통해 Extra들을 모두 가져온다
        Long result =  bundle.getLong("sec");
        String nick = bundle.getString("nN");

        txt_nickName.setText(nick+" ");
        txt_score.setText(result.toString());

        firstInit();
        addRank();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private void firstInit(){
        scores = new ArrayList<>();
        nickNames = new ArrayList<>();
    }

    public void addRank(){
        FirebaseDatabase.getInstance().getReference().child("rank").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Log.d("test", "rankChildren" + dataSnapshot.child("score").getValue());
                    Log.d("test", "rankChildren" + dataSnapshot.child("nickName").getValue());
                    scores.add(dataSnapshot.child("score").getValue().toString());

                    nickNames.add(dataSnapshot.child("nickName").getValue().toString());

                    nN_score.put(Integer.parseInt(dataSnapshot.child("score").getValue().toString()), dataSnapshot.child("nickName").getValue().toString());

                }

                for(Map.Entry<Integer, String> entrySet : nN_score.entrySet()){
                    Log.d("rank","score:" + entrySet.getKey()+"nName:"+entrySet.getValue());
                    scores.add(entrySet.getKey().toString());
                    nickNames.add(entrySet.getValue());
                    //FirebaseDatabase.getInstance().getReference().child("rank").child(""+count).child("score").setValue(entrySet.getKey().toString());
                    //FirebaseDatabase.getInstance().getReference().child("rank").child(""+count).child("nickName").setValue(entrySet.getValue());

                    count++;
                    if(count==30){
                        break;
                    }
                }

                mListViewAdapter = new ListViewAdapter(getApplicationContext(), scores, nickNames);
                v_listR.setAdapter(mListViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void insertion_sort(){

    }
}
