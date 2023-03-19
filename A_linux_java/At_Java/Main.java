package At_Java;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in); 

        Toy doll = new Toy(1, "Doll", 5, 7.5);
        Toy beyblade = new Toy(2, "Beyblade", 6, 11);
        Toy blocks = new Toy(3, "Blocks", 4, 20);
        Toy pogo = new Toy(4, "Pogo", 1, 5.65);
        Toy puzzle = new Toy(5, "Puzzle", 7, 2.5);
        Toy ball = new Toy(6, "Ball", 11, 4.4);
        Toy nerfGun = new Toy(7, "Nerf Gun", 2, 17.23);
        Toy frisbee = new Toy(8, "Frisbee", 8, 12.5);
        Toy dollHouse = new Toy(9, "Doll House", 3, 27.7);

        pogo.setWeight(95.5);
        ball.setWeight(8);

        List<Toy> toys = new ArrayList<Toy>();
        toys.add(doll);
        toys.add(beyblade);
        toys.add(blocks);
        toys.add(pogo);
        toys.add(puzzle);
        toys.add(ball);
        toys.add(nerfGun);
        toys.add(frisbee);
        toys.add(dollHouse);

        int poolSize = getPoolSize(toys, sc);        
        List<String> lotteryPrizePool = getLotteryPrizePool(toys, poolSize);
        lottery(poolSize, lotteryPrizePool, sc);
        sc.close();
    }

    private static void lottery(int poolSize, List<String> lotteryPrizePool, Scanner sc) throws IOException {
        int winNumbersLength = 3;
        List<Integer> winningNumbers = new ArrayList<>();
        List<Integer> guessedNumbers = new ArrayList<>();
        Random random = new Random();               
        String file = "WonToys.txt";
        FileWriter fw = new FileWriter(file);

        for (int i=poolSize; i>0; i--) {
            winningNumbers.clear();
            guessedNumbers.clear();
            System.out.println("\n\nПризовой фонд:");
            System.out.print(lotteryPrizePool);
            System.out.println("\nОставшиеся попытки: " + i + "\n");

            for (int j=0; j<winNumbersLength; j++) {
                while (true) {
                    int winNumber = random.nextInt(10);
                    if (!winningNumbers.contains(winNumber)) {
                        winningNumbers.add(winNumber);
                        break;
                    }
                }
            }

            System.out.println("Текущие выигрышные числа: " + winningNumbers);
            System.out.println("Введите " + winNumbersLength + " числа(ел) (от 1 до 9)");

            for (int k=0; k<winNumbersLength; k++) {
                System.out.println("Введённые числа: " + guessedNumbers);
                while (true) {
                    try {
                        String line = sc.nextLine();
                        int number = Integer.parseInt(line);
                        if (number >= 0 && number <= 9) {
                            guessedNumbers.add(number);
                            break;
                        }
                        else {
                            System.out.println("Введённое число не от 1 до 9 - попробуйте снова");
                        }
                    }
                    catch(NumberFormatException e) {
                        System.out.println("Введено не число - попробуйте снова");
                    }                
                }            
            }
            
            guessedNumbers.retainAll(winningNumbers);
            System.out.println("Совпавшие числа: " + guessedNumbers);

            if (guessedNumbers.containsAll(winningNumbers)) {
                System.out.println("\n!!! Вы выиграли \"" + lotteryPrizePool.get(0) + "\" !!!");
                fw.write(lotteryPrizePool.get(0));
                fw.append('\n');
                lotteryPrizePool.remove(0);                
            }
            else {
                System.out.println("\nПовезёт в следующий раз...");
            }
        }
        fw.flush(); 
        fw.close();
    }

    private static int getPoolSize(List<Toy> toys, Scanner sc) {
        int stock = 0;
        for (Toy toy : toys) {
            stock += toy.getStock();
        }

        int poolSize = 0;               

        while (poolSize > stock || poolSize <= 0) {
            System.out.println("Задайте число призов лотереи (от 1 до " + stock + ")");
            String line = sc.nextLine();
            try {
                poolSize = Integer.parseInt(line);
            }
            catch(NumberFormatException nfe) {
                continue;
            }
        }
        return poolSize;
    }

    private static List<String> getLotteryPrizePool(List<Toy> toys, int poolSize) {        
        Map<Double,Toy> map = new TreeMap<Double,Toy>();      
        double count = 0.0;

        for (Toy toy : toys) {
            map.put(count, toy);
            count += toy.getWeight();
        }

        Random random = new Random();
        double number;
        List<String> lotteryPrizePool = new ArrayList<>();

        while (poolSize > 0) {
            number = (1-random.nextDouble()) * count;
            if (((TreeMap<Double, Toy>) map).floorEntry(number).getValue().getStock() > 0) {
                lotteryPrizePool.add(((TreeMap<Double, Toy>) map).floorEntry(number).getValue().getName());
                ((TreeMap<Double, Toy>) map).floorEntry(number).getValue().decriseStock();
                poolSize--;
            }
            else {
                continue;
            }
        }
        System.out.print(lotteryPrizePool);
        return lotteryPrizePool; 
    }
}