import java.util.Comparator;
import java.util.PriorityQueue;
// Acts like a kitchen gate where the cooks enter the kitchen to use the machines for 
//preparing the order. Every cook decides and selects the locally best available machine 
//and uses it and continues with the remaining order.
public class KitchenEntryGate {
	//static void 
	synchronized static void bestAvailableMachine(Cook cook) {
		int burgerAvailTime=BurgerMachine.availTime;
		int friesAvailTime=FriesMachine.availTime;
		int cokeAvailTime=CokeMachine.availTime;
		int sundaeAvailTime=SundaeMachine.availTime;
		int myTime=cook.cookTime;
		PriorityQueue < Integer[] > machinesAvailable = new PriorityQueue < Integer[] > (new Comparator<Integer[]>() {

			@Override
			public int compare(Integer[] o1, Integer[] o2) {
				
				return o1[1]-o2[1];
			}
			
		});
		boolean cookTimeUpdateNeeded=true;
		int minUpdate=Integer.MAX_VALUE;
		int relatedMachine=0;
		if(cook.rBurgers>0 && myTime>=burgerAvailTime) {
			cookTimeUpdateNeeded=false;
			Integer[] temp=new Integer[2];
			temp[0]=1;
			temp[1]=myTime-burgerAvailTime;
			machinesAvailable.add(temp);
		}
		if(cook.rFries>0 && myTime>=friesAvailTime) {
			cookTimeUpdateNeeded=false;
			Integer[] temp=new Integer[2];
			temp[0]=2;
			temp[1]=myTime-friesAvailTime;
			machinesAvailable.add(temp);
		}
		if(cook.rCoke>0 && myTime>=cokeAvailTime) {
			cookTimeUpdateNeeded=false;
			Integer[] temp=new Integer[2];
			temp[0]=3;
			temp[1]=myTime-cokeAvailTime;
			machinesAvailable.add(temp);
		}
		if(cook.rSundae>0 && myTime>=sundaeAvailTime) {
			cookTimeUpdateNeeded=false;
			Integer[] temp=new Integer[2];
			temp[0]=4;
			temp[1]=myTime-sundaeAvailTime;
			machinesAvailable.add(temp);
		}
		if(cookTimeUpdateNeeded) {
			if(cook.rBurgers>0 && minUpdate>=burgerAvailTime) {
				minUpdate=burgerAvailTime;
				relatedMachine=1;
			}
			if(cook.rFries>0 && minUpdate>=friesAvailTime) {
				minUpdate=friesAvailTime;
				relatedMachine=2;
			}
			if(cook.rCoke>0 && minUpdate>=cokeAvailTime) {
				minUpdate=cokeAvailTime;
				relatedMachine=3;
			}
			if(cook.rSundae>0 && minUpdate>=sundaeAvailTime) {
				minUpdate=sundaeAvailTime;
				relatedMachine=4;
			}
			cook.cookTime=minUpdate;
			if(relatedMachine==1) {
				cook.machine1Time=minUpdate;
				BurgerMachine.addToQueue(cook.rBurgers);
				cook.cookTime+=cook.rBurgers*5;
				cook.rBurgers=0;
			}
			if(relatedMachine==2) {
				cook.machine2Time=minUpdate;
				FriesMachine.addToQueue(cook.rFries);
				cook.cookTime+=cook.rFries*3;
				cook.rFries=0;
			}
			if(relatedMachine==3) {
				cook.machine3Time=minUpdate;
				CokeMachine.addToQueue(cook.rCoke);
				cook.cookTime+=cook.rCoke*2;
				cook.rCoke=0;
			}
			if(relatedMachine==4) {
				cook.machine4Time=minUpdate;
				SundaeMachine.addToQueue(cook.rSundae);
				cook.cookTime+=cook.rSundae*1;
				cook.rSundae=0;
			}
		}
		else {
			Integer[] temp2=machinesAvailable.poll();
			relatedMachine=temp2[0];
			if(relatedMachine==1) {
				BurgerMachine.availTime+=temp2[1];
				cook.machine1Time=cook.cookTime;
				BurgerMachine.addToQueue(cook.rBurgers);
				cook.cookTime+=cook.rBurgers*5;
				cook.rBurgers=0;
			}
			if(relatedMachine==2) {
				FriesMachine.availTime+=temp2[1];
				cook.machine2Time=cook.cookTime;
				FriesMachine.addToQueue(cook.rFries);
				cook.cookTime+=cook.rFries*3;
				cook.rFries=0;
			}
			if(relatedMachine==3) {
				//System.out.println("Diner:"+cook.mydiner.id);
				//System.out.println("Before CookTime:"+cook.cookTime);
				CokeMachine.availTime+=temp2[1];
				cook.machine3Time=cook.cookTime;
				CokeMachine.addToQueue(cook.rCoke);
				cook.cookTime+=cook.rCoke*2;
				cook.rCoke=0;
				//System.out.println("After CookTime:"+cook.cookTime);
			}
			if(relatedMachine==4) {
				SundaeMachine.availTime+=temp2[1];
				cook.machine4Time=cook.cookTime;
				SundaeMachine.addToQueue(cook.rSundae);
				cook.cookTime+=cook.rSundae*1;
				cook.rSundae=0;
			}
		}
		return;
	}
}
