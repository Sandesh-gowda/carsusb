package sample.com.carusb.model;

import java.io.Serializable;

/**
 * Created by Dell on 4/18/2016.
 */
public class Dialog_Models implements Serializable {
    private String id,title;

    public Dialog_Models(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
