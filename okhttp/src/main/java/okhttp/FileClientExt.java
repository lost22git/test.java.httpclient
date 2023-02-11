package okhttp;

import common.file.FileClient;
import common.file.InfoResponse;
import common.file.UploadResponse;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

public interface FileClientExt extends FileClient {

    @Override
    default UploadResponse upload(InputStream inputStream) throws IOException {
        var bs = ByteString.read(inputStream, inputStream.available());
        inputStream.close();
        var parts = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", "test.png",
                RequestBody.create(MediaType.parse("image/png"), bs)
            ).build()
            .parts();

        return upload(parts).execute().body();
    }

    @Override
    default InfoResponse info(String id) {
        try {
            return info_0(id).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default Optional<URI> get_download_uri(String id) {
        var httpClient = new OkHttpClient.Builder()
            .proxy(FileClient.proxy)
            .addInterceptor(HttpLog.INSTANCE.createInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

        var uri = FileClient.resolve(id);
        var request = new Request.Builder()
            .url(uri.toString())
            .get()
            .build();
        String html = null;
        try {
            var body = httpClient.newCall(request).execute().body();
            if (body != null) {
                html = body.string();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return FileClient.parse_download_uri(html);
    }

    @Override
    default InputStream download(URI uri) {
        var httpClient = new OkHttpClient.Builder()
            .proxy(FileClient.proxy)
            .addInterceptor(HttpLog.INSTANCE.createInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

        var request = new Request.Builder()
            .url(uri.toString())
            .build();
        try {
            var body = httpClient.newCall(request).execute().body();
            if (body == null) return InputStream.nullInputStream();
            return body.byteStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Multipart
    @POST("/upload")
    Call<UploadResponse> upload(@Part
                                List<MultipartBody.Part> parts);

    @GET("/v2/file/{id}/info")
    Call<InfoResponse> info_0(@Path("id") String id);
}
