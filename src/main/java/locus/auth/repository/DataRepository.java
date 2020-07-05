package locus.auth.repository;

public interface DataRepository <T>{

    void add(T dataObject);
    T getById(int id);
    void remove(T dataObject);
    void removeById(int id);
    boolean contains(T dataObject);
    boolean containsById(int id);
}
