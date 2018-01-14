package homework4.test;

public class Greetings {

    private Greetings() {}

    static abstract class Greeting {
        public void greet(String name) {
            System.out.println(name);
        }
    }

    static class MaleGreeting extends Greeting {
        @Override
        public void greet(String name) {
            System.out.print("Hello Mr. ");
            super.greet(name);
        }
    }

    static class FemaleGreeting extends Greeting {
        @Override
        public void greet(String name) {
            System.out.print("Hello Ms. ");
            super.greet(name);
        }
    }

    static abstract class Person {
        private String name;

        public Person(String name) {
            this.name = name;

        }

        public String getName() {
            return name;
        }

        public abstract void greet();
    }

    static class Male extends Person {
        public Male(String name) {
            super(name);
        }

        @Override
        public void greet() {
            new MaleGreeting().greet(getName());
        }
    }

    static class Female extends Person {
        public Female(String name) {
            super(name);
        }

        @Override
        public void greet() {
            new FemaleGreeting().greet(getName());
        }
    }

    public static void main(String[] args) {
        Male male = new Male("Danny");
        Female female = new Female("Danna");
        male.greet();
        female.greet();

    }

}
