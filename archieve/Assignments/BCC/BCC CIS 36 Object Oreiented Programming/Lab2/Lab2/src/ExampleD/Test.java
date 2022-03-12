package ExampleD;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {

    public static void main(String args[]) {
        try {
            Class cls = Class.forName("ExampleD.ConcreteClass");

            Method methlist[] = cls.getDeclaredMethods();

            Method m = methlist[0];
            System.out.println("Run Method name  = " + m.getName());
            System.out.println("decl class = " + m.getDeclaringClass());
            System.out.println("super class = " + cls.getSuperclass());

            Field[] fields = cls.getFields();

            for(int i = 0; i < fields.length; i++) {
                System.out.println(" class field NAME = " + fields[i].getName());
            }
            fields = cls.getDeclaredFields();
            for(int i = 0; i < fields.length; i++) {
                System.out.println(" class Declared field NAME = " + fields[i].getName());
            }


            Class[] inter = cls.getInterfaces();
            for(int i = 0 ; i < inter.length; i++) {
                System.out.println(" InterFace NAME = " + inter[i].getName());
            }

            System.out.println(" Modifiers = " + cls.getModifiers());

            Annotation[] annotations = cls.getAnnotations();
            for(int i = 0 ; i< annotations.length; i++) {
                System.out.println(" annotations = " + annotations[i].toString());
            }


            Constructor[] construc = cls.getConstructors();
            for(int i = 0 ; i< construc.length; i++) {
                System.out.println(" construc class = " + construc[i].getName());
            }
            int idx = 1;
            Method[] meth = cls.getDeclaredMethods();
            Object obj = null;
            for(int i = 1 ; i< meth.length; i++) {
                System.out.println(" public Method = " + meth[i].getName());
                Class<?>[] a = meth[i].getParameterTypes();

                for(int j = 0 ; j < a.length; j++) {
                    if("int".equals(a[i].getName().substring(a[i].getName().length()-4, a[i].getName().length()-1))) {
                        obj = new Integer(1);
                    }else {
                        obj =new String("HELLO");
                    }
                }
                System.out.println(obj);
                meth[i].invoke(obj, null);
                idx++;
            }
        } catch (Exception e) { }
    }
}
