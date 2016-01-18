import java.util.Random;

public class Action {
    
    private int[] stateL;
    private int[] stateR;
    private int[] indL;
    private int[] indR;
    private int[] beltState;
    
    private int[] conflict;
    private int   L;
    
    public int[] actionL; // actions for left workers   |  1 -> take component
    public int[] actionR; // actions for right workers  |  2 -> wait
                          //                            |  3 -> build product
                          //                            |  4 -> place product
        
    public Action(int[] stateL, int[] stateR, int[] indL, int[] indR, int[] beltState){
        
        this.stateL    = stateL;
        this.stateR    = stateR;
        this.indL      = indL;
        this.indR      = indR;
        this.beltState = beltState;
        
        L = beltState.length;
        conflict = new int[L];
        
        for(int i=0; i<L; i++){
            conflict[i] = indL[i]+indR[i]; // number of workers for each belt step
        }
        
        actionL = new int[L];
        actionR = new int[L];
        
    }
    
    public void generateAction(String[][] w, String[] belt, int position){
        
        int   nC     = w[0].length;
        int[] state  = new int[L];
        int[] action = new int[L];
        
        if(position==0){
            System.arraycopy(stateL, 0, state, 0, L);
        } else {
            System.arraycopy(stateR, 0, state, 0, L);
        }
                
        for(int i=0; i<L; i++){
            
            if(state[i]==2){                          
                action[i] = 3;                        // no more components needed
            }
            if((state[i]==3)&(beltState[i]==3)){      
                action[i] = 4;                        // product ready, belt free
            }            
            if((state[i]==1)&(beltState[i]==1)){      // component on the belt, hand free
                action[i] = 1;                        // component needed
                for(int j=0; j<nC; j++){
                    if(belt[i].equals(w[i][j])){
                        action[i] = 2;                // if already present, component not needed
                    }
                }
                
            }
            if(((state[i]==3)&(beltState[i]==1))|((state[i]==3)&(beltState[i]==2))){
                action[i] = 2;                        // other cases
            }
            if(((state[i]==1)&(beltState[i]==2))|((state[i]==1)&(beltState[i]==3))){
                action[i] = 2;                        // other cases
            }

        }
        
        if(position==0){
            for(int i=0; i<L; i++){
                if(indL[i]==0){
                    action[i] = 2;  // if no worker is present at position 'i', then WAIT (i.e. do nothing)
                }
            }
        } else {
            for(int i=0; i<L; i++){
                if(indR[i]==0){
                    action[i] = 2;  // if no worker is present at position 'i', then WAIT (i.e. do nothing)
                }
            }
        }
        
        if(position==0){
            System.arraycopy(state,  0, stateL,  0, L);
            System.arraycopy(action, 0, actionL, 0, L);
        } else {
            System.arraycopy(state,  0, stateR,  0, L);
            System.arraycopy(action, 0, actionR, 0, L);
        }

    }
        
    public void conflictCheck(){
        
        for(int i=0; i<L; i++){
            if(conflict[i]>1){
                if((actionL[i]==1)&(actionR[i]==1)){ // take - take
                    conflictSolve(i);
                }
                if((actionL[i]==1)&(actionR[i]==4)){ // take - place
                    conflictSolve(i);
                }
                if((actionL[i]==4)&(actionR[i]==4)){ // place - place
                    conflictSolve(i);
                }
                if((actionL[i]==4)&(actionR[i]==1)){ // place - take
                    conflictSolve(i);
                }
            }
        }
        
    }
    
    private void conflictSolve(int k){
        
        // in case of conflict, we simply assume that one of the two workers will
        // wait (i.e. random choice, no strategy)
        
        Random randomGenerator = new Random();
        
        if(randomGenerator.nextInt(2)>0){
            actionL[k] = 2; // left worker waits
        } else{
            actionR[k] = 2; // right worker waits
        }
        
    }
    
    public void updateState(int[] stateL, int[] stateR, int[] beltState){
        this.stateL    = stateL;
        this.stateR    = stateR;
        this.beltState = beltState;
    }    
    
}
