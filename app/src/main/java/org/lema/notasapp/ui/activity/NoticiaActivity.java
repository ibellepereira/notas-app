package org.lema.notasapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.lema.notasapp.R;

import java.util.Random;

/**
 * Created by Isabelle on 12/03/2017.
 */

public class NoticiaActivity extends AppCompatActivity {
    private boolean hasImagemCapa = false;
    private ImageView imagemCapa;
    private TextView title;
    private TextView article;
    private TextView date;
    private TextView author;
    private TextView authorDescription;
    private ImageView authorIcon;
    private int toolbarColor;
    private Button likeButton;
    private int numlikes = 32;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticia_activity);

        setReferences();
        setContent();

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.noticia_collapsing);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.noticia_appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.activity_post_title));
                    title.setVisibility(View.INVISIBLE);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    title.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });

        if(hasImagemCapa == false) {
            this.toolbarColor = ContextCompat.getColor(getBaseContext(), getRandColor());
            imagemCapa.setColorFilter(this.toolbarColor);
        }
        else{
            imagemCapa.setBackground(getResources().getDrawable(R.drawable.logo_uezo));
        }
        //collapsingToolbarLayout.setContentScrimColor(this.toolbarColor);

        likeButton.setOnClickListener(likeButtonOnClickListener);
    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, NoticiaActivity.class));
    }

    private View.OnClickListener likeButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView likes = (TextView) findViewById(R.id.likes);
            //numlikes = Integer.parseInt(likes.getText().toString());

            if (likeButton.getBackground().getConstantState().equals
                    (getResources().getDrawable(R.drawable.heart_liked).getConstantState())) {
                likeButton.setBackgroundResource(R.drawable.heart_disliked);
                if (numlikes > 0 )
                    numlikes--;
            }
            else {
                likeButton.setBackgroundResource(R.drawable.heart_liked);
                numlikes++;
            }

            likes.setText(String.valueOf(numlikes));
            if (numlikes <= 0)
                likes.setVisibility(View.INVISIBLE);
            else
                likes.setVisibility(View.VISIBLE);
        }

    };

    public static int getRandColor(){
        Random rand = new Random();
        int pick = rand.nextInt()%4;

        switch (pick){
            case 0:
                return R.color.color_1;
            case 1:
                return R.color.color_2;
            case 2:
                return R.color.color_3;
            default:
                return R.color.color_4;
        }
    }

    public void setReferences(){
        title = (TextView) findViewById(R.id.noticia_title);
        date = (TextView) findViewById(R.id.noticia_data);
        article = (TextView) findViewById(R.id.noticia_post);
        author = (TextView) findViewById(R.id.autor_nome);
        authorDescription = (TextView) findViewById(R.id.autor_descricao);
        authorIcon= (ImageView) findViewById(R.id.autor_icon);
        imagemCapa = (ImageView) findViewById(R.id.noticia_capa);
        likeButton = (Button) findViewById(R.id.like_button);

        TextView likes = (TextView) findViewById(R.id.likes);
        likes.setText(String.valueOf(numlikes));
        if (numlikes <= 0)
            likes.setVisibility(View.INVISIBLE);
        else
            likes.setVisibility(View.VISIBLE);


    }

    public void setContent(){
        title.setText(R.string.activity_post_title);
        date.setText(R.string.activity_post_data);
        article.setText(R.string.article);
        author.setText(R.string.activity_post_autor);
        authorDescription.setText(R.string.activity_post_author_description);

        imagemCapa.setImageDrawable(getResources().getDrawable(R.drawable.ic_sem_foto, null));
        authorIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_sem_foto, null));
    }

}