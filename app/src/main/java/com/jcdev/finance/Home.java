package com.jcdev.finance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.jcdev.finance.Authentication.Login;
import com.jcdev.finance.Credito.Credito;
import com.jcdev.finance.Debito.Debito;

public class Home extends AppCompatActivity {

    protected AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* ************************** AdMod Google ************************** */
        adView = new AdView(this);
        adView.setAdUnitId(String.valueOf(R.string.banner_home));
        adView.setAdSize(AdSize.BANNER);

        // Recuperando o layout onde o anúncio vai ser exibido
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.relativeBannerHome);

        if (layout != null  ) {
            layout.addView(adView);
        }

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(String.valueOf(R.string.DEVICE_ID_TEST))
                .build();

        adView.loadAd(adRequest);

        /* ****************************************************************** */
    }

    @Override
    protected void onPause() {
        //Pausando o AdView ao pausar a activity
        adView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Resumindo o AdView ao resumir a activity
        adView.resume();
    }

    @Override
    protected void onDestroy() {
        //Destruindo o AdView ao destruir a activity
        adView.destroy();
        super.onDestroy();
    }


    //setando opção ao clicar no Layout dos Creditos
    public void Credito (View view){
        Intent btCredito = new Intent(Home.this, Credito.class);
        startActivity(btCredito);

    }

    //setando opção ao clicar no Layout dos Debitos
    public void Debito (View view){
        Intent btDebito= new Intent(Home.this, Debito.class);
        startActivity(btDebito);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    //implementar o logout
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.menuLogout:

                Intent logout = new Intent(this, Login.class);
                startActivity(logout);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
