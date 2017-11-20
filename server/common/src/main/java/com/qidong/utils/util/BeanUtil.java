package com.qidong.utils.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BeanUtil<S,T> {
    private Class<T> targetType;

    public BeanUtil(Class<T> targetType) {
        this.targetType = targetType;
    }

    public static void copyProperties(Object source, Object target){
        if (source != null && target != null){
            try {
                PropertyUtils.copyProperties(target, source);
            }catch (Exception e){
                throw new RuntimeException("类型转换时出错：", e);
            }
        }
    }

    public List<T> copyList(List<S> src){
        if (null==src || src.size()==0){
            return null;
        }

        List<T> target = new ArrayList<T>();
        for (S s : src){
            T t = BeanUtils.instantiate(targetType);
            copyProperties(s, t);
            target.add(t);
        }
        return target;
    }
}
