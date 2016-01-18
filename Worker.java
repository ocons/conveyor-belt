public class Worker {
    
    public String[][] wL; // worker left of the belt
    public String[][] wR; // worker right of the belt
    
    private int[] indL; // presence of workers on left
    private int[] indR; // presence of workers on right
    
    private int[] stateL; // state left
    private int[] stateR; // state right
    
    private int L;  // belt length
    private int nC; // number of components
    
    public String[] belt;
    
    public Worker(int nC, String[] belt){
        
        L  = belt.length;
        this.nC = nC;
        
        this.belt = new String[L];
        System.arraycopy(belt, 0, this.belt, 0, L);
        
        wL = new String[L][nC];
        wR = new String[L][nC];
        
        indL = new int[L];
        indR = new int[L];
        
        stateL = new int[L];
        stateR = new int[L];
        
        init();
        
    }
    
    private void init(){
        
        for(int i=0; i<L; i++){
            
            for(int j=0; j<nC; j++){
                wL[i][j] = null;
                wR[i][j] = null;
            }
            
            indL[i] = 1; // a worker is present (1) or not (0)
            indR[i] = 1; // (by default they are all present)
            
            stateL[i] = 1; // state 1 --> hand free
            stateR[i] = 1; // state 2 --> hands busy with components
                           // state 3 --> product ready
            
        }
        
    }
    
    public void updateState(int position){
        
        if(position==0){
            for(int i=0; i<L; i++){
                if("P".equals(wL[i][0])){
                    stateL[i] = 3;    // if first hand has P  --> state 3
                } else if(wL[i][nC-1]==null){
                    stateL[i] = 1;    // if last hand is free --> state 1
                } else stateL[i] = 2; // otherwise both hands are busy
            }
        }
        
        if(position==1){
            for(int i=0; i<L; i++){
                if("P".equals(wR[i][0])){
                    stateR[i] = 3;
                } else if(wR[i][nC-1]==null){
                    stateR[i] = 1;
                } else stateR[i] = 2;
            }
        }
        
    }
    
    public void doWork(int[] action, int position){
        
        String[][] w = new String[L][nC];
        int        k;
                
        if(position==0){
            for(int i=0; i<L; i++){
                System.arraycopy(wL[i], 0, w[i], 0, nC);
            }
        } else {
            for(int i=0; i<L; i++){
                System.arraycopy(wR[i], 0, w[i], 0, nC);
            }
        }
                
        for(int i=0; i<L; i++){
            if(action[i]==1){         // TAKE: take component from belt into free hand
                k=0;
                while(k>=0){
                    if(w[i][k]==null){
                        w[i][k] = belt[i];
                        k = -100;                        
                    }
                    k+=1;
                }
                belt[i] = null;
            }
            if(action[i]==3){         // BUILD: build product
                for(int j=1; j<nC; j++){
                    w[i][j] = null;
                }
                w[i][0] = "P";
            }
            if(action[i]==4){         // PLACE: place product on belt and empty hands
                belt[i] = "P";
                for(int j=0; j<nC; j++){
                    w[i][j] = null;
                }
                
            }                        
        }
        
        if(position==0){
            for(int i=0; i<L; i++){
                System.arraycopy(w[i], 0, wL[i], 0, nC);
            }
        } else {
            for(int i=0; i<L; i++){
                System.arraycopy(w[i], 0, wR[i], 0, nC);
            }
        }
        
        updateState(position);
        
    }
    
    public void updateBelt(String[] belt){
        System.arraycopy(belt, 0, this.belt, 0, L);
    }
    
    public int[] getStateL(){
        return stateL; 
    }
    public int[] getStateR(){
        return stateR; 
    }
    
    public int[] getWorkersL(){
        return indL; 
    }
    public int[] getWorkersR(){
        return indR; 
    }
    
}
