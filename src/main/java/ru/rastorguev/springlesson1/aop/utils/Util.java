package ru.rastorguev.springlesson1.aop.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class Util {

    //для того чтобы не было ошибки при использовании с методом в котором возвращается примитив.
    public synchronized static Object returnRequiredType(Class clazz) {
        switch (clazz.toString()) {
            case ("void"): return null;
            case ("byte"): return new Byte("");
            case ("short"): return new Short((short) 0);
            case ("int"): return new Integer(0);
            case ("long"): return new Long(0);
            case ("char"): return new Character('0');
            case ("float"): return new Float(0);
            case ("double"): return new Double(0);
            case ("boolean"): return false;
            default: return null;
        }
    }

}
