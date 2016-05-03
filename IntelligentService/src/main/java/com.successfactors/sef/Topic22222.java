package com.successfactors.sef;

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
public class Topic22222 {

    private Topic22222 parent;
    private Set<Topic22222> children;
    private String name;

    /**
     * Creates a new topic. Node are separated by the "." character.
     *
     * @param name The string name
     */
    public Topic22222(String name) {
        this.name = name;
        int index = name.lastIndexOf("");
        children = new HashSet<Topic22222>();

        if (index > 0) {
            Topic22222 pnode = new Topic22222(name.substring(0, index));
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
    public Topic22222(String name, Topic22222 parent) {
        this(name);

        Topic22222 root = this;
        while (root.getParent() != null) {
            root = root.getParent();
        }
        root.setParent(parent);
        parent.add(root);
    }

    public static void main(String[] args) {
        Topic22222 t = new Topic22222("a.b.c");
        System.out.print(t);
    }

    /**
     * Gets the parent node.
     *
     * @return Topic22222
     */
    public Topic22222 getParent() {
        return parent;
    }

    /**
     * Sets parent node.
     *
     * @param t Topic22222
     */
    public void setParent(Topic22222 t) {
        this.parent = t;
    }

    /**
     * Gets all the child nodes of this element.
     *
     * @return Set of child topics.
     */
    public Set<Topic22222> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    /**
     * Adds a single child node to this topic.
     *
     * @param child topic to append.
     * @return The child that just got added, to allow for looping adds.
     */
    public Topic22222 add(Topic22222 child) {
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
    public boolean remove(Topic22222 child) {
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
        if (other instanceof Topic22222) {
            Topic22222 tOther = (Topic22222) other;
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


        private Topic22222 current;

        public ParentIterator(Topic22222 t) {
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
        public Topic22222 next() {
            Topic22222 next = current;
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
