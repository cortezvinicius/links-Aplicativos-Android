package com.cortezvinicius.exemplo.projeto_exemplo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.cortezvinicius.linksaplicativos.LinksAplicativos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button paginaWeb, whatsapp, email, maps, telefone, galeria, camera;
    private ImageView imageView;
    private Bitmap foto;
    private Bundle extras;
    private static final int SELECAO_GALERIA = 200;
    private static final int SELECAO_CAMERA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paginaWeb = findViewById(R.id.abrirPaginaWeb);
        whatsapp = findViewById(R.id.abriWhatsapp);
        email = findViewById(R.id.abrirEmail);
        maps = findViewById(R.id.maps);
        telefone = findViewById(R.id.telefone);
        galeria = findViewById(R.id.galeria);
        camera = findViewById(R.id.camera);
        imageView = findViewById(R.id.imageView);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},0);
        }

        final LinksAplicativos linksAplicativos = new LinksAplicativos(this);

        paginaWeb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                linksAplicativos.abrirPaginaWeb("https://www.google.com.br");
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linksAplicativos.abrirWhatsapp("3592610903", "Olá Mundo");
            }
        });

        email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String[] email = {"cortezvinicius881@gmail.com"};
                linksAplicativos.abrirEmail(email, "olá Mundo", "Título");
            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linksAplicativos.abrirMapas("-20.916493","-46.983915", false);
            }
        });

        telefone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                linksAplicativos.abrirChamada("992610903");
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linksAplicativos.abrirGaleria(SELECAO_GALERIA);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linksAplicativos.abrirCamera(SELECAO_CAMERA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            foto = null;
            try
            {
                switch (requestCode)
                {
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        foto = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                        break;
                    case SELECAO_CAMERA:
                        extras = data.getExtras();
                        foto = (Bitmap) extras.get("data");
                        break;
                }

                if (foto != null)
                {
                    imageView.setImageBitmap(foto);
                    Log.d("IMAGEM_VIEW", "Deu Certo");
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
