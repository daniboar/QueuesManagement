package Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private  BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server(int i){
            //initialize queue and waitingPeriod
            this.tasks = new LinkedBlockingDeque<Task>(i);
            this.waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask){
        synchronized (tasks) {
            tasks.add(newTask);//add task to queue ;
            waitingPeriod.getAndAdd(newTask.getServiceTime()); //increment the waitingPeriod
        }
    }

    @Override
    public void run() {
        try{
            while(true){
                if(tasks.size() > 0) {
                    Task task = tasks.peek();   //extrag primul task din lista
                    Thread.sleep(1000);
                    if (task.getServiceTime() > 1) {
                        task.setServiceTime(task.getServiceTime() - 1);  //decrementam service time-ul
                    } else {
                        tasks.poll();   //elimin task-ul din lista
                    }
                    if(waitingPeriod.get() > 0) {
                        waitingPeriod.getAndDecrement();  // decrementam waitingPeriod-ul
                    }
                }
            }
        }catch (InterruptedException e) {
            System.out.println("Eroare la thread");
        }
    }
    public BlockingQueue<Task> getTasks(){  //returenz clientii
        return tasks;
    }
}
