package visual.camp.sample.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import visual.camp.sample.app.R;

public class ResultActivity extends AppCompatActivity {
    private Long time;
    TextView txt_time;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txt_time = (TextView) findViewById(R.id.txt_time);

        Intent intent = getIntent();
         time = intent.getLongExtra("time",0);

         txt_time.setText(time.toString());




    }
}
