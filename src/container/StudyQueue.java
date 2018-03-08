package container;

import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by kanyuxia on 2017/1/14.
 */
public class StudyQueue {

    public static void main(String[] args){
        StudyQueue studyQueue = new StudyQueue();
        Queue<People> queue = new LinkedList<People>();
        studyQueue.fill(queue);
        studyQueue.printf(queue);
        System.out.println("PriorityQueue");
        PriorityQueue<People> priorityQueue = new PriorityQueue<People>();
        studyQueue.fill(priorityQueue);
        studyQueue.printf(priorityQueue);
        System.out.println("PriorityQueue with Comparator");
        PriorityQueue<People> priorityQueue1 = new PriorityQueue<People>(Collections.reverseOrder());
        studyQueue.fill(priorityQueue1);
        studyQueue.printf(priorityQueue1);
    }

    public Queue fill(Queue<People> queue){
        queue.offer(new People(10));
        queue.offer(new People(13));
        queue.offer(new People(11));
        queue.offer(new People(5));
        return queue;
    }

    public void printf(Queue<People> queue){
        while(queue.peek() != null){
            System.out.print(queue.poll()+"\t");
        }
        System.out.println();
    }
}
