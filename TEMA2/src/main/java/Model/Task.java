package Model;

public class Task {
    private int id;
    private int arrivalTime;
    private int serviceTime;

    public  Task(int id, int arrivalTime1, int serviceTime1){   //constructor de initializare
        this.id = id;
        this.arrivalTime = arrivalTime1;
        this.serviceTime = serviceTime1;
    }

    public int getId() {
        return this.id;
    }

    public synchronized int getArrivalTime() {
        return this.arrivalTime;
    }

    public synchronized int getServiceTime() {
        return this.serviceTime;
    }

    public void setServiceTime(int serviceTime){
        this.serviceTime = serviceTime;
    }

    public String toString(){
        return "(" + this.id + "," + this.arrivalTime + "," + serviceTime + ")";
    }  // (1,3,5);
}
