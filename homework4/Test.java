package homework4;

public class Test {

    public static void main(String[] args) {
        ColorGenerator generator = ColorGenerator.getInstance();
        Billboard rowsBillboard = new RowsBillboard();
        Billboard columsBillboard = new ColumsBillboard();
        Billboard twoRoundsBillboard = new TwoRoundsBillboard();

        Billboard randomBillboard = new RandomBillboard();
        generator.AddBillboard(randomBillboard);
        generator.AddBillboard(rowsBillboard);
        generator.AddBillboard(columsBillboard);
        generator.AddBillboard(twoRoundsBillboard);

    }
}
