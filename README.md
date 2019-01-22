# restaurantSimulation
Simulates a restaurant service using Java synchronization primitives with limited cooks serving the waiting customers in a highly efficient manner managing all the tables and machines available.

==============================================================================================================================

My Implemetation contains 10 java files with the main function in Restaurant.java 

The java files are:

------------------------------------------------------------------------

1.BurgerMachine.java : 
	
	Handles the burger machine and its time

2.CokeMachine.java :

	Handles the Coke machine and its time

3.Cook.java :

	Contains DataStructure for the Cooks

4.CookList.java :

	Maintains the cook's time and the current diner order details

5.Diners.java :

	Contains all the information of the corresponding diner

6.FriesMachine.java :

	Handles the Fries machine and its time

7.KitchenEntryGate.java :

	Acts like a kitchen gate where the cooks enter the kitchen to use the machines for 
preparing the order. Every cook decides and selects the locally best available machine 
	and uses it and continues with the remaining order.

8.Restaurant.java :

	The file which has the main function. It creates the diner and cook threads.

9.SundaeMachine.java :

	Handles the Sundae machine and its time

10.Tables.java :

	Handles the tables resources. Maintains a priority queue of latest available table and hence
 the diner gets fastest available table.



Along with these, I have attaches the make file which compiles the java files.

For running the project,


1. Type the following command for compiling:

		make

2. Type the following command for running the program:

		java Restaurant

3. Now enter the input similar to that of the test cases provided.


Note::
 I displayed all the outputs expected from the program as a log.

Please mail me for futher clarifications.
