import java.util.ArrayList;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.*; 
import static java.util.stream.Collectors.toSet;

/* CompliantNode refers to a node that follows the rules (not malicious)*/
public class CompliantNode implements Node {
    private double p_graph, p_malicious, p_txDistribution;
    private int numRounds, currentRound;
    private boolean followees[];
    private boolean blackList[];
    private Set<Transaction> pendingTransactions, consensusTransactions;


    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
        this.p_graph = p_graph;
        this.p_malicious = p_malicious;
        this.p_txDistribution = p_txDistribution;
        this.numRounds = numRounds;
        this.currentRound = 0;
        this.pendingTransactions = new HashSet<Transaction>();
        this.consensusTransactions = new HashSet<Transaction>();

    }

    public void setFollowees(boolean[] followees) {
        this.followees = followees;
        this.blackList = new boolean[followees.length];
        Arrays.fill(this.blackList, Boolean.FALSE);
    }

    public void setPendingTransaction(Set<Transaction> pendingTransactions) {
        this.pendingTransactions.addAll(pendingTransactions);
        this.consensusTransactions.addAll(pendingTransactions);
    }

    public Set<Transaction> sendToFollowers() {
        this.currentRound++;
        // System.out.println("currentRound: "+ currentRound);

        Set<Transaction> temp = new HashSet<Transaction>();
        temp.addAll(pendingTransactions);
        this.pendingTransactions.clear();

        if(currentRound>=numRounds){
            // System.out.println("consensusTransactions: "+this.consensusTransactions);
            return this.consensusTransactions;
        }
        else{
            return temp;
        }
    }

    public void checkMalicious(Set<Candidate> candidates){
        Set<Integer> senders = candidates.stream().map(c -> c.sender).collect(toSet());
        // Set<Integer> senders = new HashSet<>();
        // Iterator<Candidate> it = candidates.iterator(); 
        // while (it.hasNext()){ 
        //     Candidate c = it.next();
        //     if(c==null){
        //         continue;
        //     }
        //     else{
        //         if(c.tx==null){
        //             continue;
        //         }
        //         senders.add(c.sender);
        //     }
        // } 

        for(int i=0;i<followees.length;i++){
            if(followees[i] && !senders.contains(i)){
                blackList[i] = true;
            }
        }
    }

    public void receiveFromFollowees(Set<Candidate> candidates) {
        if(currentRound>=numRounds-1){
            return;
        }
        this.checkMalicious(candidates);
        for(Candidate c : candidates){
            if(!blackList[c.sender] && !consensusTransactions.contains(c.tx)){
                this.pendingTransactions.add(c.tx);
                this.consensusTransactions.add(c.tx);
            }
        }
    }
}
