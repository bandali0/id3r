package org.aminb.id3r.fragment;

import android.support.annotation.Nullable;
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
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import org.aminb.id3r.R;
import org.aminb.id3r.activity.MainActivity;

import java.io.IOException;

public class MainFragment extends Fragment {

    private FloatLabeledEditText title, artist, album;
    private boolean normalMode = false;
    private Mp3File mp3File;
    
    public MainFragment() {}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (normalMode) {

            title = (FloatLabeledEditText) view.findViewById(R.id.id_title);
            artist = (FloatLabeledEditText) view.findViewById(R.id.id_artist);
            album = (FloatLabeledEditText) view.findViewById(R.id.id_album);

            setTextChangedListeners();

            ((MainActivity)getActivity()).setFABListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ID3v2 tags = mp3File.getId3v2Tag();
                    tags.setTitle(title.getTextString());
                    tags.setArtist(artist.getTextString());
                    tags.setAlbum(album.getTextString());
                    mp3File.setId3v2Tag(tags);
                    try {
                        String name = getActivity().getIntent().getData().getPath();
                        mp3File.save(name.substring(0,name.length()-4)+"wtags.mp3");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            });


            try {
                mp3File = new Mp3File(getActivity().getIntent().getData().getPath());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedTagException e) {
                e.printStackTrace();
            } catch (InvalidDataException e) {
                e.printStackTrace();
            }

            if (mp3File != null) {
                if (mp3File.hasId3v2Tag()) {
                    ID3v2 tags = mp3File.getId3v2Tag();
                    if (tags.getTitle() != null)
                        title.setText(tags.getTitle());
                    if (tags.getArtist() != null)
                        artist.setText(tags.getArtist());
                    if (tags.getAlbum() != null)
                        album.setText(tags.getAlbum());

                    // hack to make hints show up
                    title.requestFieldFocus();
                    artist.requestFieldFocus();
                    album.requestFieldFocus();
                    title.requestFieldFocus();
                }
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        if (getActivity().getIntent().getData() == null) // if opened from launcher
            return inflater.inflate(R.layout.fragment_placeholder, container, false);
        else { // if opened from 'share' menu, to open a file
            normalMode = true;
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
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
