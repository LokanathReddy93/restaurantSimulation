//Handles the Sundae machine and its time
public class SundaeMachine {
	static int availTime=0;
	synchronized static void addToQueue(int count) {
		availTime+=count*1;
	}
}
