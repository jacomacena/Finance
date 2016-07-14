package com.jcdev.finance.Authentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jcdev.finance.R;

public class Register extends AppCompatActivity {

    protected EditText mNome;
    protected EditText mEmail;
    protected EditText mSenha;
    protected Button btnCriar;
    protected Button btnLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Iniciando Variaveis
        mNome = (EditText) findViewById(R.id.nomeRegistro);
        mEmail = (EditText) findViewById(R.id.emailRegistro);
        mSenha = (EditText) findViewById(R.id.senhaRegistro);
        btnCriar = (Button) findViewById(R.id.btnRegistro);
        btnLimpar = (Button) findViewById(R.id.btnLimpar);

        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Criar conta do usuario no banco local e acessar o Main do aplicativo

            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNome.setText("");
                mEmail.setText("");
                mSenha.setText("");
            }
        });


    }

    //ação do botão de registro
}
