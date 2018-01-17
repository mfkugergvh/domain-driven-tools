package io.ddd.core.delegate;

public class Test extends Delegate {
    public void ll() {

        System.out.println(Test.That.class );
        System.out.println(Delegate.That.class );
    }

    public static void main(String[] args) {
        new Test().ll();
    }

    public class That{

    }
}
