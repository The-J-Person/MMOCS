package server;

public class MonsterSpawner extends Thread {
	int amount;
	int ratio;

	public MonsterSpawner() {
		amount = 0;
		ratio = 3;
		start();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(30000);
				if(WorldMap.getInstance().howmanyplayers()*ratio>amount) WorldMap.getInstance().addmonster();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
