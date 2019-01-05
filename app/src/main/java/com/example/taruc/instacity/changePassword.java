package com.example.taruc.instacity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.sql.Ref;
import java.util.HashMap;

public class changePassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button confirm;
    private EditText old_pass, new_pass, retype_pass;
    private DatabaseReference Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mAuth = FirebaseAuth.getInstance();

        old_pass = (EditText) findViewById(R.id.old_pass);
        new_pass = (EditText) findViewById(R.id.new_pass);
        retype_pass = (EditText) findViewById(R.id.retype_pass);
        confirm = (Button) findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdatePassword();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //editProfileActivity.this.finish();
        }
    }

    private void UpdatePassword() {
        final String Oldpass = old_pass.getText().toString();
        final String Newpass = new_pass.getText().toString();
        final String Newretypepass = retype_pass.getText().toString();
//        String current_user = mAuth.getCurrentUser().getUid();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        Ref = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        if(TextUtils.isEmpty(Oldpass)){
            Toast.makeText(this,"Please enter your old password...",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Newpass)){
            Toast.makeText(this,"Please enter your new password...",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Newretypepass)){
            Toast.makeText(this,"Please enter your confirm password...",Toast.LENGTH_SHORT).show();
        }else if(!Newpass.equals(Newretypepass)){
            Toast.makeText(this,"Your new password do not match with confirm new password...",Toast.LENGTH_SHORT).show();
        }else if (!Newretypepass.equals(Newpass)){
            Toast.makeText(this,"Your confirm new password do not match with new password...",Toast.LENGTH_SHORT).show();
        }else {
            AuthCredential credential = EmailAuthProvider.getCredential(email, Oldpass);
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(Newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            SendToUserProfile();
                                            Toast.makeText(changePassword.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(changePassword.this, "Something went Wrong, Please Type Again", Toast.LENGTH_SHORT).show();
                                            SendToUserProfile();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(changePassword.this, "Error auth failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void SendToUserProfile() {
        Intent setupIntent = new Intent(this,feed.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }

    public void backClick(View view) {
        Intent setupIntent = new Intent(this,editProfileOptionActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }
}
