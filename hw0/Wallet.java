
package hw0;

/**
 * A wallet can conatain a number of coins. There could be several coins of the same value, 
 * but the same coin cannot apear in the wallet twice
 */
import java.util.ArrayList;

public class Wallet {
    private ArrayList<Coin> coins;

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
        if(coins.contains(coin))
            return false;
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
        ArrayList<Coin> payCoins = new ArrayList<Coin>();
        for (Coin coin : coins) {
            cur = cur + coin.getValue();
            payCoins.add(coin);
            if (cur >= sum) {
                for (Coin payCoin : payCoins) {
                    coins.remove(payCoin);
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
        ArrayList<Coin> payCoins = new ArrayList<Coin>();
        double[] coinTypes = { 10, 5, 1, 0.5, 0.1 };
        for (double type : coinTypes) {
            for (Coin coin : coins) {
                if (coin.getValue() == type) {
                    cur = cur + coin.getValue();
                    payCoins.add(coin);
                    if (cur >= sum) {
                        for (Coin payCoin : payCoins) {
                            coins.remove(payCoin);
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
        for (Coin coin : coins) {
            sum = sum + coin.getValue();
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
        for (Coin coin : coins) {
            if (coin.getValue() == value) return true;
        }
        return false;
    }
}
