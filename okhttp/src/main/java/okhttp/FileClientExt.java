package okhttp;

import common.file.FileClient;
import common.file.InfoResponse;
import common.file.UploadResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    @Multipart
    @POST("/upload")
    Call<UploadResponse> upload(@Part
                                List<MultipartBody.Part> parts);

    @GET("/v2/file/{id}/info")
    Call<InfoResponse> info_0(@Path("id") String id);
}
