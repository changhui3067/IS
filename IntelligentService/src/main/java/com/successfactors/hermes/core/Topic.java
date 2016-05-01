package com.successfactors.hermes.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The topic is a channel to narrow down publishing or listening of events.
 * They are hierarchical in nature so any event posted to a child node will be received by
 * subscribers listening on parent nodes.
 *
 * @author ddiodati
 */
public class Topic {

    private Topic parent;
    private Set<Topic> children;
    private String name;

    /**
     * Creates a new topic. Node are separated by the "." character.
     *
     * @param name The string name
     */
    public Topic(String name) {
        this.name = name;
        int index = name.lastIndexOf(".");
        children = new HashSet<Topic>();

        if (index > 0) {
            Topic pnode = new Topic(name.substring(0, index));
            this.setParent(pnode);
            pnode.add(this);
        }
    }

    /**
     * Create a new topic withh a parent.
     *
     * @param name   The topic fragment.
     * @param parent The parent node that the topic fragment will be added to.
     */
    public Topic(String name, Topic parent) {
        this(name);

        Topic root = this;
        while (root.getParent() != null) {
            root = root.getParent();
        }
        root.setParent(parent);
        parent.add(root);
    }

    public static void main(String[] args) {
        Topic t = new Topic("a.b.c");
        System.out.print(t);
    }

    /**
     * Gets the parent node.
     *
     * @return Topic
     */
    public Topic getParent() {
        return parent;
    }

    /**
     * Sets parent node.
     *
     * @param t Topic
     */
    public void setParent(Topic t) {
        this.parent = t;
    }

    /**
     * Gets all the child nodes of this element.
     *
     * @return Set of child topics.
     */
    public Set<Topic> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    /**
     * Adds a single child node to this topic.
     *
     * @param child topic to append.
     * @return The child that just got added, to allow for looping adds.
     */
    public Topic add(Topic child) {
        children.add(child);
        child.setParent(this);
        return child;
    }

    /**
     * Removes a topic node.
     *
     * @param child The node to remove.
     * @return true if successful.
     */
    public boolean remove(Topic child) {
        return children.remove(child);
    }

    /**
     * Returns an iterator that traverse up the tree from child to parent.
     *
     * @return Iterator to use for looping.
     */
    public Iterator getParentIterator() {
        return new ParentIterator(this);
    }

    /**
     * {@inheritDoc}.
     */
    public String toString() {
        return name.toString();
    }

    /**
     * returns topic name
     *
     * @return topic name
     */

    public String getName() {
        return name;
    }


    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Topic) {
            Topic tOther = (Topic) other;
            return this.name != null && tOther.name != null && this.name.equals(tOther.name);
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}.
     */
    public int hashCode() {
        return this.name.hashCode();

    }

    /**
     * Inner iterator that returns the child first and traverses up the topic nodes.
     *
     * @author ddiodati
     */
    private static final class ParentIterator implements Iterator {


        private Topic current;

        public ParentIterator(Topic t) {
            current = t;
        }

        /**
         * {@inheritDoc}.
         */
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return current != null;
        }

        /**
         * {@inheritDoc}.
         */
        public Topic next() {
            Topic next = current;
            current = current.getParent();
            return next;
        }

        /**
         * {@inheritDoc}.
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


}
