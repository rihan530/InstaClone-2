package com.coolwhite.instaclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.coolwhite.instaclone.databinding.ActivityLoginBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;

import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements OnConnectionFailedListener, View.OnClickListener {

    // 구글 로그인
    private static final int RC_SIGN_IN = 9001; // Intent Request ID
    private FirebaseAuth auth;
    private GoogleApi mGoogleApi;
    // 바인딩
    private ActivityLoginBinding binding;
    // 페이스북 로그인
    private CallbackManager callbackManager;
    // 로그인 리스너
    FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Firebase 로그인 통합 관리하는 Object 생성
        auth = FirebaseAuth.getInstance();

    }

    // 이메일 회원가입 및 로그인
    private void createAndLoginEmail() {
        auth.createUserWithEmailAndPassword(binding.emailEdittext.getText().toString(), binding.passwordEdittext.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                        // 비밀번호를 8자리 이상 입력하지 않았을 경우
                        else if(binding.passwordEdittext.getText().toString().length() < 8) {
                            binding.progressBar.setVisibility(View.GONE);

                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        // 아이디가 있을 경우엔 바로 로그인
                        else {
                            signinEmail();
                        }
                    }
                });
    }

    // 로그인
    private void signinEmail() {
        auth.signInWithEmailAndPassword(binding.emailEdittext.getText().toString(), binding.passwordEdittext.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // 비밀번호 불일치
                if(!task.isSuccessful()) {
                    binding.progressBar.setVisibility(View.GONE);

                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}