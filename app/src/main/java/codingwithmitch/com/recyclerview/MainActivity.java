package codingwithmitch.com.recyclerview;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {




    private  String [] mDrawerTitle = {"Home", "About", "Reference", "Detail","Log out"};
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView mListView;
    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");




        initImageBitmaps();
        mDrawerLayout = findViewById(R.id.drawer_layout);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
//                mDrawerLayout,
//                R.string.open_drawer,
//                R.string.close_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = findViewById(R.id.drawer);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this,
                android.R.layout.simple_list_item_1,mDrawerTitle );
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) mListView.getItemAtPosition(position);

                mDrawerLayout.closeDrawers();
                Toast.makeText(getApplicationContext(),
                        "Go" + " to " + itemValue + " ...... ", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        switch (item.getItemId()) {
            case R.id.mnuNew:
                Toast.makeText(this, "New Profile!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mnuNew2:
                showNotification();
                return true;
            case R.id.showdialog:
                showdialog();
                return true;
            case R.id.mnuOpen:
                Toast.makeText(this, "UEFA Champions League!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mnuHelp:
                Toast.makeText(this, "Help!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://img.taste.com.au/decVmkuu/taste/2017/03/chocolate-meringue-layer-cake-124699-1.jpg");
        mNames.add("cake");

        mImageUrls.add("https://img.buzzfeed.com/video-api-prod/assets/8d9f5d51fa584fd6ad1c6ebe70c415d9/BFV23450_FB.jpg?output-format=webp&output-quality=60&resize=1000:*");
        mNames.add("ice cream");

        mImageUrls.add("https://www.handletheheat.com/wp-content/uploads/2018/02/BAKERY-STYLE-CHOCOLATE-CHIP-COOKIES-9-768x768.jpg");
        mNames.add("cookies");

        mImageUrls.add("https://www.stayathomemum.com.au/cache/555x700-0/wp-content/uploads/2015/06/feat-4.jpg");
        mNames.add("muffin");

        mImageUrls.add("https://marysvillelib.org/wp-content/uploads/2017/02/cupcake.jpg");
        mNames.add("cupcake");


        mImageUrls.add("http://static.kidspot.com.au/recipe_asset/3498/1930.jpg-20170607055634~q75,dx720y432u1r1gg,c--.jpg");
        mNames.add("lemon tart");

        mImageUrls.add("https://img.taste.com.au/nGjAovK2/taste/2016/11/layered-chocolate-mousse-cakes-101268-1.jpeg");
        mNames.add("Chocolate mousse ");

        mImageUrls.add("https://www.bettycrocker.com/recipes/lattice-peach-apple-pie/788d8941-0c2a-436a-9c30-8bd9ad630faf");
        mNames.add("Apple Pie");

        mImageUrls.add("https://images-gmi-pmc.edge-generalmills.com/d48f476f-ab97-4edf-8d5b-d617e5f261d0.jpg");
        mNames.add("Brownie");



        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void showNotification() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.onthehillkaron.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("GOD")
                        .setContentText("ตาย ห่า!!!!!")
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000, notification);
    }

    public void showdialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        final EditText mEmail = (EditText) mView.findViewById(R.id.etEmail);
        final EditText mPassword = (EditText) mView.findViewById(R.id.etPassword);
        Button mLogin = (Button) mView.findViewById(R.id.btnLogin);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEmail.getText ().toString ().isEmpty () && !mPassword.getText ().toString ().isEmpty ()) {
                    Toast.makeText (MainActivity.this,
                            R.string.success_login_msg,
                            Toast.LENGTH_SHORT).show ();
                    dialog.dismiss ();
                } else {
                    Toast.makeText (MainActivity.this,
                            R.string.error_login_msg,
                            Toast.LENGTH_SHORT).show ();
                }
            }


        });

    }
}

























