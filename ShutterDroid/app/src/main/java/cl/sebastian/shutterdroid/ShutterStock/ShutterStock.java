package cl.sebastian.shutterdroid.ShutterStock;

import android.util.Base64;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by sebalegutie on 16-03-15.
 */
public class ShutterStock {
    static final String API_URL = "https://api.shutterstock.com/v2";
    static final String CLIENT_ID="d5015946b5d18ec85f2c";
    static final String CLIENT_SECRET="253385a5fb53081766056e0cf7fbaf5e3ef8e08a";
    static final String AUTH_INFO = CLIENT_ID+":"+CLIENT_SECRET;
    static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setEndpoint(API_URL)
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    String auth = "Basic "+ Base64.encodeToString(AUTH_INFO.getBytes(),Base64.NO_WRAP);
                    request.addHeader("Authorization",auth);
                }
            })
            .build();

    static final ShutterStockService SERVICE = REST_ADAPTER.create(ShutterStockService.class);

    public static ShutterStockService getService() {
        return SERVICE;
    }

    public static void search(String query, Callback<List<Image>> cb){
        SERVICE.search(query, new ImageCallback(cb));
    }

    public static void getRecent(String date, Callback<List<Image>> cb){
        SERVICE.getRecent(date, new ImageCallback(cb));
    }
    private static class ImageCallback implements Callback<Response>{
        Callback<List<Image>> cb;
        ImageCallback(Callback<List<Image>> cb){
            this.cb = cb;
        }

        @Override
        public void success(Response response, retrofit.client.Response response2) {
            cb.success(response.data, response2);
        }

        @Override
        public void failure(RetrofitError error) {
            cb.failure(error);

        }

    }

}
