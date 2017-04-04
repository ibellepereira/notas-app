package org.lema.notasapp.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.Post;

/**
 * Created by igor on 08/03/17.
 */

public class PostViewHolderComImagem extends RecyclerView.ViewHolder {

    private Activity activity;

    final TextView mensagem;
    final TextView autor;
    final ImageView imagem;

    public PostViewHolderComImagem(Activity activity, View itemView) {
        super(itemView);

        this.activity = activity;
        mensagem = (TextView) itemView.findViewById(R.id.tv_feed_mensagem);
        autor = (TextView) itemView.findViewById(R.id.tv_feed_autor);
        imagem = (ImageView) itemView.findViewById(R.id.iv_feed_imagem);

    }

    public void onBind(final Post post) {
        mensagem.setText(String.valueOf(post.getTexto()));
        autor.setText(String.valueOf(post.getAutor().getNome()));
        Picasso.with(activity)
                .load(post.getLinkParaFoto())
                .into(imagem);
    }
}
