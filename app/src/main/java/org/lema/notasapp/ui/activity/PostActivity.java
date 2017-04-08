package org.lema.notasapp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.Autor;
import org.lema.notasapp.domain.model.Post;
import org.lema.notasapp.domain.service.PostService;
import org.lema.notasapp.infra.app.NotasAppAplication;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by Isabelle on 04/04/2017.
 */

public class PostActivity extends AppCompatActivity {
    private Post post;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;

    private int corToolBar;
    private TextView titulo;
    private ImageView capa;
    private TextView dataPostagem;
    private TextView texto;
    private Button likeButton;
    private TextView autorNome;
    private TextView autorDescricao;
    private ImageView autorFoto;

    public PostActivity(){
    }

    public PostActivity(Post post){
        this.post = post;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        preencheReferencias();

        valoresTeste();

        preparaToolbar();

        preencheValores();

    }

    private void preparaToolbar() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScrollSize = 0;

                if (maxScrollSize == 0)
                    maxScrollSize = appBarLayout.getTotalScrollRange();

                int currentScrollPercentage = (Math.abs(verticalOffset)) * 100 / maxScrollSize;

                if (currentScrollPercentage >= 99) {
                    if (isShow) {
                        isShow = false;
                        collapsingToolbarLayout.setTitle(post.getTitulo());

                        titulo.setVisibility(View.INVISIBLE);
                    }
                }

                if (currentScrollPercentage < 99) {
                    if (!isShow) {
                        isShow = true;
                        collapsingToolbarLayout.setTitle(" ");

                        titulo.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
/*
        if (! post.getLinkParaFoto().isEmpty()) {
           // capa.setVisibility(View.VISIBLE);
            new DownLoadImageTask(capa).execute(post.getLinkParaFoto());
        } else {
            capa.setBackgroundColor(corToolBar);
        }
        */
    }

    private void preencheReferencias(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.post_collapsing);
        appBarLayout = (AppBarLayout) findViewById(R.id.post_appbar);

        titulo = (TextView) findViewById(R.id.post_titulo);
        capa = (ImageView) findViewById(R.id.post_foto);
        dataPostagem = (TextView) findViewById(R.id.post_dataPostagem);
        texto = (TextView) findViewById(R.id.post_texto);
        likeButton = (Button) findViewById(R.id.like_button);
        likeButton.setOnClickListener(likeButtonOnClickListener);
        autorNome = (TextView) findViewById(R.id.autor_nome);
        autorFoto = (ImageView) findViewById(R.id.autor_foto);
        autorDescricao = (TextView) findViewById(R.id.autor_descricao);
    }

    private void preencheValores() {
        titulo.setText(post.getTitulo());
        dataPostagem.setText(post.getDataPostagem().toString());
        texto.setText(post.getTexto());
        autorNome.setText(post.getAutor().getNome());
        autorDescricao.setText(post.getAutor().getDescricao());
        new DownLoadImageTask(autorFoto).execute(post.getAutor().getLinkParaFoto());
    }

    private void valoresTeste(){
        Autor autor = new Autor();
        autor.setNome("Caio Castro");
        autor.setDescricao("Aluno do sexto período do curso de Engenharia de Produção na UEZO, voluntário na UEZO Jr. Consultoria e redator no NOTAS APP.");
        autor.setLinkParaFoto("http://www.1jour1actu.com/wp-content/uploads/MOANA_203.7_066.00_0022_cmyk-970x647.jpg");

        Date date = new Date();

        post = new Post();
        post.setLinkParaFoto(null);
        post.setTitulo("PROF. ALESSANDRO KAPPEL JORDÃO CONTEMPLADO NO EDITAL APQ1/2016");
        post.setAutor(autor);
        post.setDataPostagem(date);

        post.setTexto("\tLorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                "\t\tIn mollis est vitae erat laoreet consectetur. Mauris dui mauris,\n" +
                "\t\tdictum vel egestas vel, porta sed ante. Phasellus congue viverra ipsum,\n" +
                "\t\tin elementum arcu ultricies nec.Morbi tincidunt nisl lacinia, placerat nisi eget, luctus tortor.\n" +
                "\t\tMorbi efficitur dictum leo, sit amet volutpat lorem sodales nec. Quisque et erat et turpis fringilla condimentum.\n" +
                "\t\tLorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris auctor vulputate libero vitae iaculis.\n" +
                "\t\tCurabitur id blandit quam. Praesent massa ante, pulvinar vitae sagittis commodo, scelerisque porttitor eros.\n" +
                "\t\tMauris suscipit a enim nec congue. Mauris tristique cursus aliquet. Nunc quis hendrerit nunc. Praesent tristique tellus purus,\n" +
                "\t\ta pretium ex gravida id. Cras laoreet malesuada nulla, at bibendum metus ultricies a. Fusce eu ornare arcu. Proin ut molestie est.\n" +
                "\t\tVestibulum quis vehicula erat. Nam sed tempus nibh. Sed tortor ante, maximus venenatis odio sed, ultricies maximus lectus.\n" +
                "\t\tAliquam sed volutpat ligula. Integer bibendum, magna ac lacinia suscipit,\n" +
                "\t\trisus libero rutrum justo, sed tempus augue tellus eget est. Curabitur convallis augue nisl,\n" +
                "\t\tid efficitur urna posuere faucibus. Ut sed egestas nulla, eu finibus metus. Nunc a arcu ante.\n" +
                "\t\tAenean ullamcorper dolor non condimentum congue. Aenean mauris nunc, pellentesque at placerat vitae,\n" +
                "\t\taliquet quis orci. Curabitur maximus augue ut bibendum sagittis.");

    }

    private View.OnClickListener likeButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public void setPost(Post post){
        this.post = post;
    }

    public void setCorToolBar(int corToolBar){
        this.corToolBar = corToolBar;
    }

    /*transforma a url em bitmap*/
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

}