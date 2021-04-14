package bin.data.Models;

import java.io.Serializable;

public class Model implements Serializable {
    private long id;

    public Model(){id++;}

    public Model(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
