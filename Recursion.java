package Nhom5_N01;

public class Recursion {
  int num;
  Recursion() {} 
  Recursion(int number) {} 

  public int Recursion(int number) {
    this.num = number;
    if (this.num <= 1)
      return 1;
    else
      return this.num * Recursion(this.num - 1);
  }

}
