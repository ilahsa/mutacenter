import com.ifeng.ad.mutacenter.SQLConstant;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by wangting3 on 2019/1/24.
 */
public class SqlTextTest {
    public static void main(String[] args) {
        Class clazz = SQLConstant.class;
        Field[] fields =clazz.getFields();

        Arrays.stream(fields).map(Field::getName).forEach(fname -> {
            try {
                if(fname.startsWith("SQL_")) {
                    System.out.println();
                    System.out.println("==========================");
                    System.out.println(fname+":");
                    System.out.print(SQLConstant.class.getField(fname).get(null).toString());
                }
                else {
                    System.out.print(SQLConstant.class.getField(fname).get(null).toString());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
