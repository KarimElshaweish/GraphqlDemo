package graphql.graphqldemo;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MyApolloClient {
    private static final String BASE_URL="https://api.graph.cool/simple/v1/cjnf0cybc2t110184ieyneqmd";
    public static ApolloClient myApolloClient;

    public static ApolloClient getMyApolloClient(){
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        myApolloClient=ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        return myApolloClient;
    }

}
