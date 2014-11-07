package org.aminb.id3r.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mpatric.mp3agic.ID3v2;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import org.aminb.id3r.R;
import org.aminb.id3r.activity.MainActivity;
import org.aminb.id3r.util.File;


public class MainFragment extends Fragment {

    private FloatLabeledEditText title, artist, album;
    private boolean normalMode = false;
    private File file;
    private ID3v2 tags;
    
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
                    tags.setTitle(title.getTextString());
                    tags.setArtist(artist.getTextString());
                    tags.setAlbum(album.getTextString());
                    file.setTags(tags);
                    if (file.save(getActivity()))
                        Toast.makeText(getActivity(), "Save Successful :)", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "Save Unsuccessful :(", Toast.LENGTH_SHORT).show();
                }
            });

            file = new File(getActivity().getIntent().getData().getPath());
            tags = file.getTags();

            if (tags != null) {
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
