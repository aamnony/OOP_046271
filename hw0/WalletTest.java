package hw0;

public class WalletTest {
    public static void main(String[] args) {
        Wallet w = new Wallet();
        Coin c1 = new Coin(1);
        Coin c5 = new Coin(5);
        System.out.format("Wallet size: %d, total: %f\n", w.getWalletSize(), w.getWalletTotal());
        System.out.format("trying to add coin of value %f, results: %s\n", c1.getValue(), (w.addCoin(c1)? "True":"False"));
        System.out.format("trying to add same coin again, results: %s\n", (w.addCoin(c1)? "True":"False"));
        System.out.format("trying to add coin of value %f, results: %s\n", c5.getValue(), (w.addCoin(c5)? "True":"False"));
        System.out.format("Added two coins, 1 than 5, wallet size: %d, wallet total:%f\n", w.getWalletSize(), w.getWalletTotal());
        double payed = w.pay(5);
        System.out.format("called Pay(5) actual pay - %f\n",payed);
        
        System.out.format("Wallet size: %d, total: %f\n", w.getWalletSize(), w.getWalletTotal());
        w.addCoin(c1);
        w.addCoin(c5);
        System.out.format("Added two coins, 1 than 5, wallet size: %d, wallet total:%f\n", w.getWalletSize(), w.getWalletTotal());
        System.out.format("check if Wallet contains coin with value 5, results: %s\n", (w.containsCoin(5)? "True":"False"));
        payed = w.payMinimum(5);        
        System.out.format("called PayMinimum(5) actual pay - %f\n",payed);
        System.out.format("check if Wallet contains coin with value 5, results: %s\n", (w.containsCoin(5)? "True":"False"));
        System.out.format("Wallet size: %d, total: %f\n", w.getWalletSize(), w.getWalletTotal());
        w.emptyWallet();
        System.out.format("Emptied Wallet, new size: %d, new total: %f\n", w.getWalletSize(), w.getWalletTotal());
    }
}
