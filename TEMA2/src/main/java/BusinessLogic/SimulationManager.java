package BusinessLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable{
    public int timeLimit;
    public int maxArrivalTime;
    public int minArrivalTime;
    public int minServiceTime;
    public int maxServiceTime;
    public int numberOfServers;
    public int numberOfClients;
    public JTextArea afisor;
    private Scheduler scheduler;
    private List<Task> generatedTasks = new ArrayList<>();
    private FileWriter file;

    public SimulationManager(int time, int nrc, int nrs, int mina, int maxa, int mins, int maxs, JTextArea a){
        timeLimit = time;
        maxArrivalTime = maxa;
        minArrivalTime = mina;
        minServiceTime = mins;
        maxServiceTime = maxs;
        numberOfServers = nrs;
        numberOfClients = nrc;
        afisor = a;
        this.scheduler = new Scheduler(numberOfServers, numberOfClients); //initialize the scheduler
        generateNRandomTasks();   //generate nrofclients cliends using generatenrandomtasks()
        try{
           file = new FileWriter("Log.txt");  // incerc sa creez fisierul txt
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void sortByArrivalTime(List<Task> t){ //sortez lista dupa arrival time
        Collections.sort(t, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getArrivalTime() - o2.getArrivalTime();
            }
        });
    }
    private void generateNRandomTasks(){
        //generate n random tasks;
        int i;
        Random random = new Random();
        for(i = 0; i < numberOfClients; i++){
                int arrivalT = random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime; //random arrival time
                int Time = random.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime; //random service time
                generatedTasks.add(new Task(i+1, arrivalT, Time)); //adaug in lista de task-uri(clineti)
        }
        sortByArrivalTime(generatedTasks);    //sort list with respect to arrivalTime
        System.out.print("List of clients: ");
        afisor.append("List of clients: ");
        for(Task t :generatedTasks) { //parcurg clientii si ii afisez
            System.out.print("(" + t.getId() + "," + t.getArrivalTime() + "," + t.getServiceTime() + "); ");
            afisor.append(t + "; ");
        }
        afisor.append("\n\n");
        System.out.println();
    }

    public boolean end(List<Server> sv){ //verific daca se termina executia taskurilor si a cozilor
        if(generatedTasks.size() != 0)
            return false;
        else
            for(Server s: sv){
                if(s.getTasks().size() != 0)
                    return false;
            }
        return true;
    }

    public void prettyPrint(List<Server> sv, int currentTime){ //metoda de afisare in gui si in txt
        try{
            file.write("CurrentTime: " + currentTime + "\n");
            file.write("Waiting client:");
            afisor.append("CurrentTime: " + currentTime + "\n");
            afisor.append("Waiting clients:");
            for(Task t: generatedTasks){
                file.write(t.toString() + "; ");
                afisor.append(t + "; ");
            }
            afisor.append("\n");
            file.write("\n");
            for(int i = 0; i < numberOfServers; i++){
                file.write("Queue " + i + ": ");
                afisor.append("Queue " + i + ": ");
                if(scheduler.getServers().get(i).getTasks().size() == 0){
                    file.write("closed");
                    afisor.append("closed");
                }
                for(Task t: scheduler.getServers().get(i).getTasks()) {
                    file.write(t + "; ");
                    afisor.append(t + "; ");
                }
                afisor.append("\n");
                file.write("\n");
            }
            file.write("\n--------------------------------------------------------------------------------------------------\n");
            afisor.append("\n-----------------------------------------------------------------------------------------------------------------------------------\n");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        int currentTime = 0;
        int peakHour = 0;
        int maxim = 0;
        int AvgServiceTime = 0;
        AtomicInteger avgWaitingTime = new AtomicInteger(0);
        List<Task> tasksRemoved = new ArrayList<>();
        List<Server> sv = scheduler.getServers();
        while(currentTime <= timeLimit && !end(sv)){
            int aglomerare = 0;
            for(Task t: generatedTasks){ //iterate geeretedTasks list and pick tasks that have the arrivatTime equal with the currentTime
                if(t.getArrivalTime() == currentTime){
                    scheduler.dispatchTask(t);  // - send task to queue by calling the dispatchTask method from Scheduler
                    AvgServiceTime += t.getServiceTime();
                    avgWaitingTime.addAndGet(t.getServiceTime());
                    tasksRemoved.add(t); //adaug intr-o lista noua clientul pe care l-am adaugat in coada
                }
            }
            generatedTasks.removeAll(tasksRemoved);//-- delete client from list
            prettyPrint(sv, currentTime); // print in Log.txt si in GUI
            //System.out.println("CurrentTime:" + currentTime);
            //System.out.print("Waiting clients: ");
            for(Task t: generatedTasks){
                //System.out.print(t.toString() + "; ");
            }
            System.out.println();
            for(int i = 0; i < numberOfServers; i++){//verific si scot clientii care au ajuns sa aiba service time-ul 0
                for(Task t: scheduler.getServers().get(i).getTasks()) {
                    if(t.getServiceTime() == 0){
                        scheduler.getServers().get(i).getTasks().remove();
                    }
                }
            }
            for(int i = 0; i < numberOfServers; i++) {
                aglomerare += scheduler.getServers().get(i).getTasks().size();
                if (aglomerare > maxim) {  // aflu ora de varf din cozi
                    maxim = aglomerare;
                    peakHour = currentTime;
                }
                if (scheduler.getServers().get(i).getTasks().size() == 0) {
                    //System.out.println("Queue " + i + ": closed");
                } else {
                    //System.out.print("Queue " + i + ":");
                    for (Task t : scheduler.getServers().get(i).getTasks()) {
                        //System.out.print(t.toString() + "; ");
                    }
                    //System.out.println();}
                }
            }
            ////System.out.println();
            currentTime++;
            try{
                Thread.sleep(1000); //wait an interval of 1 second
            }catch (InterruptedException e) {
                System.out.println("Eroare la thread");
            }
        }
        try{
            file.write("Average waiting time: " + (double)(avgWaitingTime.intValue() / numberOfClients) + "\n");
            file.write("Average service time: " + (double) AvgServiceTime/numberOfClients + "\n");
            file.write("PeakHour:" + peakHour);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Average waiting time: " + (double)(avgWaitingTime.intValue() / numberOfClients));
        //System.out.println("Average service time: " + (double) AvgServiceTime/numberOfClients);
        //System.out.println("PeakHour:" + peakHour);

        afisor.append("Average waiting time: " + (double)(avgWaitingTime.intValue() / numberOfClients) + "\n");
        afisor.append("Average service time: " + (double) AvgServiceTime/numberOfClients + "\n");
        afisor.append("PeakHour:" + peakHour);
    }

    public static void main(String[] args) {
        SimulationFrame f = new SimulationFrame();
        f.setVisible(true);
    }
}
