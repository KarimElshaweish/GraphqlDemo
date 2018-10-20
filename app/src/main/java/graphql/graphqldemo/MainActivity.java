package graphql.graphqldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    TextView text;
    EditText nameText;
    String resp;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.Text);
        nameText=findViewById(R.id.nameText);
        pb=findViewById(R.id.pb);
        GetUser();
    }
    private void GetUser(){
        MyApolloClient.getMyApolloClient().query(
                AllusersGraphqlQuery.builder().build()).enqueue(new ApolloCall.Callback<AllusersGraphqlQuery.Data>() {
            @Override
            public void onResponse(@NotNull final Response<AllusersGraphqlQuery.Data> response) {
                Log.d("dataResp","respones:"+response.data().allUsers().get(0).id.toString());
                resp=response.data().allUsers.get(0).id.toString();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(resp);
                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
    }
    public void post(View v){
        pb.setVisibility(View.VISIBLE);
     String name=nameText.getText().toString();
     MyApolloClient.getMyApolloClient().mutate(NewUserMutation.builder().name(name).build())
                    .enqueue(new ApolloCall.Callback<NewUserMutation.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<NewUserMutation.Data> response) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pb.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "name added suceefully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {

                        }
                    });
    }
}
