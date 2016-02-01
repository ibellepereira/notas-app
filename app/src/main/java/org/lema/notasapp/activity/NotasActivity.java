package org.lema.notasapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.lema.notasapp.R;
import org.lema.notasapp.adapter.MateriasAdapter;
import org.lema.notasapp.json.MateriaJsonConverter;
import org.lema.notasapp.modelo.Materia;

import java.util.List;


public class NotasActivity extends ActionBarActivity {

    private ListView mListaDeMaterias;
    private ImageHolder holder;
    private IProfile item;
    private AccountHeader headerResult;
    private Toolbar toolbar;
    private Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notas_fragment);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String caminhoFoto = intent.getStringExtra("caminhoFoto");
                Log.i("foto", "chegou sim " + caminhoFoto);


                Bitmap foto = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap reduzida = Bitmap.createScaledBitmap(foto, 100, 100, true);

                foto.recycle();
                item = new ProfileDrawerItem().withName("Leonardo Cordeiro")
                        .withEmail("1413331050")
                        .withIdentifier(123)
                        .withIcon(reduzida);

                headerResult.updateProfileByIdentifier(item);

            }
        }, new IntentFilter("foto"));

        toolbar = (Toolbar) findViewById(R.id.bar);
        setSupportActionBar(toolbar);

        mListaDeMaterias = (ListView) findViewById(R.id.lv_materias);

        String json = (String) getIntent().getSerializableExtra("materias");

        if(json != null) {
            List<Materia> materias = new MateriaJsonConverter(json).toList();

            MateriasAdapter materiasAdapter = new MateriasAdapter(this, materias);
            mListaDeMaterias.setAdapter(materiasAdapter);

            mListaDeMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Materia materia = (Materia) adapterView.getItemAtPosition(i);

                    String notas = "AV1: " + materia.getAv1() + "\n" +
                            "AV2: " + materia.getAv2() + "\n" +
                            "AV3: " + materia.getAv3() + "\n" +
                            "MÉDIA: " + materia.getMedia() + "\n";

                    Toast.makeText(NotasActivity.this, notas, Toast.LENGTH_LONG).show();

                }
            });
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.app_name);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withName(R.string.app_name);

        item = new ProfileDrawerItem().withName("Leonardo Cordeiro")
                .withEmail("1413331050")
                .withIdentifier(123)
                .withIcon(getResources().getDrawable(R.drawable.ic_sem_foto));

        this.holder = item.getIcon();

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorPrimary)
                .addProfiles(item)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withTranslucentStatusBar(true)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawerItem().withName(R.string.app_name)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return true;
                    }
                }).build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    public static Bitmap decodeSampledBitmapFromFile(String filename,
                                                     int reqWidth, int reqHeight) {
// First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options
                options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);
// Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
// Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
// BEGIN_INCLUDE (calculate_sample_size)
// Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            long totalPixels = width * height / inSampleSize;

            // Anything more than 2x the requested pixels we'll sample down further
            final long totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels > totalReqPixelsCap) {
                inSampleSize *= 2;
                totalPixels /= 2;
            }
        }
        return inSampleSize;
// END_INCLUDE (calculate_sample_size)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_logout) {
            Intent irParaPerfil = new Intent(this, PerfilActivity.class);
            startActivity(irParaPerfil);

        }
        return true;
    }

}
