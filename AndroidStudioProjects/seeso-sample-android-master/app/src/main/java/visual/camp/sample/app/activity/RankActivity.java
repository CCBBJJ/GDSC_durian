package visual.camp.sample.app.activity;

import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.Map;
import java.util.TreeMap;

import visual.camp.sample.app.R;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RankActivity extends AppCompatActivity {
    Button btn_back, btn_test;
    private DatabaseReference database;
    private ArrayList<String> scores;
    private ArrayList<String> nickNames;
    private ListView v_list;
    private ListViewAdapter mListViewAdapter;
    Comparator<Integer> comparator = Comparator.reverseOrder();
    private Map<Integer, String> nN_score = new TreeMap<Integer, String>(comparator);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        v_list = (ListView) findViewById(R.id.v_list);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_test = (Button) findViewById(R.id.btn_test);

        firstInit();
        addRank();


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 1; i<31; i++){
                    FirebaseDatabase.getInstance().getReference().child("rank").child(""+i).child("score").setValue("0000");
                    FirebaseDatabase.getInstance().getReference().child("rank").child(""+i).child("nickName").setValue("empty");
                }
            }
        });
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
                    //scores.add(dataSnapshot.child("score").getValue().toString());
                    //nickNames.add(dataSnapshot.child("nickName").getValue().toString());

                    nN_score.put(Integer.parseInt(dataSnapshot.child("score").getValue().toString()), dataSnapshot.child("nickName").getValue().toString());
                }

                for(Map.Entry<Integer, String> entrySet : nN_score.entrySet()) {
                    Log.d("rank", "score:" + entrySet.getKey() + "nName:" + entrySet.getValue());
                    scores.add(entrySet.getKey().toString());
                    nickNames.add(entrySet.getValue());

                    mListViewAdapter = new ListViewAdapter(getApplicationContext(), scores, nickNames);
                    v_list.setAdapter(mListViewAdapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
