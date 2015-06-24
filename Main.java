Public class Main {
 public static void main(String args[])
 {
  String name;
   int age;
  Scanner sc=new Scanner(System.in);
  System.out.println("Enter name:");
   name=sc.next();
  System.out.println("Enter age:");
   Person p=new Person(name,age);
  p.toString();    
  
 }
}