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

public class MainFragment extends Fragment {
    
    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

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
