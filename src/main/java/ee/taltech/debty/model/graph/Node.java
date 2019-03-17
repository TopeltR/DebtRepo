package ee.taltech.debty.model.graph;

import ee.taltech.debty.entity.Person;
import lombok.Data;

import java.util.PriorityQueue;
import java.util.Queue;

@Data
class Node {

    private Person person;
    private Queue<Vertex> incomingVertices = new PriorityQueue<>((o1, o2) -> o2.getSum().intValue() - o1.getSum().intValue());
    private Queue<Vertex> outgoingVertices = new PriorityQueue<>((o1, o2) -> o2.getSum().intValue() - o1.getSum().intValue());

    Node(Person person) {
        this.person = person;
    }

}
