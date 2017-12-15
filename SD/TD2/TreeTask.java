package fr.unice.miage.sd.tp2;

public class TreeTask implements Runnable {
	Integer predIndex;
	TreeTask[] TasksArray;
	String myName;
	int myIndex;
	Thread myThread;

	public TreeTask(String Name, MutableInt index, TreeTask[] TA, int pred) {
		TasksArray = TA;
		myName = Name;
		predIndex = pred;
		myIndex = index.val();

		TasksArray[myIndex] = this;
		index.increment(1);
		myThread = new Thread(this);
		System.out.format("Creating Runnable %s with index %d, pred is %s \n", myName, myIndex, predIndex);

	}

	public String toString() {
		return String.format("Exec_Object number %d, id %s, pred is %d, thread name is %s", myIndex, myName, predIndex,
				myThread.getName());
	}

	public void run() {

		System.out.format("%s Started\n", myName);

		Thread previousTask = null;
		if (predIndex >= 0) {
			previousTask = TasksArray[predIndex].myThread;
			try {
				previousTask.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.format("%s Running\n", myName);
		zzzz();
		System.out.format("%s Completed\n", myName);
	}

	public void generateSon(int depth, MutableInt firsfreetindex) {
		if (depth == 0)
			return;

		String leftName = myName + "0";
		String rightName = myName + "1";

		TreeTask Left = new TreeTask(leftName, firsfreetindex, TasksArray, myIndex);
		TreeTask Right = new TreeTask(rightName, firsfreetindex, TasksArray, myIndex);

		Left.generateSon(depth - 1, firsfreetindex);
		Right.generateSon(depth - 1, firsfreetindex);

	}

	private void zzzz() {
		try {
			Thread.sleep((int) (2e4 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		int depthofTree = 4;
		TreeTask[] MyExObjects = new TreeTask[256];

		MutableInt index = new MutableInt(0);

		TreeTask RootTask = new TreeTask("R", index, MyExObjects, -1);

		RootTask.generateSon(depthofTree, index);

		System.out.format("%d Items Generated\n", index.val());
		for (int i = 0; i < index.val(); i++) {
			if (MyExObjects[i] != null)
				System.out.println(MyExObjects[i].toString());
			else
				System.out.println(i + " is null ");
		}
		for (int i = 0; i < index.val(); i++)
			MyExObjects[i].myThread.start();

	}

}
