package org.aminb.id3r.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import org.aminb.id3r.R;
import org.aminb.id3r.activity.MainActivity;

import java.io.IOException;

public class MainFragment extends Fragment {

    private FloatLabeledEditText title, artist, album;
    
    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        title = (FloatLabeledEditText) rootView.findViewById(R.id.id_title);
        artist = (FloatLabeledEditText) rootView.findViewById(R.id.id_artist);
        album = (FloatLabeledEditText) rootView.findViewById(R.id.id_album);

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

        setTextChangedListeners();

        return rootView;
    }

    private TextWatcher onTextChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            ((MainActivity)getActivity()).showFAB();
        }
    };

    private void setTextChangedListeners() {
        title.getEditText().addTextChangedListener(onTextChanged);
        artist.getEditText().addTextChangedListener(onTextChanged);
        album.getEditText().addTextChangedListener(onTextChanged);
    }
}
