package org.lema.notasapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.Post;
import org.lema.notasapp.viewholder.PostViewHolderComImagem;
import org.lema.notasapp.viewholder.PostViewHolderSemImagem;

import java.util.List;

/**
 * Created by igor on 08/03/17.
 */

public class FeedAdapter extends RecyclerView.Adapter {

    private List<Post> posts;
    private Activity activity;
    private final int COM_IMAGEM = 1, SEM_IMAGEM = 0;

    public FeedAdapter(Activity activity, List<Post> posts) {
        this.activity = activity;
        this.posts = posts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(activity);

        switch (viewType) {
            case SEM_IMAGEM:
                View v1 = inflater.inflate(R.layout.item_list_feed_sem_foto, parent, false);
                viewHolder = new PostViewHolderSemImagem(activity, v1);
                break;
            case COM_IMAGEM:
                View v3 = inflater.inflate(R.layout.item_list_feed_com_foto, parent, false);
                viewHolder = new PostViewHolderComImagem(activity, v3);
                break;
            default:
                return null;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case SEM_IMAGEM:
                PostViewHolderSemImagem postViewHolderSemImagem = (PostViewHolderSemImagem) holder;
                postViewHolderSemImagem.onBind(posts.get(position));
                break;
            case COM_IMAGEM:
                PostViewHolderComImagem postViewHolderComImagem = (PostViewHolderComImagem) holder;
                postViewHolderComImagem.onBind(posts.get(position));
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(posts.get(position).getLinkParaFoto() == null) {
            return SEM_IMAGEM;
        } else {
            return COM_IMAGEM;
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
