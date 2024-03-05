import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BingoGame implements Runnable {
    List<BingoCard> cards;
    static boolean [] result = new boolean[76];
    static boolean isBingo;

    public BingoGame () {
        result[0] = true;
        isBingo = false;
    }

    @Override
    public void run () {
        Scanner sc = new Scanner(System.in);
        System.out.print("How many players? ");
        int cnt = sc.nextInt();

        cards = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            cards.add(new BingoCard(i+1));
        }

        for (BingoCard card: cards) {
            System.out.println("Card " + card.id);
            System.out.println(card);
        }

        for (int i = 1; i <= 75; i++) {
            result[i] = false;
        }

//        BINGO CHECKERS

        Thread [] checkerThreadsRow = new Thread[cnt];
        Thread [] checkerThreadsCol = new Thread[cnt];
        for (int i = 0; i < cnt; i++) {
            for (int j = 1; j < 5; j++) {
                checkerThreadsRow[i] = new Thread(new BingoRowChecker(cards.get(i),j));
                checkerThreadsRow[i].start();
            }
        }

        for (int i = 0; i < cnt; i++) {
            for (int j = 1; j < 5; j++) {
                checkerThreadsCol[i] = new Thread(new BingoColChecker(cards.get(i),j));
                checkerThreadsCol[i].start();
            }
        }

        //    TOOD randomly get numbers while not bingo
        while (!isBingo) {
            Random rand = new Random();
            int newNum;
            do  {
                newNum = rand.nextInt(1,76);
            } while (result[newNum]);
            System.out.print(newNum + " ");
            result[newNum] = true;

            synchronized (result) {
                result.notifyAll();
            }
            try {

                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

            for (Thread e : checkerThreadsRow) {
                e.interrupt();
            }
            for (Thread e : checkerThreadsCol) {
                e.interrupt();
            }
            System.exit(0);


    }
}
