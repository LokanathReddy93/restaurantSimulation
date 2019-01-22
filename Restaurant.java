import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
//The file which has the main function. It creates the diner and cook threads.
class CookThread extends Thread {
	Cook mycook;
	CookThread(Cook cook){
		mycook=cook;
	}
	public void run() {
		while(mycook.rBurgers+mycook.rFries+mycook.rCoke+mycook.rSundae>0) {
			KitchenEntryGate.bestAvailableMachine(mycook);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mycook.mydiner.machine1Time=mycook.machine1Time;
		mycook.mydiner.machine2Time=mycook.machine2Time;
		mycook.mydiner.machine3Time=mycook.machine3Time;
		mycook.mydiner.machine4Time=mycook.machine4Time;
		mycook.mydiner.servingTime=mycook.cookTime;
		Integer[] ca=new Integer[2];
		ca[0]=mycook.id;
		ca[1]=mycook.cookTime;
		CookList.addCook(ca);
	}
}
class DinerThread extends Thread{
	Diners mydiner;
	Cook myCook;
	CookThread worker;
	DinerThread(Diners diner){
		mydiner=diner;
	}
	static Integer criticalRegion1=0;
	public void run() {
		synchronized(criticalRegion1) {
			while(Tables.getAvailableTables()==0) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Tables.getSeated(mydiner);
		}
		myCook=new Cook();
		myCook.mydiner=mydiner;
		CookList.getCook(myCook);
		worker=new CookThread(myCook);
		worker.start();
		try {
			worker.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer[] temp=new Integer[2];
		temp[0]=myCook.mydiner.tableUsed;
		temp[1]=myCook.mydiner.servingTime+30;
		Tables.addTable(temp);
	}
}
public class Restaurant {
	public static void main(String args[]) throws InterruptedException {
		System.out.println("Enter Input");
		Scanner in = new Scanner(System.in);
		int N=in.nextInt();
		System.out.println("Number of diners ="+N);
		int nTables=in.nextInt();
		System.out.println("Number of Tables="+nTables);
		int nCooks=in.nextInt();
		System.out.println("Number of Cooks="+nCooks);
		Diners[] nDiners = new Diners[N];
		DinerThread[] nDinerThreads = new DinerThread[N];
		for(int i=1;i<=nTables;i++) {
			Integer[] t2=new Integer[2];
			t2[0]=i;
			t2[1]=0;
			Tables.addTable(t2);
		}
		for(int i=1;i<=nCooks;i++) {
			Integer[] t3=new Integer[2];
			t3[0]=i;
			t3[1]=0;
			CookList.addCook(t3);
		}
		int prev=0;
		for(int i=1;i<=N;i++) {
			nDiners[i-1]=new Diners();
			nDiners[i-1].id=i;
			nDiners[i-1].arrivingTime=in.nextInt();
			nDiners[i-1].nBurgers=in.nextInt();
			nDiners[i-1].nFries=in.nextInt();
			nDiners[i-1].ncoke=in.nextInt();
			nDiners[i-1].nsundae=in.nextInt();
			nDinerThreads[i-1]=new DinerThread(nDiners[i-1]);//,executor);
			nDinerThreads[i-1].start();
			TimeUnit.MILLISECONDS.sleep(10*(nDiners[i-1].arrivingTime-prev));
			prev=nDiners[i-1].arrivingTime;
		}
		int lastDinerTime=-1;
		for(int i=1;i<=N;i++) {
			nDinerThreads[i-1].join();
			System.out.println("----------Diner "+i+"------------");
			System.out.println("id="+nDiners[i-1].id);
			System.out.println("Diner arrivingTime="+nDiners[i-1].arrivingTime);
			System.out.print("nBurgers="+nDiners[i-1].nBurgers);
			System.out.print("; nFries="+nDiners[i-1].nFries);
			System.out.print("; coke="+nDiners[i-1].ncoke);
			System.out.println("; sundae="+nDiners[i-1].nsundae);
			System.out.println("Diner Seating Time="+nDiners[i-1].seatingTime);
			System.out.println("table Allowted ="+nDiners[i-1].tableUsed);
			System.out.println("Cook who took the order is Cook "+nDiners[i-1].cookName);
			if(nDiners[i-1].machine1Time!=-1) System.out.println("Burger machine was used at "+nDiners[i-1].machine1Time);
			if(nDiners[i-1].machine2Time!=-1) System.out.println("Fries machine was used at "+nDiners[i-1].machine2Time);
			if(nDiners[i-1].machine3Time!=-1) System.out.println("Coke machine was used at "+nDiners[i-1].machine3Time);
			if(nDiners[i-1].machine4Time!=-1) System.out.println("Sundae machine was used at "+nDiners[i-1].machine4Time);
			System.out.println("Food was brought to table at "+nDiners[i-1].servingTime);
			if(nDiners[i-1].servingTime+30>lastDinerTime) lastDinerTime=nDiners[i-1].servingTime+30;
		}
		System.out.println("------------------------------------------------------");
		System.out.print("Last Diner leaves at "+lastDinerTime);
		in.close();
		return;
	}
}
