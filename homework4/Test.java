package homework4;

public class Test {

    public static void main(String[] args) {
        ColorGenerator generator = ColorGenerator.getInstance();
        Billboard rowsBillboard = new Billboard(new RowsStrategy());
        Billboard columsBillboard = new Billboard(new ColumsStrategy());
        Billboard twoRoundsBillboard = new Billboard(new TwoRoundsStrategy());
        Billboard randomBillboard = new Billboard(new RandomStrategy());

        generator.addBillboard(rowsBillboard);
        generator.addBillboard(columsBillboard);
        generator.addBillboard(twoRoundsBillboard);
        generator.addBillboard(randomBillboard);

    }
}
