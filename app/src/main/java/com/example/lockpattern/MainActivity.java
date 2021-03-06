package com.example.lockpattern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    String save_pattern_key = "patron_codigo";
    String final_pattern = "";
    PatternLockView mPatternLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        Toast.makeText(MainActivity.this,"Patrón Correcto!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,Formulario.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this,"Patrón Incorrecto!",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCleared() {

                }
            });
        }else{
            setContentView(R.layout.activity_main);
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
                }

                @Override
                public void onCleared() {

                }
            });
            Button btnGuardarPatron = (Button)findViewById(R.id.btnGuardarPatron);
            btnGuardarPatron.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().write(save_pattern_key,final_pattern);
                    Toast.makeText(MainActivity.this, "Patrón guardado correctamente!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,unlock.class);
                    startActivity(intent);
                }
            });
        }
    }
}