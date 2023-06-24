package BusinessLogic;

import Model.Server;
import Model.Task;
import java.util.List;

public class Strategy {
    public void addTask(List<Server> sv, Task t){  //ShortestQueue
        Server shortest = null;  //initializez cea mai mica coada cu null
        int min_size = 1000000;
        for(Server s: sv){   // parcurg cozile
            if(s.getTasks().size() < min_size){   //verific size-ul fiecarei cozi si il compar cu minimul
                min_size = s.getTasks().size();
                shortest = s;
            }
        }
        shortest.addTask(t);  // adaug task-ul dorit in cea mai mica coada
    }
}
