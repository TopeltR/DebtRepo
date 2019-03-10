package ee.taltech.debty.model.graph;

import ee.taltech.debty.entity.Debt;
import ee.taltech.debty.entity.Person;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Graph {

    private Map<Person, Node> people = new HashMap<>();
    private List<Vertex> vertices = new ArrayList<>();

    public Graph(List<Person> people) {
        people.forEach(person -> this.people.put(person, new Node(person)));
    }

    public void addRelation(BigDecimal sum, Person from, Person to) {
        if (to == from) return;
        Vertex vertex = new Vertex(sum, from, to);
        this.vertices.add(vertex);
        this.people.get(from).getOutgoingVertices().add(vertex);
        this.people.get(to).getIncomingVertices().add(vertex);
    }

    private void deleteRelation(Vertex vertex) {
        this.people.get(vertex.getFrom()).getOutgoingVertices().remove(vertex);
        this.people.get(vertex.getTo()).getIncomingVertices().remove(vertex);
        this.vertices.remove(vertex);
    }

    public List<Debt> getAllDebts() {
        return this.vertices.stream().map(vertex ->
                Debt.builder()
                        .sum(vertex.getSum())
                        .payer(vertex.getFrom())
                        .receiver(vertex.getTo()).build())
                .collect(Collectors.toList());
    }

    public void optimizeDebts() {
        boolean optimized = false;

        while (!optimized) {
            optimized = true;

            for (Node node : this.people.values()) {
                Person person = node.getPerson();

                while (!node.getIncomingVertices().isEmpty() && !node.getOutgoingVertices().isEmpty()) {
                    optimized = false;

                    Vertex minIncoming = node.getIncomingVertices().poll();
                    Vertex minOutgoing = node.getOutgoingVertices().poll();

                    shortenPath(person, minIncoming, minOutgoing);
                }
            }
        }
    }

    private void shortenPath(Person person, Vertex minIncoming, Vertex minOutgoing) {
        BigDecimal incSum = Objects.requireNonNull(minIncoming).getSum();
        BigDecimal outSum = Objects.requireNonNull(minOutgoing).getSum();
        int compareTo = incSum.compareTo(outSum);
        final int EQUAL = 0;

        Person from = minIncoming.getFrom();
        Person to = minOutgoing.getTo();

        if (compareTo == EQUAL) {

            addRelation(incSum, from, to);
        } else if (compareTo < EQUAL) {
            // incoming sum is less than outgoing sum

            if (from != to) addRelation(incSum, from, to);
            addRelation(outSum.subtract(incSum), person, to);
        } else {
            // outgoing sum is less than incoming sum

            if (from != to) addRelation(outSum, from, to);
            addRelation(incSum.subtract(outSum), from, person);
        }

        deleteRelation(minOutgoing);
        deleteRelation(minIncoming);
    }

}
