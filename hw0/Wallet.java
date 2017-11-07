
package hw0;

/**
 * A wallet can conatain a number of coins. There could be several coins of the same value, 
 * but the same coin cannot apear in the wallet twice
 */
import java.util.ArrayList;

public class Wallet {
    private ArrayList<Coin> coins;

    public static void main(String[] args) {
        Wallet w = new Wallet();
        Coin c1 = new Coin(1);
        Coin c5 = new Coin(5);
        System.out.format("Wallet size: %d, total: %f\n", w.getWalletSize(), w.getWalletTotal());
        w.addCoin(c1);
        w.addCoin(c5);
        System.out.format("Added two coins, 1 than 5, wallet size: %d, wallet total:%f\n", w.getWalletSize(),
                w.getWalletTotal());
        double payed = w.pay(5);
        System.out.format("called Pay(5) actual pay - %f\n", payed);

        System.out.format("Wallet size: %d, total: %f\n", w.getWalletSize(), w.getWalletTotal());
        w.addCoin(c1);
        w.addCoin(c5);
        System.out.format("Added two coins, 1 than 5, wallet size: %d, wallet total:%f\n", w.getWalletSize(),
                w.getWalletTotal());
        System.out.format("check if Wallet contains coin with value 5, results: %s\n",
                (w.containsCoin(5) ? "True" : "False"));
        payed = w.payMinimum(5);
        System.out.format("called PayMinimum(5) actual pay - %f\n", payed);
        System.out.format("check if Wallet contains coin with value 5, results: %s\n",
                (w.containsCoin(5) ? "True" : "False"));
        System.out.format("Wallet size: %d, total: %f\n", w.getWalletSize(), w.getWalletTotal());
        w.emptyWallet();
        System.out.format("Emptied Wallet, new size: %d, new total: %f\n", w.getWalletSize(), w.getWalletTotal());
    }

    /**
     * @effects Creates a new empty wallet
     */
    public Wallet() {
        coins = new ArrayList<Coin>();
    }

    /**
     * @modifies this
     * @effects Adds a coin to the wallet
     * @return true if the coin was successfully added to the wallet; false
     *         otherwise
     */
    public boolean addCoin(Coin coin) {
        coins.add(coin);
        return true;
    }

    /**
     * @modifies this
     * @effects tries to match at least the sum "sum" with coins in the wallet. If
     *          transaction is possible, removes the paid coins from the wallet;
     *          else; changes nothing
     * @requires sum < total money in wallet
     * @return the amount actually paid, 0 if amount could not be obtained
     */
    public double pay(double sum) {
        double cur = 0;
        ArrayList<Coin> pay_coins = new ArrayList<Coin>();
        for (Coin C : coins) {
            cur = cur + C.getValue();
            pay_coins.add(C);
            if (cur >= sum) {
                for (Coin pay_coin : pay_coins) {
                    coins.remove(pay_coin);
                }
                return cur;
            }
        }
        return 0;
    }

    /**
     * @modifies this
     * @effects tries to match at least the sum "sum" with the minimum number of
     *          coins available from the wallet If transaction is possible, removes
     *          the paid coins from the wallet; else; changes nothing
     * @return the amount actually paid, 0 if amount could not be obtained
     */
    public double payMinimum(double sum) {
        double cur = 0;
        ArrayList<Coin> pay_coins = new ArrayList<Coin>();
        double[] coin_types = { 10, 5, 1, 0.5, 0.1 };
        for (double type : coin_types) {
            for (Coin C : coins) {
                if (C.getValue() == type) {
                    cur = cur + C.getValue();
                    pay_coins.add(C);
                    if (cur >= sum) {
                        for (Coin pay_coin : pay_coins) {
                            coins.remove(pay_coin);
                        }
                        return cur;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * @return the current total value of coins in the wallet
     */
    public double getWalletTotal() {
        double sum = 0;
        for (Coin C : coins) {
            sum = sum + C.getValue();
        }
        return sum;
    }

    /**
     * @return the number of coins in the wallet
     */
    public int getWalletSize() {
        return coins.size();
    }

    /**
     * @modifies this
     * @effects Empties the the wallet. After this method is called, both
     *          getWalletSize() and getWalletTotal() will return 0 if called
     */
    public void emptyWallet() {
        coins.clear();
    }

    /**
     * @return true if this wallet contains a coin with value "value" false
     *         otherwise
     */
    public boolean containsCoin(double value) {
        for (Coin C : coins) {
            if (C.getValue() == value) return true;
        }
        return false;
    }
}
