package okhttp;

import common.mail.MailClient;
import common.mail.MailMsg;
import common.mail.MailMsgDetails;
import common.util.LazyValue;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface MailClientExt extends MailClient {

    LazyValue<OkHttpClient> inner_client = LazyValue.create(() -> {
        return new OkHttpClient.Builder()
//            .proxy(FileClient.proxy)
            .addInterceptor(HttpLog.INSTANCE.createInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();
    });

    @Override
    default String get_auth_token(String mail) {
        Response<Void> res = null;
        try {
            res = get_auth_token_0(mail).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return res.headers().values("set-cookie")
            .stream()
            .filter(v -> v.contains("auth_token="))
            .flatMap(v -> Arrays.stream(v.split(";")))
            .filter(v -> v.startsWith("auth_token="))
            .map(v -> v.substring("auth_token=".length()).trim())
            .findFirst()
            .orElse(null);
    }

    @Override
    default List<MailMsg> get_msg_list(String mail,
                                       String auth_token) {
        var token = auth_token.startsWith("Bearer ") ? auth_token : "Bearer " + auth_token;
        var referer = "https://mail.td/zh/mail/" + mail;
        try {
            return get_msg_list_0(mail, token, referer).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default MailMsgDetails get_msg_details(String mail,
                                           String auth_token,
                                           String id) {

        var token = auth_token.startsWith("Bearer ") ? auth_token : "Bearer " + auth_token;
        var referer = "https://mail.td/zh/mail/" + mail;
        try {
            return get_msg_details_0(mail, id, token, referer).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GET("/zh/mail/{mail}")
    Call<Void> get_auth_token_0(@Path("mail") String mail);


    @GET("/api/api/v1/mailbox/{mail}")
    Call<List<MailMsg>> get_msg_list_0(@Path("mail") String mail,
                                       @Header("authorization") String auth_token,
                                       @Header("referer") String referer);

    @GET("/api/api/v1/mailbox/{mail}/{id}")
    Call<MailMsgDetails> get_msg_details_0(@Path("mail") String mail,
                                           @Path("id") String id,
                                           @Header("authorization") String auth_token,
                                           @Header("referer") String referer);
}
