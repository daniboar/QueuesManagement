package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategie = new Strategy();

    public Scheduler(int nrsv, int maxTasksPerServer){
        servers = new ArrayList<>();
        this.maxNoServers = nrsv;
        this.maxTasksPerServer = maxTasksPerServer;
        for(int i = 0; i < maxNoServers; i++){
            Server s = new Server(maxTasksPerServer); // creez obiect de tip server
            servers.add(s); //adaug obiectul la lista
            Thread thread = new Thread(servers.get(i)); // creez thread de tip server
            thread.start();   //pornim thread-ul
        }
    }

    public void dispatchTask(Task t){
        strategie.addTask(servers, t);   //call the strategy addTask method
    }

    public List<Server> getServers(){
        return servers;
    }  //returnez cozile
}
