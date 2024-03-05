import java.util.*;

public class BingoCard {
    int [][] nums;
    int id;

    public BingoCard (int id) {
        this.id = id;
        nums = new int [5][5];

        int bingoMin = 1;
        int bingoMax = 15;
        Random rand = new Random();

        List<Integer> checker = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 2 && j == 2) {
                    nums[i][j] = 0;
                } else {
                    int newNum;
                    do {
                        newNum = rand.nextInt(bingoMin,bingoMax + 1);
                    } while (checker.contains(newNum));
                    checker.add(newNum);
                    nums[i][j] = newNum;
                    bingoMin += 15;
                    bingoMax += 15;
                }
            }
            bingoMin = 1;
            bingoMax = 15;
        }
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("B\tI\tN\tG\tO\n");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                sb.append(nums[i][j]).append("\t");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
