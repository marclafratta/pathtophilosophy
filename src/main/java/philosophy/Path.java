package philosophy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Path {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @ElementCollection
    private List<String> pathList;
    private boolean foundPhilosophy = false;


    protected Path ()
    {
        // needed for persistence
    }

    public Path(ArrayList<String> pathList, boolean foundPhilosophy){
        this.pathList = pathList;
        this.foundPhilosophy = foundPhilosophy;
    }

    public List<String> getPathList() {
        return pathList;
    }

    public void setPathList(List<String> pathList) {
        this.pathList = pathList;
    }

    public boolean isFoundPhilosophy() {
        return foundPhilosophy;
    }

    public void setFoundPhilosophy(boolean foundPhilosophy) {
        this.foundPhilosophy = foundPhilosophy;
    }
}
