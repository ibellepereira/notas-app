package br.com.mytho.mobi.service;

import java.util.List;

import br.com.mytho.mobi.model.Event;
import br.com.mytho.mobi.oauth2.service.OAuth2BaseService;
import retrofit2.Call;
import retrofit2.http.GET;


public class EventBaseService extends OAuth2BaseService<EventBaseService.RetrofitEventService> {


    public interface RetrofitEventService {

        @GET("event")
        Call<List<Event>> list();

    }

}