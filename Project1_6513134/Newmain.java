package Project1_6513134;
//Puthipong Yomabut 6513134
//Phattaradanai Sornsawang 6513172
//Patiharn Kamenkit 6513170
//Praphasiri Wannawong 6513116
import java.lang.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

interface HotelItem {
    String get_name();

    double get_rate();


    double cal_rate();
}
class Room implements HotelItem ,Comparable<Room>{
    private String name;
    private double rate;
    private int room;
    Room(String n, double r) {
        this.name = n;
        this.rate = r;
    }
    Room(String n,double r,int a){
        this.name=n;
        this.rate=r;
        this.room=a;
    }
    @Override
    public String get_name() {

        return this.name;
    }
    @Override
    public double get_rate() {
        return this.rate;
    }
    public int get_room(){
        return this.room;
    }

    public double cal_rate() {
        double servicecharge = rate * 0.1;
        double vat = (rate + servicecharge) * 0.07;
        return rate + servicecharge + vat;
    }
    public int compareTo(Room other) {
        // Compare rooms based on their rate
        if (this.room < other.room)       return 1;
        else if (this.room > other.room)  return -1;
        else                        return 0;
    }
}
class Meal implements HotelItem {
    private final String name;
    private double rate;

    Meal(String n, double r) {
        this.name = n;
        this.rate = r;
    }
    @Override
    public String get_name() {
        return this.name;
    }
    @Override
    public double get_rate() {
        return this.rate;
    }
    public double cal_rate() {
        return this.rate;
    }
}
class Customer {
    private String name;
    private double cashback;
    Customer(String n,double cb) {
        this.name = n;
        this.cashback = cb;
    }
    public void set_cashback(double cashback) {
        this.cashback = cashback;
    }
    public String get_name() {
        return this.name;
    }

    public double get_cashback() {
        return this.cashback;
    }
}
class Booking {
    private int booking_id;
    private Customer customer;
    private double final_bill;
    private int night, single, twin, triple, dorm, breakfast;
    public double sumsingle=0,sumtwin=0,sumtriple=0,sumdorm=0,sumbreakfast=0,sumcostmeal=0;
    public int room_sing=0,room_twin=0,room_triple=0,room_dorm=0,count_breakfast=0;

