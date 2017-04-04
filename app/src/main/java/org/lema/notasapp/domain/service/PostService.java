package org.lema.notasapp.domain.service;

import org.lema.notasapp.domain.model.Aluno;
import org.lema.notasapp.domain.model.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by igor on 08/03/17.
 */

public interface PostService {
    @GET("post")
    Call<Post> getPosts();
}
