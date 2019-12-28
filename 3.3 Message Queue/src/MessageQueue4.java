import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Scanner;

public class MessageQueue4 {
	private static int n_ids;

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Message> queue = new LinkedBlockingQueue<>(10);

		Scanner in = new Scanner(System.in);
		System.out.println("How many consumers do you want?");
		int n = in.nextInt();

		System.out.println("How many producers do you want");
		int m = in.nextInt();

		List<Producer> ProducerList = new ArrayList<>();
		List<Consumer> ConsumerList = new ArrayList<>();
		for (int i=0; i<m; i++){
			ProducerList.add(new Producer(queue, n_ids++));
		}

		for (int i=0; i<n; i++){
			ConsumerList.add(new Consumer(queue, n_ids++));
		}

		for(Producer x: ProducerList){
			new Thread(x).start();
		}
		for(Consumer y: ConsumerList){
			new Thread(y).start();
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (n > m){
			for (int i=0; i < (n-m); i++){
				Message stop = new Message("stop");
				queue.put(stop);
			}
			for(Producer z: ProducerList){
				z.stop();
			}
		}else {
			for(Producer z: ProducerList){
				z.stop();
		}
		}
	}
}
