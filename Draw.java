public class Draw {
    
    // For each time point we plot the components taken by the workers and the belt.
    // E.g. two components (A,B); belt of length L=3
    //
    // piece 1       A A A   <--- first  'hand' of workers 1,2,3 (on the left of the belt)
    // piece 2       - - -   <--- second 'hand' of workers 1,2,3 (on the left of the belt)
    //  BELT         A = P   <--- positions 1,2,3 of the belt
    // piece 1       B A A   <--- first  'hand' of workers 1,2,3 (on the right of the belt)
    // piece 2       - - -   <--- second 'hand' of workers 1,2,3 (on the right of the belt)
    //
    // For each time there are two plots:
    // 1) as soon as the belt has slided
    // 2) after workers have performed their actions
        
    private Belt   mybelt;
    private Worker myworker;
    
    private int L;
    private int nC;
    
    public Draw(int L, int nC){
        
        this.L  = L;
        this.nC = nC;
        
    }
    
    public void test(Belt B, Worker W){
        
        mybelt   = B;
        myworker = W;
        
        // ---------------------------------------------------------------------
     
        for(int i=0; i<nC; i++){
            System.out.print("piece " + (i+1) + "       ");
            for(int j=0; j<L; j++){
                if(W.wL[j][i]==null){
                    System.out.print("-" + " ");
                } else{
                    System.out.print(W.wL[j][i] + " ");
                }                    
            }
            System.out.println();
        }
        
        System.out.print(" BELT         ");
        for(int i=0; i<L; i++){
            if(B.belt[i]==null){
                System.out.print("=" + " ");
            } else {
                System.out.print(B.belt[i] + " ");
            }            
        }
        System.out.println();
        
        for(int i=0; i<nC; i++){
            System.out.print("piece " + (i+1) + "       ");
            for(int j=0; j<L; j++){
                if(W.wR[j][i]==null){
                    System.out.print("-" + " ");
                } else{
                    System.out.print(W.wR[j][i] + " ");
                } 
            }
            System.out.println();
        }
        System.out.println();
        
    }
    
}
