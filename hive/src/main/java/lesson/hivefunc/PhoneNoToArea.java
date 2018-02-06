package lesson.hivefunc;

//import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.HashMap;

public class PhoneNoToArea { // extends UDF {


    private static HashMap<String, String> hashMap = new HashMap<>();

    static {

        hashMap.put("1381", "beijing");
        hashMap.put("1382", "tianjin");
        hashMap.put("1383", "shanghai");
        hashMap.put("1384", "guangzhou");

    }


    private void evaluate() {

    }

}
