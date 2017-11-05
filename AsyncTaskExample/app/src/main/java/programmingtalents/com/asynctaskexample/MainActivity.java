package programmingtalents.com.asynctaskexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mLoadImageButton;
    private Button mOtherFuctButton;
    private ProgressBar mProgressBar;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadImageButton= (Button) findViewById(R.id.loadBtn);
        mOtherFuctButton= (Button) findViewById(R.id.otherBtn);
        mImageView= (ImageView) findViewById(R.id.imageview);
        mProgressBar= (ProgressBar) findViewById(R.id.progress);

        mLoadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uncomment below code when you want to do long running operation in main thread.
               /* Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                //Adding some delay manually
                for(int i=1;i<11;i++){
                    sleep();
                }
                mImageView.setImageBitmap(bitmap);*/
                //Doing the same thing in background thread.
               LoadImageTask loadImageTask=new LoadImageTask();
                loadImageTask.execute(R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
            }
        });
        mOtherFuctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"I am working",Toast.LENGTH_SHORT).show();
            }
        });
    }

    class LoadImageTask extends AsyncTask<Integer,Integer,Bitmap>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),params[0]);
            for(int i=1;i<11;i++){         //20
                                            //100%  -100/20=5
                sleep();
                publishProgress(i*10);
            }

            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mProgressBar.setProgress(View.INVISIBLE);
            mImageView.setImageBitmap(bitmap);
        }

        private void sleep() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //Used for explicitly putting delay to test the behaviour
    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
