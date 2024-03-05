//AHHAHAHAHAHAHAHAHAH WRONG

import java.util.ArrayList;
import java.util.List;

public abstract class BingoPattern implements Runnable {

    List<BingoChecker> checkers;
    BingoCard card;

    public BingoPattern(BingoCard card) {
        this.card = card;
    }

    @Override
    public void run() {
        List<Thread> thrds = new ArrayList<>();

        for (BingoChecker checker : checkers) {
            thrds.add(new Thread(checker));
        }

        for (Thread thrd : thrds) {
            thrd.start();
        }

        for (Thread thrd : thrds) {
            try {
                thrd.join();
            } catch (InterruptedException e) {
                System.out.println("Card " + card.id + " loses");
                return;
            }
        }

        System.out.println(String.format("Winner Card %s%n%s", card.id, card));

        synchronized (BingoGame.result) {
            BingoGame.isBingo = true;
        }
    }

    public static class BingoPatternPlus extends BingoPattern {

        public BingoPatternPlus(BingoCard card) {
            super(card);
            this.checkers = new ArrayList<>();
            this.checkers.add(new BingoChecker.BingoRowChecker(card, 2));
            this.checkers.add(new BingoChecker.BingoColChecker(card, 2));
        }
    }

    public static class BingoPatternHash extends BingoPattern {

        public BingoPatternHash(BingoCard card) {
            super(card);
            this.checkers = new ArrayList<>();
            this.checkers.add(new BingoChecker.BingoRowChecker(card, 1));
            this.checkers.add(new BingoChecker.BingoRowChecker(card, 3));
            this.checkers.add(new BingoChecker.BingoColChecker(card, 1));
            this.checkers.add(new BingoChecker.BingoColChecker(card, 3));
        }
    }
}