package hw0;

/**
 * A coin collection contains coins. Coins can only be of value 0.10, 0.5, 1, 5,
 * 10 Each coin VALUE can only appear in the collection once (e.g only one coin
 * with value 5)
 */
public class CoinCollection {
    private Wallet wallet;

    /**
     * @effects Creates a new coin collection
     */
    public CoinCollection() {
        wallet = new Wallet();
    }

    /**
     * @modifies this
     * @effects Adds a coin to the collection
     * @return true if the coin was successfully added to the collection; false
     *         otherwise
     */
    public boolean addCoin(Coin coin) {
        if (wallet.containsCoin(coin.getValue())) return false;
        wallet.addCoin(coin);
        return true;
    }

    /**
     * @return the current value of the collection
     */
    public double getCollectionTotal() {
        return wallet.getWalletTotal();
    }

    /**
     * @return the number of coins in the collection
     */
    public int getCollectionSize() {
        return wallet.getWalletSize();

    }

    /**
     * @modifies this
     * @effects Empties the collection
     */
    public void emptyCollection() {
        wallet.emptyWallet();
    }
}
