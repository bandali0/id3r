package org.aminb.id3r.util;

import android.os.AsyncTask;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class File {
    class GetTags extends AsyncTask<String, Void, ArrayList<Object>> {

        @Override
        protected ArrayList<Object> doInBackground(String... strings) {
            Mp3File file = null;
            try {
                file = new Mp3File(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedTagException e) {
                e.printStackTrace();
            } catch (InvalidDataException e) {
                e.printStackTrace();
            }

            ArrayList<Object> list = new ArrayList<Object>();

            if (file != null) {
                list.add(file);
                list.add(file.getId3v2Tag());
                return list;
            }

            else
                return null;
        }
    }

    class SaveFile extends AsyncTask<Mp3File, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Mp3File... mp3Files) {
            try {
                String tmp = fileName + "tmp";
                mp3Files[0].save(tmp);
                java.io.File ioFile = new java.io.File(tmp);
                boolean deleted = new java.io.File(fileName).delete();
                if (deleted)
                    return ioFile.renameTo(new java.io.File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotSupportedException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    String fileName;
    Mp3File file;

    public File(String name) {
        fileName = name;
    }

    public ID3v2 getTags() {
        try {
            ArrayList<Object> result = new GetTags().execute(fileName).get();
            file = (Mp3File)result.get(0);
            return (ID3v2)result.get(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setTags(ID3v2 tags) {
        file.setId3v2Tag(tags);
    }

    public boolean save() {
        try {
            return new SaveFile().execute(file).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }
}
