package org.lema.notasapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.Autor;
import org.lema.notasapp.domain.model.Post;

import java.text.SimpleDateFormat;
import java.util.Date;


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

        buscaParametros();

        valoresTeste();

        preparaToolbar();

        preencheValores();

    }

    private void buscaParametros() {
        post = (Post) getIntent().getSerializableExtra("post");
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

                        dataPostagem.setVisibility(View.INVISIBLE);
                        titulo.setVisibility(View.INVISIBLE);
                    }
                }

                if (currentScrollPercentage < 99) {
                    if (!isShow) {
                        isShow = true;
                        collapsingToolbarLayout.setTitle(" ");

                        dataPostagem.setVisibility(View.VISIBLE);
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
        autorNome = (TextView) findViewById(R.id.autor_nome);
        autorFoto = (ImageView) findViewById(R.id.autor_foto);
        autorDescricao = (TextView) findViewById(R.id.autor_descricao);
    }

    private void preencheValores() {
        titulo.setText(post.getTitulo());
        if (post.getDataPostagem() != null) {
            SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
            dataPostagem.setText("Postado em " + data.format(post.getDataPostagem()).toString() + " às " + hora.format(post.getDataPostagem()).toString());
        }
        texto.setText(post.getTexto());
        autorNome.setText(post.getAutor().getNome());
        autorDescricao.setText(post.getAutor().getDescricao());
        Picasso
                .with(this)
                .load(post.getAutor() != null ? post.getAutor().getLinkParaFoto() : null)
                .fit()
                .into(autorFoto);
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

    public void setPost(Post post){
        this.post = post;
    }

    public void setCorToolBar(int corToolBar){
        this.corToolBar = corToolBar;
    }

}
