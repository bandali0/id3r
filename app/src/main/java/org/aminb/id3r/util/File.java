package org.aminb.id3r.util;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.IOException;

public class File {

    String fileName;
    Mp3File file;

    public File(String name) {
        fileName = name;
    }

    public ID3v2 getTags() {
        Mp3File file = null;
        try {
            file = new Mp3File(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
        this.file = file;
        if (file != null) {
            return file.getId3v2Tag();
        }
        return null;
    }

    public void setTags(ID3v2 tags) {
        file.setId3v2Tag(tags);
    }

    public boolean save() {
        try {
            String tmp = fileName + "tmp";
            file.save(tmp);
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
