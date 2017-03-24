package com.kyuwankim.android.memowithnodejs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kyuwankim.android.memowithnodejs.domain.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
    }

    public void getData(){


        // 1. 레트로핏을 생성하고
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.85:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LocalhostInterface service = retrofit.create(LocalhostInterface.class);

        // 3. 데이터를 가져온다
        Call<Data> result = service.getData();

        // 4. 데이터를 가져오는 부분은 네트웍을 통해서 오기 때문에 비동기 처리된다.
        result.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                // 값이 정상적으로 리턴됬을 경우
                if(response.isSuccessful()){
                    Data data = response.body(); // 원래 반환값인 jsonString 이 Data 클래스로 변환되어 리턴된다.
                    DataStore dataStore = DataStore.getInstance();
                    dataStore.setDatas(data.getData());

                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    startActivity(intent);

                    finish();

                }else{
                    Log.e("Retrofit", response.message()); // 정상적이지 않을경우 message에 오류내용이 담겨 온다
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e("Retrofit", t.getMessage());
            }
        });

    }
}
