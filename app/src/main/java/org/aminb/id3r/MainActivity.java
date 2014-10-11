package org.aminb.id3r;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import java.io.IOException;


public class MainActivity extends Activity {

    protected static MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        activity = this;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            Mp3File file = null;
            try {
                file = new Mp3File(activity.getIntent().getData().getPath());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedTagException e) {
                e.printStackTrace();
            } catch (InvalidDataException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e) {
                // TODO: placeholder layout
            }

            if (file != null) {
                if (file.hasId3v2Tag()) {
                    ID3v2 tags = file.getId3v2Tag();
                    if (tags.getTitle() != null)
                        ((FloatLabeledEditText)rootView.findViewById(R.id.id_title)).setText(tags.getTitle());
                    if (tags.getArtist() != null)
                        ((FloatLabeledEditText)rootView.findViewById(R.id.id_artist)).setText(tags.getArtist());
                    if (tags.getAlbum() != null)
                        ((FloatLabeledEditText)rootView.findViewById(R.id.id_album)).setText(tags.getAlbum());

                    // hack to make hints show up
                    ((FloatLabeledEditText)rootView.findViewById(R.id.id_artist)).requestFieldFocus();
                    ((FloatLabeledEditText)rootView.findViewById(R.id.id_album)).requestFieldFocus();
                    ((FloatLabeledEditText)rootView.findViewById(R.id.id_title)).requestFieldFocus();
                }
            }

            return rootView;
        }
    }
}
