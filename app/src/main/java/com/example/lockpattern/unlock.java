package com.example.lockpattern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import io.paperdb.Paper;

public class unlock extends AppCompatActivity {

    String save_pattern_key = "patron_codigo";
    String final_pattern = "";
    PatternLockView mPatternLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);
        if (save_pattern!=null && !save_pattern.equals("null")){
            setContentView(R.layout.activity_unlock);
            mPatternLockView = (PatternLockView)findViewById(R.id.pattern_lock_view);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(mPatternLockView,pattern);
                    if (final_pattern.equals(save_pattern)){
                        Toast.makeText(unlock.this,"Patrón Correcta!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(unlock.this,Formulario.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(unlock.this,"Patrón Incorrecto!",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCleared() {

                }
            });
        }
    }
}