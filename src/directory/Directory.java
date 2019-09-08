package directory;

import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("unchecked")
public class Directory<T> {

    private Contact<T> root;
    private int n;

    /**
     * Inserts the name into the Directory, overwriting the old value
     * with the new value if the name is already in it.
     * If the value is null, this effectively deletes the name from the symbol table.
     *
     * @param name  contact name
     * @param val the value
     */
    public void add(String name, T val) {
        if (name == null) {
            throw new IllegalArgumentException("calls add() with null name");
        }
        root = add(root, name, val, 0);
    }

    private Contact<T> add(Contact<T> contact, String name, T val, int d) {
        char c = name.charAt(d);
        if (contact == null) {
            contact = new Contact<T>();
            contact.c = c;
        }
        if (c < contact.c)
            contact.left = add(contact.left, name, val, d);
        else if (c > contact.c)
            contact.right = add(contact.right, name, val, d);
        else if (d < name.length() - 1)
            contact.middle = add(contact.middle, name, val, d + 1);
        else
            contact.value = val;
        return contact;
    }

    /**
     * Returns all of the contacts in the directory that start with query.
     * Returns the Default contact list if there is search miss
     *
     * @param query the query
     * @return all of the contacts in the set that start with query or default contacts
     */
    public Queue<String> searchContact(String query) {
        Queue<String> contacts = searchContactInDirectory(query);
        if (contacts.size()>0){
            return contacts;
        }else{
            return getDefaultContacts();
        }
    }

    private Queue<String> searchContactInDirectory(String query){
        if (query == null) {
            throw new IllegalArgumentException("calls searchContact() with null argument");
        }

        Queue<String> queue = new LinkedList<>();
        Contact<T> contact = get(root, query, 0);

        if (contact == null)
            return queue;
        if (contact.value != null)
            queue.add(query);

        collect(contact.middle, new StringBuilder(query), queue);

        return queue;
    }

    //  returns the last contact node matched with given query
    private Contact<T> get(Contact<T> contact, String query, int d) {
        if (contact == null) return null;
        if (query.length() == 0) throw new IllegalArgumentException("query must have length >= 1");

        char c = query.charAt(d);
        if (c < contact.c)
            return get(contact.left, query, d);
        else if (c > contact.c)
            return get(contact.right, query, d);
        else if (d < query.length() - 1)
            return get(contact.middle, query, d + 1);
        else
            return contact;
    }

    // all contacts in directory rooted at contact with given query
    private void collect(Contact<T> contact, StringBuilder query, Queue<String> queue) {
        if (contact == null) return;

        collect(contact.left, query, queue);

        if (contact.value != null)
            queue.add(query.toString() + contact.c);

        collect(contact.middle, query.append(contact.c), queue);

        query.deleteCharAt(query.length() - 1);

        collect(contact.right, query, queue);
    }


    /*
    * Returns the Default contact list if there is search miss
    */
    public Queue<String> getDefaultContacts() {
        Queue<String> listOfContacts = new LinkedList<>();
        collect(root, new StringBuilder(""), listOfContacts);
        return listOfContacts;
    }

    /**
     * Returns the value associated with the given contactName.
     * @param contactName the contact name
     * @return the value associated with the given contactName if the contactName is in the Directory
     *     and null if contactName is not in Directory
     */
    public T get(String contactName) {
        if (contactName == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (contactName.length() == 0) throw new IllegalArgumentException("contactName must have length >= 1");
        Contact<T> x = get(root, contactName, 0);
        if (x == null) return null;
        return x.value;
    }

    /**
     * Does this directory contains the given contact?
     * @param name the name
     * @return true if this symbol table contains contact and false otherwise
     */
    public boolean contains(String name) {
        if (name == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(name) != null;
    }
}
