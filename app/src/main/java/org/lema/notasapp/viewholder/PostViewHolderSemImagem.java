package org.lema.notasapp.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.Post;

/**
 * Created by igor on 08/03/17.
 */

public class PostViewHolderSemImagem extends RecyclerView.ViewHolder {

    private Activity activity;

    final TextView mensagem;
    final TextView autor;

    public PostViewHolderSemImagem(Activity activity, View itemView) {
        super(itemView);

        this.activity = activity;
        mensagem = (TextView) itemView.findViewById(R.id.tv_feed_mensagem);
        autor = (TextView) itemView.findViewById(R.id.tv_feed_autor);

    }

    public void onBind(final Post post) {
        mensagem.setText(post.getTitulo());
        autor.setText(post.getAutor().getNome());
    }
}
