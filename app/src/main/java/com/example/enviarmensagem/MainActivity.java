package com.example.enviarmensagem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonMensagem, buttonNavegador, buttonMapa, buttonRota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonMensagem = (Button) findViewById(R.id.btnMensagem);
        buttonNavegador = (Button) findViewById(R.id.btnNavegador);
        buttonMapa = (Button) findViewById(R.id.btnMapa);
        buttonRota = (Button) findViewById(R.id.btnRota);

        buttonNavegador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facens.br");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        buttonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uri uri = Uri.parse("geo:0,0?q=Rua+Jair+Salim+Junior+129");
                //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //startActivity(intent);
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        // obter a localização atual do usuário
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        // abrir o Google Maps com a localização atual do usuário
                        Uri uri = Uri.parse("geo:" + latitude + "," + longitude);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                        // remover as atualizações de localização para economizar bateria
                        locationManager.removeUpdates(this);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                    @Override
                    public void onProviderEnabled(String provider) {}

                    @Override
                    public void onProviderDisabled(String provider) {}
                };

                // solicitar permissão para acessar a localização do usuário
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    return;
                }

                // registrar o LocationListener para receber atualizações de localização
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        });

        buttonRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("google.navagation:q=Rua+Jair+Salim+Junior+129");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        buttonMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EnviarMensagemActivity.class);
                startActivity(intent);
            }
        });

    }
}