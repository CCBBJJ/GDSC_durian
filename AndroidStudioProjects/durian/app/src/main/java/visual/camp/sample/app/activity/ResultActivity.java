package visual.camp.sample.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import visual.camp.sample.app.R;

public class ResultActivity extends AppCompatActivity {
    private Long time;
    TextView txt_time;
    Button btn_home;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txt_time = (TextView) findViewById(R.id.txt_time);
        btn_home = (Button) findViewById(R.id.btn_home);

        Intent intent = getIntent();
         time = intent.getLongExtra("time",0);
         String result = time.toString();

         //txt_time.setText(result.substring(0,result.length()-3));
         txt_time.setText(minute(result));

         btn_home.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent1 = new Intent(ResultActivity.this, LoginActivity.class);
                 startActivity(intent1);
                 finish();
             }
         });

    }


    private String minute(String result){
        int m, s;
        String temp;
        String time_s;
        temp = result.substring(0,result.length()-3);
        int a = Integer.parseInt(temp);
        if(a<60){
            time_s = ""+a+"초";
        } else{
            m = a/60;
            s = a%60;
            time_s = ""+m+"분 "+s+"초";
        }


        return time_s;
    }




}
