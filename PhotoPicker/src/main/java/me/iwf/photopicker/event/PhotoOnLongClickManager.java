package me.iwf.photopicker.event;

/**
 * Created by q805699513 on 2017/1/22.
 */

public class PhotoOnLongClickManager {
    private static PhotoOnLongClick listener;
    private static PhotoOnLongClickManager photoOnLongClickManager;
    public static PhotoOnLongClickManager getInstance() {

        if (photoOnLongClickManager == null) {
            photoOnLongClickManager = new PhotoOnLongClickManager();
        }
        return photoOnLongClickManager;
    }

    public void setOnLongClickListener(PhotoOnLongClick nm) {
//        if (listener==null){
            listener = nm;
//        }
    }

    public void setOnLongClick(int position, String path) {
        if (listener!=null){
            listener.sendOnLongClick(position, path);
        }
    }
}