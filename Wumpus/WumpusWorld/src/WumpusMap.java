public class WumpusMap {
    public static final int NUM_ROWS = 10;
    public static final int NUM_COLUMNS = 10;
    public static final int NUM_PITS = 10;
    private WumpusSquare[][] grid;
    private int ladderColumn;
    private int ladderRow;

    public WumpusMap(){
        createMap();
        System.out.println(this);
    }

    public void createMap(){
        grid= new WumpusSquare[10][10];
        for(int r = 0; r<=9; r++){
            for(int c = 0; c<=9; c++){
                grid[r][c]= new WumpusSquare();
            }
        }

        for(int i=1; i<=10; i++){
            // 10 pits & breezes
            //int randomNum = min + (int)(Math.random() * ((max – min) + 1));
            int randRow = 0+(int)(Math.random()*((9-0)+1));
            int randCol = 0+(int)(Math.random()*((9-0)+1));
            while(grid[randRow][randCol].isPit()){
                randRow = 0+(int)(Math.random()*((9-0)+1));
                randCol = 0+(int)(Math.random()*((9-0)+1));
            }
            grid[randRow][randCol].setPit(true);
            if(randRow+1<=9){
                if(!grid[randRow+1][randCol].isPit()){
                    grid[randRow+1][randCol].setBreeze(true);
                }
            }
            if(randRow-1>=0){
                if(!grid[randRow-1][randCol].isPit()){
                    grid[randRow-1][randCol].setBreeze(true);
                }
            }
            if(randCol+1<=9){
                if(!grid[randRow][randCol+1].isPit()){
                    grid[randRow][randCol+1].setBreeze(true);
                }
            }
            if(randCol-1>=0){
                if(!grid[randRow][randCol-1].isPit()){
                    grid[randRow][randCol-1].setBreeze(true);
                }
            }



        }
        //gold
        int randRow = 0+(int)(Math.random()*((9-0)+1));
        int randCol = 0+(int)(Math.random()*((9-0)+1));
        while(grid[randRow][randCol].isPit() || grid[randRow][randCol].isLadder()){
            randRow = 0+(int)(Math.random()*((9-0)+1));
            randCol = 0+(int)(Math.random()*((9-0)+1));
        }
        grid[randRow][randCol].setGold(true);

        //wumpus and Stenches
        randRow = 0+(int)(Math.random()*((9-0)+1));
        randCol = 0+(int)(Math.random()*((9-0)+1));
        while(grid[randRow][randCol].isPit() || grid[randRow][randCol].isLadder()){
            randRow = 0+(int)(Math.random()*((9-0)+1));
            randCol = 0+(int)(Math.random()*((9-0)+1));
        }
        grid[randRow][randCol].setWumpus(true);
        if(randRow+1<=9){
            if(!grid[randRow+1][randCol].isPit()){
                grid[randRow+1][randCol].setStench(true);
            }
        }
        if(randRow-1>=0){
            if(!grid[randRow-1][randCol].isPit()){
                grid[randRow-1][randCol].setStench(true);
            }
        }
        if(randCol+1<=9){
            if(!grid[randRow][randCol+1].isPit()){
                grid[randRow][randCol+1].setStench(true);
            }
        }
        if(randCol-1>=0){
            if(!grid[randRow][randCol-1].isPit()){
                grid[randRow][randCol-1].setStench(true);
            }
        }

        //ladder
        randRow = 0+(int)(Math.random()*((9-0)+1));
        randCol = 0+(int)(Math.random()*((9-0)+1));
        while(grid[randRow][randCol].isPit() || grid[randRow][randCol].isGold() || grid[randRow][randCol].isWumpus()){
            randRow = 0+(int)(Math.random()*((9-0)+1));
            randCol = 0+(int)(Math.random()*((9-0)+1));
        }
        grid[randRow][randCol].setLadder(true);
        ladderRow=randRow;
        ladderColumn=randCol;
        System.out.println("ladder row: " + randRow + "   ladder column: " + randCol);
    }

    public int getLadderColumn() {
        return ladderColumn;
    }

    public int getLadderRow() {
        return ladderRow;
    }

    public WumpusSquare getSquare(int row, int col){
        if(row>=0 && row<=9 && col>=0 && col<=9){

            return grid[row][col];
        }
        return null;
    }

    public String toString(){
        //need to do breeze things
        String string = "";
        for(int r = 0; r<=9; r++){
            for(int c = 0; c<=9; c++){
                string+=grid[r][c].toString() + " ";
            }
            string+= "\n";
        }
        return string;
    }


}
