import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by james on 23/04/15.
 */
public class Main {
    private static final int totalitterations = 1000000;

    private static int timeBetweenRequests;
    private static int queueMaxSize;
    private static int numberOfServers;
    private static int requestProcessTime;

    private static int missedRequests = 0;
    private static int totalRequests = 0;
    public static List<Integer> requestExecutionTime = new ArrayList<Integer>();
    public static List<Integer> queueSize = new ArrayList<Integer>();

    public static void main(String[] args){
        if(args.length!=4) {usage(); return;}
        queueMaxSize = Integer.parseInt(args[0]);
        timeBetweenRequests = Integer.parseInt(args[1]);
        numberOfServers = Integer.parseInt(args[2]);
        requestProcessTime = Integer.parseInt(args[3]);

        RequestQueue queue = new RequestQueue(queueMaxSize);
        ServerPool serverPool = new ServerPool(numberOfServers);
        for(int itteration = 1;itteration<=totalitterations;itteration++){
            boolean newrequest = Math.random()<((totalitterations/(float)timeBetweenRequests)/totalitterations);
            if(newrequest)queue.newRequest(itteration);

            if(queue.hasWaitingRequest()&&serverPool.hasAvailableServer()){
                int topRequest = queue.popRequest();
                serverPool.process(topRequest);
            }
            serverPool.tick(itteration);
            queue.tick();
            //System.out.println("nr" + (newrequest?"Y":"N") + " \tqs:" + queue.getUnprocessedRequests() + " sl"+ serverPool.countOfRunningRequests());
        }
        missedRequests+=queue.getUnprocessedRequests();
        float percMissed = missedRequests/(float)totalRequests;
        float averageQueueSize = listAverage(queueSize);
        float averageRespTime = listAverage(requestExecutionTime);
        System.out.println("Total requests:" + totalRequests);
        System.out.println("Rejected requests:" + missedRequests);
        System.out.println("Percentage missed:" + percMissed);
        System.out.println("Average queue size:" + averageQueueSize);
        System.out.println("Average response time:" + averageRespTime);
        System.out.println("");
        System.out.println("Tot requests completed:" + requestExecutionTime.size());
        System.out.println("Running requests at end of sim:" + serverPool.countOfRunningRequests());
        System.out.println("Queue size at end of sim:" + queue.getUnprocessedRequests());
    }

    private static float listAverage(List<Integer> queueSize) {
        int total = queueSize.size();
        int sum = 0;
        for(Integer i : queueSize) sum += i;
        return (sum / (float)total);
    }

    public static class RequestQueue{
        private int maxSize;

        public RequestQueue(int maxSize){

            this.maxSize = maxSize;
        }
        private List<Integer> queue = new ArrayList<Integer>();

        public void newRequest(int itteration){
            totalRequests++;
            if(queue.size()<maxSize) {
                queue.add(itteration);// new request added before tick
            }else{
                // queue is of max size so reject request
                missedRequests++;
            }
        }

        public boolean hasWaitingRequest(){return queue.size()>0;}

        public int popRequest(){
            int head = queue.get(0);
            queue.remove(0);
            return head;
        }

        /**
         * called after any requests have been pushed to any idle server
         */
        public void tick(){
            queueSize.add(queue.size());
        }

        public int getUnprocessedRequests() {
            return queue.size();
        }
    }

    public static class ServerPool{
        public Set<Server> servers;
        public ServerPool(int size){
            servers = new HashSet<Server>();
            for(int i=0;i<size;i++)servers.add(new Server());
        }

        public boolean hasAvailableServer() {
            for(Server s: servers){
                if(s.isIdle()) return true;
            }
            return false;
        }

        public void process(int requestStartItteration) {
            for(Server s: servers){
                if(s.isIdle()) {
                    s.process(requestStartItteration);
                    return;
                }
            }
            throw new RuntimeException("no server to process request");
        }

        public void tick(int itteration) {
            for(Server s: servers)s.tick(itteration);
        }

        public int countOfRunningRequests() {
            int tot =0;
            for(Server s: servers) if(!s.isIdle())tot+=1;
            return tot;
        }
    }

    public static class Server{
        private int ticksRemaining;
        private int curentRequestStartItteration;
        private boolean idle = true;
        public Server(){

        }
        public boolean isIdle(){return idle;}
        public void tick(int itteration){
            if(!idle){

                ticksRemaining--;
                // if this was the last tick then mark as completed and idle again
                if(ticksRemaining == 0){
                    idle = true;
                    requestExecutionTime.add((itteration - curentRequestStartItteration)+1);
                }
            }
        }
        public void process(int requestStartItteration){
            idle = false;
            ticksRemaining = requestProcessTime;
            this.curentRequestStartItteration = requestStartItteration;
        }
    }


    private static void usage() {
        System.out.println("Usage:\n\tjava Main <queueMaxSize> <timeBetweenRequests> <numberOfServers> <requestProcessTime>");
    }
}
