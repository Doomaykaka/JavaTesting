package retrofit.requests;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface GoogleRequests {
    @GET("/")
    Call<ResponseBody> getBasePage();
    
    @GET("/search")
    Call<ResponseBody> search(@Query("q") String find);
}
