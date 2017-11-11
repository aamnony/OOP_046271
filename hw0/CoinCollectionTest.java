package hw0;

public class CoinCollectionTest {
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
}
