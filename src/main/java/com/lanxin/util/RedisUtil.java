package com.lanxin.util;
import java.util.Collection;
import java.util.List;  
import java.util.Map;  
import java.util.Set;  
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.util.CollectionUtils;


public class RedisUtil {  

    private RedisTemplate<String, Object> redisTemplate;  

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //=============================common============================  
    /** 
     * ָ������ʧЧʱ�� 
     * @param key �� 
     * @param time ʱ��(��) 
     * @return 
     */
    public boolean expire(String key,long time){  
        try {  
            if(time>0){  
                redisTemplate.expire(key, time, TimeUnit.SECONDS);  
            }  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * ����key ��ȡ����ʱ�� 
     * @param key �� ����Ϊnull 
     * @return ʱ��(��) ����0����Ϊ������Ч 
     */  
    public long getExpire(String key){  
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);  
    }  

    /** 
     * �ж�key�Ƿ���� 
     * @param key �� 
     * @return true ���� false������ 
     */  
    public boolean hasKey(String key){  
        try {  
            return redisTemplate.hasKey(key);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * ɾ������ 
     * @param key ���Դ�һ��ֵ ���� 
     */  
    @SuppressWarnings("unchecked")  
    public void del(String ... key){  
        if(key!=null && key.length>0){  
            if(key.length==1){  
                redisTemplate.delete(key[0]);  
            }else{  
                redisTemplate.delete(CollectionUtils.arrayToList(key));  
            }  
        }  
    }  

    //============================String=============================  


    /** 
     * ��ͨ������� 
     * @param key �� 
     * @param value ֵ 
     * @return true�ɹ� falseʧ�� 
     */  
    public boolean set(String key,Object value) {  
         try {  
            redisTemplate.opsForValue().set(key, value);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  

    }  

    /** 
     * ��ͨ������벢����ʱ�� 
     * @param key �� 
     * @param value ֵ 
     * @param time ʱ��(��) timeҪ����0 ���timeС�ڵ���0 ������������ 
     * @return true�ɹ� false ʧ�� 
     */  
    public boolean set(String key,Object value,long time){  
        try {  
            if(time>0){  
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);  
            }else{  
                set(key, value);  
            }  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * ���� 
     * @param key �� 
     * @param by Ҫ���Ӽ�(����0) 
     * @return 
     */  
    public long incr(String key, long delta){    
        if(delta<0){  
            throw new RuntimeException("�������ӱ������0");  
        }  
        return redisTemplate.opsForValue().increment(key, delta);  
    }  

    /** 
     * �ݼ� 
     * @param key �� 
     * @param by Ҫ���ټ�(С��0) 
     * @return 
     */  
    public long decr(String key, long delta){    
        if(delta<0){  
            throw new RuntimeException("�ݼ����ӱ������0");  
        }  
        return redisTemplate.opsForValue().increment(key, -delta);    
    }    

    //================================Map=================================  
    /** 
     * HashGet 
     * @param key �� ����Ϊnull 
     * @param item �� ����Ϊnull 
     * @return ֵ 
     */  
    public Object hget(String key,String item){  
        return redisTemplate.opsForHash().get(key, item);  
    }  

    /** 
     * ��ȡhashKey��Ӧ�����м�ֵ 
     * @param key �� 
     * @return ��Ӧ�Ķ����ֵ 
     */  
    public Map<Object,Object> hmget(String key){  
        return redisTemplate.opsForHash().entries(key);  
    }  

    /** 
     * HashSet 
     * @param key �� 
     * @param map ��Ӧ�����ֵ 
     * @return true �ɹ� false ʧ�� 
     */  
    public boolean hmset(String key, Map<String,Object> map){    
        try {  
            redisTemplate.opsForHash().putAll(key, map);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * HashSet ������ʱ�� 
     * @param key �� 
     * @param map ��Ӧ�����ֵ 
     * @param time ʱ��(��) 
     * @return true�ɹ� falseʧ�� 
     */  
    public boolean hmset(String key, Map<String,Object> map, long time){    
        try {  
            redisTemplate.opsForHash().putAll(key, map);  
            if(time>0){  
                expire(key, time);  
            }  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * ��һ��hash���з�������,��������ڽ����� 
     * @param key �� 
     * @param item �� 
     * @param value ֵ 
     * @return true �ɹ� falseʧ�� 
     */  
    public boolean hset(String key,String item,Object value) {  
         try {  
            redisTemplate.opsForHash().put(key, item, value);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * ��һ��hash���з�������,��������ڽ����� 
     * @param key �� 
     * @param item �� 
     * @param value ֵ 
     * @param time ʱ��(��)  ע��:����Ѵ��ڵ�hash����ʱ��,���ｫ���滻ԭ�е�ʱ�� 
     * @return true �ɹ� falseʧ�� 
     */  
    public boolean hset(String key,String item,Object value,long time) {  
         try {  
            redisTemplate.opsForHash().put(key, item, value);  
            if(time>0){  
                expire(key, time);  
            }  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * ɾ��hash���е�ֵ 
     * @param key �� ����Ϊnull 
     * @param item �� ����ʹ��� ����Ϊnull 
     */  
    public void hdel(String key, Object... item){    
        redisTemplate.opsForHash().delete(key,item);  
    }   

    /** 
     * �ж�hash�����Ƿ��и����ֵ 
     * @param key �� ����Ϊnull 
     * @param item �� ����Ϊnull 
     * @return true ���� false������ 
     */  
    public boolean hHasKey(String key, String item){  
        return redisTemplate.opsForHash().hasKey(key, item);  
    }   

    /** 
     * hash���� ���������,�ͻᴴ��һ�� �����������ֵ���� 
     * @param key �� 
     * @param item �� 
     * @param by Ҫ���Ӽ�(����0) 
     * @return 
     */  
    public double hincr(String key, String item,double by){    
        return redisTemplate.opsForHash().increment(key, item, by);  
    }  

    /** 
     * hash�ݼ� 
     * @param key �� 
     * @param item �� 
     * @param by Ҫ���ټ�(С��0) 
     * @return 
     */  
    public double hdecr(String key, String item,double by){    
        return redisTemplate.opsForHash().increment(key, item,-by);    
    }    

    //============================set=============================  
    /** 
     * ����key��ȡSet�е�����ֵ 
     * @param key �� 
     * @return 
     */  
    public Set<Object> sGet(String key){  
        try {  
            return redisTemplate.opsForSet().members(key);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  

    /** 
     * ����value��һ��set�в�ѯ,�Ƿ���� 
     * @param key �� 
     * @param value ֵ 
     * @return true ���� false������ 
     */  
    public boolean sHasKey(String key,Object value){  
        try {  
            return redisTemplate.opsForSet().isMember(key, value);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * �����ݷ���set���� 
     * @param key �� 
     * @param values ֵ �����Ƕ�� 
     * @return �ɹ����� 
     */  
    public long sSet(String key, Object...values) {  
        try {  
            return redisTemplate.opsForSet().add(key, values);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  

    /** 
     * ��set���ݷ��뻺�� 
     * @param key �� 
     * @param time ʱ��(��) 
     * @param values ֵ �����Ƕ�� 
     * @return �ɹ����� 
     */  
    public long sSetAndTime(String key,long time,Object...values) {  
        try {  
            Long count = redisTemplate.opsForSet().add(key, values);  
            if(time>0) expire(key, time);  
            return count;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  

    /** 
     * ��ȡset����ĳ��� 
     * @param key �� 
     * @return 
     */  
    public long sGetSetSize(String key){  
        try {  
            return redisTemplate.opsForSet().size(key);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  

    /** 
     * �Ƴ�ֵΪvalue�� 
     * @param key �� 
     * @param values ֵ �����Ƕ�� 
     * @return �Ƴ��ĸ��� 
     */  
    public long setRemove(String key, Object ...values) {  
        try {  
            Long count = redisTemplate.opsForSet().remove(key, values);  
            return count;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  
    //===============================list=================================  

    /** 
     * ��ȡlist��������� 
     * @param key �� 
     * @param start ��ʼ 
     * @param end ����  0 �� -1��������ֵ 
     * @return 
     */  
    public List<Object> lGet(String key,long start, long end){  
        try {  
            return redisTemplate.opsForList().range(key, start, end);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  

    /** 
     * ��ȡlist����ĳ��� 
     * @param key �� 
     * @return 
     */  
    public long lGetListSize(String key){  
        try {  
            return redisTemplate.opsForList().size(key);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  

    /** 
     * ͨ������ ��ȡlist�е�ֵ 
     * @param key �� 
     * @param index ����  index>=0ʱ�� 0 ��ͷ��1 �ڶ���Ԫ�أ��������ƣ�index<0ʱ��-1����β��-2�����ڶ���Ԫ�أ��������� 
     * @return 
     */  
    public Object lGetIndex(String key,long index){  
        try {  
            return redisTemplate.opsForList().index(key, index);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  

    /** 
     * ��list���뻺�� 
     * @param key �� 
     * @param value ֵ 
     * @param time ʱ��(��) 
     * @return 
     */  
    public boolean lSet(String key, Object value) {  
        try {  
            redisTemplate.opsForList().rightPush(key, value);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * ��list���뻺�� 
     * @param key �� 
     * @param value ֵ 
     * @param time ʱ��(��) 
     * @return 
     */  
    public boolean lSet(String key, Object value, long time) {  
        try {  
            redisTemplate.opsForList().rightPush(key, value);  
            if (time > 0) expire(key, time);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * ��list���뻺�� 
     * @param key �� 
     * @param value ֵ 
     * @param time ʱ��(��) 
     * @return 
     */  
    public boolean lSet(String key, List<Object> value) {  
        try {  
            redisTemplate.opsForList().rightPushAll(key, value);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * ��list���뻺�� 
     * @param key �� 
     * @param value ֵ 
     * @param time ʱ��(��) 
     * @return 
     */  
    public boolean lSet(String key, List<Object> value, long time) {  
        try {  
            redisTemplate.opsForList().rightPushAll(key, value);  
            if (time > 0) expire(key, time);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * ���������޸�list�е�ĳ������ 
     * @param key �� 
     * @param index ���� 
     * @param value ֵ 
     * @return 
     */  
    public boolean lUpdateIndex(String key, long index,Object value) {  
        try {  
            redisTemplate.opsForList().set(key, index, value);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }   

    /** 
     * �Ƴ�N��ֵΪvalue  
     * @param key �� 
     * @param count �Ƴ����ٸ� 
     * @param value ֵ 
     * @return �Ƴ��ĸ��� 
     */  
    public long lRemove(String key,long count,Object value) {  
        try {  
            Long remove = redisTemplate.opsForList().remove(key, count, value);  
            return remove;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  

    /** 
     * ���� String ���� key-value 
     * @param key 
     * @param value 
     */  
    public void set(String key,String value){  
        redisTemplate.opsForValue().set(key, value);  
    }  


    /** 
     * ��ȡ String ���� key-value 
     * @param key 
     * @return 
     */  
    public String get(String key){  
        return (String) redisTemplate.opsForValue().get(key);  
    }  

    /** 
     * ���� String ���� key-value ����ӹ���ʱ�� (���뵥λ) 
     * @param key 
     * @param value 
     * @param time ����ʱ��,���뵥λ 
     */  
    public void setForTimeMS(String key,String value,long time){  
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);  
    }  

    /** 
     * ���� String ���� key-value ����ӹ���ʱ�� (���ӵ�λ) 
     * @param key 
     * @param value 
     * @param time ����ʱ��,���ӵ�λ 
     */  
    public void setForTimeMIN(String key,String value,long time){  
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);  
    }  


    /** 
     * ���� String ���� key-value ����ӹ���ʱ�� (���ӵ�λ) 
     * @param key 
     * @param value 
     * @param time ����ʱ��,���ӵ�λ 
     */  
    public void setForTimeCustom(String key,String value,long time,TimeUnit type){  
        redisTemplate.opsForValue().set(key, value, time, type);  
    }  

    /** 
     * ��� key �����򸲸�,�����ؾ�ֵ. 
     * ���������,����null ����� 
     * @param key 
     * @param value 
     * @return 
     */  
    public String getAndSet(String key,String value){  
        return (String) redisTemplate.opsForValue().getAndSet(key, value);  
    }  


    /** 
     * ������� key-value (�ظ��ļ��Ḳ��) 
     * @param keyAndValue 
     */  
    public void batchSet(Map<String,String> keyAndValue){  
        redisTemplate.opsForValue().multiSet(keyAndValue);  
    }  

    /** 
     * ������� key-value ֻ���ڼ�������ʱ,����� 
     * map ��ֻҪ��һ��key����,��ȫ������� 
     * @param keyAndValue 
     */  
    public void batchSetIfAbsent(Map<String,String> keyAndValue){  
        redisTemplate.opsForValue().multiSetIfAbsent(keyAndValue);  
    }  

    /** 
     * ��һ�� key-value ��ֵ���мӼ�����, 
     * ����� key ������ ������һ��key ����ֵ�� number 
     * ��� key ����,�� value ���ǳ����� ,������ 
     * @param key 
     * @param number 
     */  
    public Long increment(String key,long number){  
        return redisTemplate.opsForValue().increment(key, number);  
    }  

    /** 
     * ��һ�� key-value ��ֵ���мӼ�����, 
     * ����� key ������ ������һ��key ����ֵ�� number 
     * ��� key ����,�� value ���� ������ ,������ 
     * @param key 
     * @param number 
     */  
    public Double increment(String key,double number){  
        return redisTemplate.opsForValue().increment(key, number);  
    }  


    /** 
     * ��һ��ָ���� key ֵ���ӹ���ʱ�� 
     * @param key 
     * @param time 
     * @param type 
     * @return 
     */  
    public boolean expire(String key,long time,TimeUnit type){  
        return redisTemplate.boundValueOps(key).expire(time, type);  
    }  

    /** 
     * �Ƴ�ָ��key �Ĺ���ʱ�� 
     * @param key 
     * @return 
     */  
    public boolean persist(String key){  
        return redisTemplate.boundValueOps(key).persist();  
    }  


    /** 
     * �޸� key 
     * @param key 
     * @return 
     */  
    public void rename(String key,String newKey){  
        redisTemplate.boundValueOps(key).rename(newKey);  
    }  



    //hash����  

    /** 
     * ��� Hash ��ֵ�� 
     * @param key 
     * @param hashKey 
     * @param value 
     */  
    public void put(String key, String hashKey, String value){  
        redisTemplate.opsForHash().put(key, hashKey, value);  
    }  

    /** 
     * ������� hash �� ��ֵ�� 
     * ���򸲸�,û������� 
     * @param key 
     * @param map 
     */  
    public void putAll(String key,Map<String,String> map){  
        redisTemplate.opsForHash().putAll(key, map);  
    }  

    /** 
     * ��� hash ��ֵ��. �����ڵ�ʱ������ 
     * @param key 
     * @param hashKey 
     * @param value 
     * @return 
     */  
    public boolean putIfAbsent(String key, String hashKey, String value){  
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);  
    }  


    /** 
     * ɾ��ָ�� hash �� HashKey 
     * @param key 
     * @param hashKeys 
     * @return ɾ���ɹ��� ���� 
     */  
	/*
	 * public Long delete(String key, String ...hashKeys){ return
	 * redisTemplate.opsForHash().delete(key, hashKeys); }
	 */


    /** 
     * ��ָ�� hash �� hashkey ���������� 
     * @param key 
     * @param hashKey 
     * @param number 
     * @return 
     */  
    public Long increment(String key, String hashKey,long number){  
        return redisTemplate.opsForHash().increment(key, hashKey, number);  
    }  

    /** 
     * ��ָ�� hash �� hashkey ���������� 
     * @param key 
     * @param hashKey 
     * @param number 
     * @return 
     */  
    public Double increment(String key, String hashKey,Double number){  
        return redisTemplate.opsForHash().increment(key, hashKey, number);  
    }  

    /** 
     * ��ȡָ�� key �µ� hashkey 
     * @param key 
     * @param hashKey 
     * @return 
     */  
    public Object getHashKey(String key,String hashKey){  
        return redisTemplate.opsForHash().get(key, hashKey);  
    }  


    /** 
     * ��ȡ key �µ� ����  hashkey �� value 
     * @param key 
     * @return 
     */  
    public Map<Object,Object> getHashEntries(String key){  
        return redisTemplate.opsForHash().entries(key);  
    }  

    /** 
     * ��ָ֤�� key �� ��û��ָ���� hashkey 
     * @param key 
     * @param hashKey 
     * @return 
     */  
    public boolean hashKey(String key,String hashKey){  
        return redisTemplate.opsForHash().hasKey(key, hashKey);  
    }  

    /** 
     * ��ȡ key �µ� ���� hashkey �ֶ��� 
     * @param key 
     * @return 
     */  
    public Set<Object> hashKeys(String key){  
        return redisTemplate.opsForHash().keys(key);  
    }  


    /** 
     * ��ȡָ�� hash ����� ��ֵ�� ���� 
     * @param key 
     * @return 
     */  
    public Long hashSize(String key){  
        return redisTemplate.opsForHash().size(key);  
    }  

    //List ����  

    /** 
     * ָ�� list ������ջ 
     * @param key 
     * @return ��ǰ���еĳ��� 
     */  
    public Long leftPush(String key,Object value){  
        return redisTemplate.opsForList().leftPush(key, value);  
    }  

    /** 
     * ָ�� list �����ջ 
     * ����б�û��Ԫ��,��������б�һֱ��Ԫ�ػ��߳�ʱΪֹ 
     * @param key 
     * @return ��ջ��ֵ 
     */  
    public Object leftPop(String key){  
        return redisTemplate.opsForList().leftPop(key);  
    }  

    /** 
     * �����������ջ 
     * ����˳���� Collection ˳�� 
     * ��: a b c => c b a 
     * @param key 
     * @param values 
     * @return 
     */  
    public Long leftPushAll(String key,Collection<Object> values){  
        return redisTemplate.opsForList().leftPushAll(key, values);  
    }  

    /** 
     * ָ�� list ������ջ 
     * @param key 
     * @return ��ǰ���еĳ��� 
     */  
    public Long rightPush(String key,Object value){  
        return redisTemplate.opsForList().rightPush(key, value);  
    }  

    /** 
     * ָ�� list ���ҳ�ջ 
     * ����б�û��Ԫ��,��������б�һֱ��Ԫ�ػ��߳�ʱΪֹ 
     * @param key 
     * @return ��ջ��ֵ 
     */  
    public Object rightPop(String key){  
        return redisTemplate.opsForList().rightPop(key);  
    }  

    /** 
     * ���ұ�������ջ 
     * ����˳���� Collection ˳�� 
     * ��: a b c => a b c 
     * @param key 
     * @param values 
     * @return 
     */  
    public Long rightPushAll(String key,Collection<Object> values){  
        return redisTemplate.opsForList().rightPushAll(key, values);  
    }  


    /** 
     * �����±��ȡֵ 
     * @param key 
     * @param index 
     * @return 
     */  
    public Object popIndex(String key,long index){  
        return redisTemplate.opsForList().index(key, index);  
    }  


    /** 
     * ��ȡ�б�ָ������ 
     * @param key 
     * @param index 
     * @return 
     */  
    public Long listSize(String key,long index){  
        return redisTemplate.opsForList().size(key);  
    }  


    /** 
     * ��ȡ�б� ָ����Χ�ڵ�����ֵ 
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    public List<Object> listRange(String key,long start,long end){  
        return redisTemplate.opsForList().range(key, start, end);  
    }  


    /** 
     * ɾ�� key �� ֵΪ value �� count ����. 
     * @param key 
     * @param count 
     * @param value 
     * @return �ɹ�ɾ���ĸ��� 
     */  
    public Long listRemove(String key,long count,Object value){  
        return redisTemplate.opsForList().remove(key, count, value);  
    }  


    /** 
     * ɾ�� �б� [start,end] ���������Ԫ�� 
     * @param key 
     * @param start 
     * @param end 
     */  
    public void listTrim(String key,long start,long end){  
        redisTemplate.opsForList().trim(key, start, end);  

    }  

    /** 
     * �� key �ҳ�ջ,������ջ�� key2 
     * 
     * @param key �ҳ�ջ���б� 
     * @param key2 ����ջ���б� 
     * @return ������ֵ 
     */  
    public Object rightPopAndLeftPush(String key,String key2){  
        return redisTemplate.opsForList().rightPopAndLeftPush(key, key2);  

    }  

    //set ����  �����ظ�����  

    /** 
     * ��� set Ԫ�� 
     * @param key 
     * @param values 
     * @return 
     */  
    public Long add(String key ,String ...values){  
        return redisTemplate.opsForSet().add(key, values);  
    }  

    /** 
     * ��ȡ�������ϵĲ 
     * @param key 
     * @param key2 
     * @return 
     */  
    public Set<Object> difference(String key ,String otherkey){  
        return redisTemplate.opsForSet().difference(key, otherkey);  
    }  



    /** 
     * ��  key �� otherkey �Ĳ ,��ӵ��µ� newKey ������ 
     * @param key 
     * @param otherkey 
     * @param newKey 
     * @return ���ز������ 
     */  
    public Long differenceAndStore(String key ,String otherkey,String newKey){  
        return redisTemplate.opsForSet().differenceAndStore(key, otherkey, newKey);  
    }  


    /** 
     * ɾ��һ�����������е�ָ��ֵ 
     * @param key 
     * @param values 
     * @return �ɹ�ɾ������ 
     */  
    public Long remove(String key,Object ...values){  
        return redisTemplate.opsForSet().remove(key, values);  
    }  

    /** 
     * ����Ƴ�һ��Ԫ��,�����س��� 
     * @param key 
     * @return 
     */  
    public Object randomSetPop(String key){  
        return redisTemplate.opsForSet().pop(key);  
    }  

    /** 
     * �����ȡһ��Ԫ�� 
     * @param key 
     * @return 
     */  
    public Object randomSet(String key){  
        return redisTemplate.opsForSet().randomMember(key);  
    }  

    /** 
     * �����ȡָ��������Ԫ��,ͬһ��Ԫ�ؿ��ܻ�ѡ������ 
     * @param key 
     * @param count 
     * @return 
     */  
    public List<Object> randomSet(String key,long count){  
        return redisTemplate.opsForSet().randomMembers(key, count);  
    }  

    /** 
     * �����ȡָ��������Ԫ��,ȥ��(ͬһ��Ԫ��ֻ��ѡ����һ��) 
     * @param key 
     * @param count 
     * @return 
     */  
    public Set<Object> randomSetDistinct(String key,long count){  
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);  
    }  

    /** 
     * �� key �е� value ת�뵽 destKey �� 
     * @param key 
     * @param value 
     * @param destKey 
     * @return ���سɹ���� 
     */  
    public boolean moveSet(String key,Object value,String destKey){  
        return redisTemplate.opsForSet().move(key, value, destKey);  
    }  

    /** 
     * ���򼯺ϵĴ�С 
     * @param key 
     * @return 
     */  
    public Long setSize(String key){  
        return redisTemplate.opsForSet().size(key);  
    }  

    /** 
     * �ж� set ������ �Ƿ��� value 
     * @param key 
     * @param value 
     * @return 
     */  
    public boolean isMember(String key,Object value){  
        return redisTemplate.opsForSet().isMember(key, value);  
    }  

    /** 
     * ���� key �� othere �Ĳ��� 
     * @param key 
     * @param otherKey 
     * @return 
     */  
    public Set<Object> unionSet(String key,String otherKey){  
        return redisTemplate.opsForSet().union(key, otherKey);  
    }  



    /** 
     * �� key �� otherKey �Ĳ���,���浽 destKey �� 
     * @param key 
     * @param otherKey 
     * @param destKey 
     * @return destKey ���� 
     */  
    public Long unionAndStoreSet(String key, String otherKey,String destKey){  
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);  
    }  



    /** 
     * ���ؼ���������Ԫ�� 
     * @param key 
     * @return 
     */  
    public Set<Object> members(String key){  
        return redisTemplate.opsForSet().members(key);  
    }  

    //Zset ���� socre ����   ���ظ� ÿ��Ԫ�ظ���һ�� socre  double���͵�����(double �����ظ�)  

    /** 
     * ��� ZSet Ԫ�� 
     * @param key 
     * @param value 
     * @param score 
     */  
    public boolean add(String key,Object value,double score){  
        return redisTemplate.opsForZSet().add(key, value, score);  
    }  

    /** 
     * ������� Zset <br> 
     *         Set<TypedTuple<Object>> tuples = new HashSet<>();<br> 
     *         TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<Object>("zset-5",9.6);<br> 
     *         tuples.add(objectTypedTuple1); 
     * @param key 
     * @param tuples 
     * @return 
     */  
    public Long batchAddZset(String key,Set<TypedTuple<Object>> tuples){  
        return redisTemplate.opsForZSet().add(key, tuples);  
    }  

    /** 
     * Zset ɾ��һ������Ԫ�� 
     * @param key 
     * @param values 
     * @return 
     */  
    public Long removeZset(String key,String ...values){  
        return redisTemplate.opsForZSet().remove(key, values);  
    }  

    /** 
     * ��ָ���� zset �� value ֵ , socre �������������� 
     * @param key 
     * @param value 
     * @param score 
     * @return 
     */  
    public Double incrementScore(String key,Object value,double score){  
        return redisTemplate.opsForZSet().incrementScore(key, value, score);  
    }  

    /** 
     * ��ȡ key ��ָ�� value ������(��0��ʼ,��С��������) 
     * @param key 
     * @param value 
     * @return 
     */  
    public Long rank(String key,Object value){  
        return redisTemplate.opsForZSet().rank(key, value);  
    }  

    /** 
     * ��ȡ key ��ָ�� value ������(��0��ʼ,�Ӵ�С����) 
     * @param key 
     * @param value 
     * @return 
     */  
    public Long reverseRank(String key,Object value){  
        return redisTemplate.opsForZSet().reverseRank(key, value);  
    }  

    /** 
     * ��ȡ���������ڵ�����������(��0��ʼ,��С����,���Ϸ���) 
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    public Set<TypedTuple<Object>> rangeWithScores(String key, long start, long end){  
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);  
    }  

    /** 
     * ��ȡ���������ڵ�����������(��0��ʼ,��С����,ֻ������) 
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    public Set<Object> range(String key, long start, long end){  
        return redisTemplate.opsForZSet().range(key, start, end);  
    }  

    /** 
     * ��ȡ������Χ�ڵ� [min,max] ������������ (��С����,ֻ������) 
     * @param key 
     * @param min 
     * @param max 
     * @return 
     */  
    public Set<Object> rangeByScore(String key, double min, double max){  
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);  
    }  

    /** 
     * ��ȡ������Χ�ڵ� [min,max] ������������ (��С����,���ϴ�����) 
     * @param key 
     * @param min 
     * @param max 
     * @return 
     */  
    public Set<TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max){  
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);  
    }  

    /** 
     * ���� ������Χ�� ָ�� count ������Ԫ�ؼ���, ���Ҵ� offset �±꿪ʼ(��С����,���������ļ���) 
     * @param key 
     * @param min 
     * @param max 
     * @param offset ��ָ���±꿪ʼ 
     * @param count ���ָ��Ԫ������ 
     * @return 
     */  
    public Set<Object> rangeByScore(String key, double min, double max,long offset,long count){  
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);  
    }  

    /** 
     * ���� ������Χ�� ָ�� count ������Ԫ�ؼ���, ���Ҵ� offset �±꿪ʼ(��С����,�������ļ���) 
     * @param key 
     * @param min 
     * @param max 
     * @param offset ��ָ���±꿪ʼ 
     * @param count ���ָ��Ԫ������ 
     * @return 
     */  
    public Set<TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max,long offset,long count){  
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);  
    }  

    /** 
     * ��ȡ���������ڵ�����������(��0��ʼ,�Ӵ�С,ֻ������) 
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    public Set<Object> reverseRange(String key,long start,long end){  
        return redisTemplate.opsForZSet().reverseRange(key, start, end);  
    }  

    /** 
     * ��ȡ���������ڵ�����������(��0��ʼ,�Ӵ�С,���Ϸ���) 
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    public Set<TypedTuple<Object>> reverseRangeWithScores(String key,long start,long end){  
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);  
    }  

    /** 
     * ��ȡ������Χ�ڵ� [min,max] ������������ (�Ӵ�С,���ϲ�������) 
     * @param key 
     * @param min 
     * @param max 
     * @return 
     */  
    public Set<Object> reverseRangeByScore(String key,double min,double max){  
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);  
    }  

    /** 
     * ��ȡ������Χ�ڵ� [min,max] ������������ (�Ӵ�С,���ϴ�����) 
     * @param key 
     * @param min 
     * @param max 
     * @return 
     */  
    public Set<TypedTuple<Object>> reverseRangeByScoreWithScores(String key,double min,double max){  
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);  
    }  

    /** 
     * ���� ������Χ�� ָ�� count ������Ԫ�ؼ���, ���Ҵ� offset �±꿪ʼ(�Ӵ�С,���������ļ���) 
     * @param key 
     * @param min 
     * @param max 
     * @param offset ��ָ���±꿪ʼ 
     * @param count ���ָ��Ԫ������ 
     * @return 
     */  
    public Set<Object> reverseRangeByScore(String key,double min,double max,long offset,long count){  
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);  
    }  

    /** 
     * ���� ������Χ�� ָ�� count ������Ԫ�ؼ���, ���Ҵ� offset �±꿪ʼ(�Ӵ�С,�������ļ���) 
     * @param key 
     * @param min 
     * @param max 
     * @param offset ��ָ���±꿪ʼ 
     * @param count ���ָ��Ԫ������ 
     * @return 
     */  
    public Set<TypedTuple<Object>> reverseRangeByScoreWithScores(String key,double min,double max,long offset,long count){  
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);  
    }  

    /** 
     * ����ָ���������� [min,max] ��Ԫ�ظ��� 
     * @param key 
     * @param min 
     * @param max 
     * @return 
     */  
    public long countZSet(String key,double min,double max){  
        return redisTemplate.opsForZSet().count(key, min, max);  
    }  

    /** 
     * ���� zset �������� 
     * @param key 
     * @return 
     */  
    public long sizeZset(String key){  
        return redisTemplate.opsForZSet().size(key);  
    }  

    /** 
     * ��ȡָ����Ա�� score ֵ 
     * @param key 
     * @param value 
     * @return 
     */  
    public Double score(String key,Object value){  
        return redisTemplate.opsForZSet().score(key, value);  
    }  

    /** 
     * ɾ��ָ������λ�õĳ�Ա,���г�Ա������( ��С���� ) 
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    public Long removeRange(String key,long start ,long end){  
        return redisTemplate.opsForZSet().removeRange(key, start, end);  
    }  

    /** 
     * ɾ��ָ�� ������Χ �ڵĳ�Ա [main,max],���г�Ա������( ��С���� ) 
     * @param key 
     * @param min 
     * @param max 
     * @return 
     */  
    public Long removeRangeByScore(String key,double min ,double max){  
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);  
    }  

    /** 
     *  key �� other �������ϵĲ���,������ destKey ������, ������ͬ�� score ��� 
     * @param key 
     * @param otherKey 
     * @param destKey 
     * @return 
     */  
    public Long unionAndStoreZset(String key,String otherKey,String destKey){  
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);  
    }  

    /** 
     *  key �� otherKeys ������ϵĲ���,������ destKey ������, ������ͬ�� score ��� 
     * @param key 
     * @param otherKeys 
     * @param destKey 
     * @return 
     */  
    public Long unionAndStoreZset(String key,Collection<String> otherKeys,String destKey){  
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);  
    }  

    /** 
     *  key �� otherKey �������ϵĽ���,������ destKey ������ 
     * @param key 
     * @param otherKey 
     * @param destKey 
     * @return 
     */  
    public Long intersectAndStore(String key,String otherKey,String destKey){  
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);  
    }  

    /** 
     *  key �� otherKeys ������ϵĽ���,������ destKey ������ 
     * @param key 
     * @param otherKeys 
     * @param destKey 
     * @return 
     */  
    public Long intersectAndStore(String key,Collection<String> otherKeys,String destKey){  
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);  
    }  

}  
