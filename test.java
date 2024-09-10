package Nhom5_N01;

public class test {
    String title;
    String author;
    int numPages;
    test() { }

    public test(String t, String a, int p) {
        title = t;
        author = a;
        numPages = p;
    }

    @Override
    public String toString() {
        return "(" + "\"" + title + "\"" + ", " + "\"" + author + "\"" + ", " + numPages + ")";
    }

    public static void main(String[] args) {
        test myObj = new test("The TeXBook", "Donald Knuth", 483);
        System.out.println(myObj);
    }
}
