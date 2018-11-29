package com.gprovs.firebasedatabaseapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
TextView name,email,number;
Button save,view;
ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.adtname);
        email = findViewById(R.id.adtmail);
        number = findViewById(R.id.adtphone);
        save = findViewById(R.id.btnsave);
        view = findViewById(R.id.btnview);
        dialog = new ProgressDialog(this);
        dialog.setMessage("saving...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jina = name.getText().toString();
                String arafa = email.getText().toString();
                String simu = number.getText().toString();
                if(jina.isEmpty()||arafa.isEmpty()||simu.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fill all inputs", Toast.LENGTH_SHORT).show();
                }else {
                    long time = System.currentTimeMillis();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("watu/"+time);
                            Item x = new Item(jina,arafa,simu);
                            dialog.show();
                            ref.setValue(x).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    dialog.dismiss();
                                    if(task.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                        name.setText("");
                                        email.setText("");
                                        number.setText("");
                                    }else{
                                        Toast.makeText(MainActivity.this, "Not saved", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
            }
        });
    view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent move = new Intent(MainActivity.this,UserActivity.class);
            startActivity(move);
        }
    });
    }
}
