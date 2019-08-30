package test;

import directory.Directory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Test {


    List<String> list = new ArrayList<>();
    Directory<String> tst = new Directory<>();


    void before() {
        list.add("Chris");
        list.add("Chris Cairns");
        list.add("Chris Harris");


        tst.add("Chris Harris", "Some");
        tst.add("Chris", "Some");
        tst.add("Harry Potter", "Some");
        tst.add("Chris Cairns", "Some");
    }

    public void runTests(){
        before();
        Queue<String> contacts = tst.searchContact("Chris");
        if (testSearch(list,contacts)){
            System.out.println("Search Test passed");
        }else{
            System.out.println("Search Tests failed");
        }
        if (testContactIsInDirectory()){
            System.out.println("Contains contact Test passed");
        }else{
            System.out.println("Contains contact Tests failed");
        }
    }

    private boolean testSearch(List<String> list, Queue<String> contacts){
        int i = 0;
        for (String x: contacts){
            try {
                assert list.get(i).equals(x);
                i++;
            }catch (AssertionError e){
                return false;
            }
        }
        return true;
    }

    private boolean testContactIsInDirectory(){

        try {
            assert tst.contains("Chris") == list.contains("Chris");
        }catch (AssertionError e){
            return false;
        }
        return true;
    }
}
