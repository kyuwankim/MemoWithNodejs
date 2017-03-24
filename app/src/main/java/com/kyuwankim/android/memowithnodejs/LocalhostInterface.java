package com.kyuwankim.android.memowithnodejs;

import com.kyuwankim.android.memowithnodejs.domain.Data;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kimkyuwan on 2017. 3. 24..
 */

public interface LocalhostInterface {

    @GET("bbs")
    Call<Data> getData();

}
