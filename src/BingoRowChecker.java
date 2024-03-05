public class BingoRowChecker extends BingoChecker{
    int row;
    public BingoRowChecker(BingoCard card,int row) {
        super(card);
        this.row = row - 1;
    }

    @Override
    public void run() {
        for (int col = 0; col < 5; col++) {
            int num = card.nums[row][col];
            while (!BingoGame.result[num]) {
                try {
                    synchronized (BingoGame.result) {
                        BingoGame.result.wait();
                    }
                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
                }
            }
        }
        BingoGame.isBingo = true;
        System.out.println("\nROW CHECK Card " + card.id + " done in row : " + (row + 1) + "\n" + card);
    }
}
