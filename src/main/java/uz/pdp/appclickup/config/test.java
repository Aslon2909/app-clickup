package uz.pdp.appclickup.config;


public class test {
    public static void main(String[] args) {
        int a = 5;
        for (int i = 0; i < a; i++) {
            for (int k = a; k > i; k--) {
                System.out.print(" ");
            }
            for (int j = 0; j < i; j++) {

                System.out.print("* ");
            }
            System.out.println();
        }
    }
}
