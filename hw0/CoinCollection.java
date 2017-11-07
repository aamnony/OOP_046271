package hw0;

/**
 * A coin collection contains coins. Coins can only be of value 0.10, 0.5, 1, 5,
 * 10 Each coin VALUE can only appear in the collection once (e.g only one coin
 * with value 5)
 */
public class CoinCollection {
    private Wallet wallet;

    public static void main(String[] args) {
        CoinCollection collection = new CoinCollection();
        Coin c1 = new Coin(1);
        Coin c5 = new Coin(5);
        System.out.format("trying to add coin of value %f, results: %s\n", c1.getValue(),
                (collection.addCoin(c1) ? "True" : "False"));
        System.out.format("collection size: %d, collection total: %f\n", collection.getCollectionSize(),
                collection.getCollectionTotal());
        System.out.format("trying to add coin of value %f, results: %s\n", c5.getValue(),
                (collection.addCoin(c5) ? "True" : "False"));
        System.out.format("collection size: %d, collection total: %f\n", collection.getCollectionSize(),
                collection.getCollectionTotal());
        System.out.format("trying to add coin of value %f, results: %s\n", c5.getValue(),
                (collection.addCoin(c5) ? "True" : "False"));
        System.out.format("collection size: %d, collection total: %f\n", collection.getCollectionSize(),
                collection.getCollectionTotal());
        System.out.format("emptying collection\n");
        collection.emptyCollection();
        System.out.format("collection size: %d, collection total: %f\n", collection.getCollectionSize(),
                collection.getCollectionTotal());
    }

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
        return wallet.emptyWallet();
    }
}
