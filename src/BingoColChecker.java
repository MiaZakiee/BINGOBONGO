public class BingoColChecker extends BingoChecker{
    int col;
    public BingoColChecker(BingoCard card,int col) {
        super(card);
        this.col = col - 1;
    }

    @Override
    public void run() {
        for (int row = 0; row < 5; row++) {
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
        System.out.println("\nCOL CHECK Card " + card.id + " done in col : " + (col + 1) + "\n" + card);
    }
}
