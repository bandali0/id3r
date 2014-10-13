package org.aminb.id3r.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import org.aminb.id3r.R;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainFragment extends Fragment {

    public MainFragment() {}

    @InjectView(R.id.id_title)  FloatLabeledEditText title;
    @InjectView(R.id.id_artist) FloatLabeledEditText artist;
    @InjectView(R.id.id_album)  FloatLabeledEditText album;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);

        Mp3File file = null;
        try {
            file = new Mp3File(getActivity().getIntent().getData().getPath());
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
                    title.setText(tags.getTitle());
                if (tags.getArtist() != null)
                    artist.setText(tags.getArtist());
                if (tags.getAlbum() != null)
                    album.setText(tags.getAlbum());

                // hack to make hints show up
                artist.requestFieldFocus();
                album.requestFieldFocus();
                title.requestFieldFocus();
            }
        }

        return rootView;
    }
}