    public Booking(int id, String custom, int n, int sg, int tw, int tp, int dm, int bf, double cashBack) {
        this.booking_id = id;
        this.customer = new Customer(custom,cashBack);
        this.night = n;
        this.single = sg;
        this.twin = tw;
        this.triple = tp;
        this.dorm = dm;
        this.breakfast = bf;
    }
    public int get_count_breakfast(){
        return this.count_breakfast;
    }
    public int get_room_sing(){
        return this.room_sing;
    }
    public int get_room_twin(){
        return this.room_twin;
    }
    public int get_room_triple(){
        return this.room_triple;
    }
    public int get_room_dorm(){
        return this.room_dorm;
    }
    public Customer getCustomer(){
        return this.customer;
    }
    public int get_booking_id(){
        return this.booking_id;
    }
    public double get_single() {
        return this.sumsingle;
    }
    public double get_twin() {
        return this.sumtwin;
    }
    public double get_triple() {
        return this.sumtriple;
    }
    public double get_dorm() {
        return this.sumdorm;
    }
    public double get_breakfast() {
        return this.sumbreakfast;
    }
    public double get_costmeal() {
        return sumcostmeal;
    }
    public void process(ArrayList<Room> rooms, ArrayList<Meal> meals) {

        double[] costs = new double[rooms.size()];
        for (int z = 0; z < rooms.size(); z++) {
            Room currentRoom = rooms.get(z);
            costs[z] = currentRoom.cal_rate();
        }
        double costformeal = 0.0;  // Initialize cost for meal
        if (!meals.isEmpty()) {
            Meal currentMeal = meals.get(0);
            costformeal = currentMeal.cal_rate();
        }

        double costs_single = night * single * costs[0] ;
        double costs_twin = night * twin * costs[1];
        double costs_triple = night * triple * costs[2];
        double costs_dorm = night * dorm * costs[3];
        double costs_breakfast = night * breakfast * costformeal;
        int count_roomsing=night*single;
        int count_roomtwin=night*twin;
        int count_roomtriple=night*triple;
        int count_roomdowm=night*dorm;
        int count_bf=night*breakfast;

        double cashback_2 = customer.get_cashback();
        double total_room = night * ((single * costs[0]) + (twin * costs[1]) + (triple * costs[2]) + (dorm * costs[3]));
        double cashback_3 = total_room * 0.05;
        double total_meal = night * breakfast * costformeal;
        double total_bill = total_room + total_meal;
        double final_bill = total_bill;
        double cash_remain = 0;
        double cashback_half = 0;
        customer.set_cashback(cashback_3);
        System.out.printf("Booking  %2d, %-5s,  %3d  nights  >>  Single Room (%2d)      Twin Room (%2d)     Triple Room (%2d)     Single Dorm Bed (%2d)   Breakfast (%2d)  \n", booking_id, customer.get_name(), night, single, twin, triple, dorm, breakfast);
        System.out.printf("Available cashback   =  %,-7.0f   >>  Total room price++    = %,10.2f       with service charge and VAT \n",cashback_2, total_room);
        System.out.printf("                                  >>  Total meal price      = %,10.2f\n", total_meal);
        if(cashback_2>(final_bill*0.5)){
            cashback_half = (final_bill*0.5);
            final_bill = total_bill-cashback_half;
            cash_remain = cashback_2 - cashback_half;
            cashback_2 = cashback_half;
        } else final_bill = total_bill-cashback_2;
        System.out.printf("                                  >>  Total bill            = %,10.2f       redeem = %,d \n", total_bill, (int)(cashback_2));
        System.out.printf("                                  >>  Final bill            = %,10.2f       cashback for next booking = %,.0f\n", final_bill,(cashback_3 + cash_remain));

        count_breakfast += count_bf ;
        room_sing += count_roomsing;
        room_twin += count_roomtwin;
        room_triple += count_roomtriple;
        room_dorm += count_roomdowm;

        sumsingle += costs_single;
        sumtwin += costs_twin;
        sumtriple += costs_triple;
        sumdorm += costs_dorm;
        sumbreakfast += costs_breakfast;
        sumcostmeal += total_meal;
    }
}
class MyInputReader {
    private String path,filename;
    private Scanner keyboardScan;
    public MyInputReader(String P,String Fn) {
        this.path=P;
        this.filename=Fn;
        this.keyboardScan = new Scanner(System.in);
    }
    public void processline(String line,ArrayList<Room> r,ArrayList<Meal> m ){
        try {
            String[] col = line.split(",");
            String item_type = col[0].trim();
            String item_name = col[1].trim();
            double rate = Double.parseDouble(col[2].trim());
            if (rate<0){throw new ArithmeticException("For input : \""+rate+"\""  );}
            if (item_type.equals("R")) {
                r.add(new Room(item_name, rate));
            } else if (item_type.equals("M")) {
                m.add(new Meal(item_name, rate));
            }
        }
        catch(ArithmeticException e){
            System.out.println( e +"\n" + line);
            System.out.print( "\n");
        }
        catch(RuntimeException e ){
            System.out.println(e + "\n" + line);
            System.out.print( "\n");
        }

    }
    public void processline2(String line,ArrayList<Booking> b){
        try {
            double cashBack = 0.0;
            String[] col = line.split(",");
            int booking_id = Integer.parseInt(col[0].trim());
            String customer_name = col[1].trim();
            int night = Integer.parseInt(col[2].trim());
            int single = Integer.parseInt(col[3].trim());
            int twin = Integer.parseInt(col[4].trim());
            int triple = Integer.parseInt(col[5].trim());
            int dorm = Integer.parseInt(col[6].trim());
            int breakfast = Integer.parseInt(col[7].trim());
            if (night<0){
                throw new ArithmeticException("For input : \""+night+"\""  );
            }
            if (single<0){
                throw new ArithmeticException("For input : \""+single+"\""  );
            }
            if (twin<0){
                throw new ArithmeticException("For input : \""+twin+"\""  );
            }
            if (triple<0){
                throw new ArithmeticException("For input : \""+triple+"\""  );
            }
            if (dorm<0){
                throw new ArithmeticException("For input : \""+dorm+"\""  );
            }
            if (breakfast<0){;
                throw new ArithmeticException("For input : \""+breakfast+"\""  );
            }
            Booking booking = new Booking(booking_id, customer_name, night, single, twin, triple, dorm, breakfast,0);
            b.add(booking);
        }
        catch(ArithmeticException e){
            System.out.printf( e +"\n" + "[" + line + "]");
            System.out.printf(" --> skip this booking");
            System.out.println( "\n");
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.printf( e +"\n" + "[" + line + "]");
            System.out.printf(" --> skip this booking");
            System.out.println( "\n");
        }
        catch(RuntimeException e ){
            System.out.printf( e +"\n" + "[" + line + "]");
            System.out.printf(" --> skip this booking");
            System.out.println( "\n");
        }
        catch(Exception e){
            System.out.printf( e +"\n" + "[" + line + "]");
            System.out.printf(" --> skip this booking");
            System.out.println( "\n");
        }
    }
    public void OpenFile1(ArrayList<Room> r, ArrayList<Meal> m) {
        boolean opensuccess = false;
        while(!opensuccess) {
            try(Scanner scan = new Scanner(new File(path+filename));){
                opensuccess = true;
                System.out.print( "\n");
                for( int linenumber=0;scan.hasNext();linenumber++) {
                    //String line = scan.nextLine();
                    processline(scan.nextLine(),r,m);
                }
                System.out.println("Read hotel data from file " + path+"\n");
                for (Room room : r) {
                    System.out.printf("%-15s    rate = %,10.2f   rate++ = %,10.2f \n", room.get_name(), room.get_rate(), room.cal_rate());
                }
                for (Meal meal : m) {
                    System.out.printf("%-15s    rate = %,10.2f   rate++ = %,10.2f \n", meal.get_name(), meal.get_rate(), meal.cal_rate());
                }
            }catch(FileNotFoundException e) {
                System.out.println(e);
                System.out.println("New file name = ");
                filename=keyboardScan.next();
            }
        }
    }
    public void OpenFile2(ArrayList<Booking> b,ArrayList<Room> r,ArrayList<Meal> m){
        boolean opensuccess = false;
        while(!opensuccess) {

            try(Scanner scan2 = new Scanner(new File(path+filename));){
                opensuccess = true;
                System.out.print( "\n");
                for( int linenumber=0;scan2.hasNext();linenumber++){
                    try {
                        processline2(scan2.nextLine(), b);
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }
                System.out.println("Read booking data from file " + path+"\n");
                System.out.println("==== Booking Processing ====");
                for (Booking booking : b){
                    Customer customer = booking.getCustomer();
                    String customer_name = customer.get_name();
                    for (int i = b.size()-1 ; i>=0 ;i--){
                        Booking booking_2 = b.get(i);
                        if(booking_2.get_booking_id() >= booking.get_booking_id()){
                            continue;
                        }
                        Customer customer_2 = booking_2.getCustomer();
                        if(customer_2.get_name().equals(customer_name)){
                            double oldCashBack = customer_2.get_cashback();
                            customer.set_cashback(oldCashBack);
                            break;
                        }
                    }
                    booking.process(r,m);
                }
            }
            catch(FileNotFoundException e) {
                System.out.println(e);
                System.out.println("New file name = ");
                filename=keyboardScan.next();
            }
            catch(ArithmeticException e){
                System.out.println( e +"\n" );
                System.out.print( "\n");
            }
            catch(ArrayIndexOutOfBoundsException e){
             System.out.println( e +"\n" );
             System.out.print( "\n");
            }
        }
    }
}
public class Newmain {
    public static void main(String[] args) {
        String path = "src/main/Java/Project1_6513134/";
        String[] file1 = {"hotel.txt", "wrong.txt"};
        String[] file2 = {"bookings_errors.txt", "wrong.txt"};
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Meal> meals = new ArrayList<>();
        ArrayList<Booking> bookings = new ArrayList<>();
        MyInputReader cal = new MyInputReader(path, file1[1]);
        cal.OpenFile1(rooms,meals);

        MyInputReader cal2 = new MyInputReader(path, file2[1]);
        cal2.OpenFile2(bookings,rooms,meals);

        double totalMealSales = 0;
        double total_sing=0;
        double total_twin=0;
        double total_triple=0;
        double total_dorm =0;
        int room_sing=0;
        int room_twin=0;
        int room_triple=0;
        int room_dorm=0;
        int bf=0;
        for (Booking booking : bookings) {
            totalMealSales += booking.get_costmeal();
            total_sing += booking.get_single();
            total_twin += booking.get_twin();
            total_triple += booking.get_triple();
            total_dorm += booking.get_dorm();
            room_sing += booking.get_room_sing();
            room_twin += booking.get_room_twin();
            room_triple += booking.get_room_triple();
            room_dorm += booking.get_room_dorm();
            bf += booking.get_count_breakfast();
        }

        Room []r = new Room[4];
        r[0]= new Room(rooms.get(0).get_name(),total_sing,room_sing);
        r[1]=new Room(rooms.get(1).get_name(),total_twin,room_twin);
        r[2]=new Room(rooms.get(2).get_name(),total_triple,room_triple);
        r[3]=new Room(rooms.get(3).get_name(),total_dorm,room_dorm);
        ArrayList<Room> sum_room = new ArrayList<>();
        for (int i=0; i < r.length; i++) {
            sum_room.add(r[i]);
        }
        Collections.sort(sum_room);
        System.out.println();
        System.out.println("===== Sorted Room Summary =====");
        for (Room p : sum_room) {
            System.out.printf("%-18s     total sales = %3d rooms     %,10.2f  Baht\n",p.get_name(),p.get_room(),p.get_rate());
        }
        System.out.println();
        System.out.println("===== Meal Summary =====");
        System.out.printf("%-18s     total sales = %3d heads    %,11.2f baht%n", meals.get(0).get_name(),bf, totalMealSales);


    }
}