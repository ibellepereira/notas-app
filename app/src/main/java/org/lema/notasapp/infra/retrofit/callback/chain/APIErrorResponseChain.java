package org.lema.notasapp.infra.retrofit.callback.chain;

import android.util.Log;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import org.lema.notasapp.infra.RetrofitUtils;
import org.lema.notasapp.infra.error.APIError;
import org.lema.notasapp.infra.event.APIErrorEvent;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Created by leonardocordeiro on 29/11/16.
 */
public class APIErrorResponseChain implements ResponseChain {
    private ResponseChain next;

    @Override
    public void handler(Call call, Response response) {
        ResponseBody responseBody = response.errorBody();

        try {
            Converter<ResponseBody, APIError> converter = RetrofitUtils.getInstance().responseBodyConverter(APIError.class, new Annotation[0]);
            APIError error = converter.convert(responseBody);
            EventBus.getDefault().post(new APIErrorEvent(error));
        } catch (IOException e) {
            Log.e("exception", e.toString() + "handle() - BoletimConverter");
        }
    }

    @Override
    public void setNext(ResponseChain next) {
        this.next = next;
    }
}
