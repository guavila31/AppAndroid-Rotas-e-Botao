package com.example.enviarmensagem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EnviarMensagemActivity extends AppCompatActivity {
    Button buttonVoltar, buttonEnviar, buttonDiscar1, buttonDiscar2;
    TextView textMsg, textCelular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_mensagem);
        buttonVoltar = (Button) findViewById(R.id.btnVoltar);
        buttonEnviar = (Button) findViewById(R.id.btnEnviar);
        buttonDiscar1 = (Button) findViewById(R.id.btnDiscar2);
        buttonDiscar2 = (Button) findViewById(R.id.btnDiscar1);
        textMsg = (TextView) findViewById(R.id.txtMensagem);
        textCelular = (TextView) findViewById(R.id.txtCelular);

        buttonDiscar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerLigacao(Intent.ACTION_CALL);
            }
        });

        buttonDiscar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerLigacao(Intent.ACTION_DIAL);
            }
        });

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, textMsg.getText().toString());
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void fazerLigacao(String acao){
        Uri uri = Uri.parse("tel:" + textCelular.getText().toString());
        Intent intent = new Intent(acao, uri);

        int permissao = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissao != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CALL_PHONE
            }, 1);
        }else{
            startActivity(intent);
        }
    }
}