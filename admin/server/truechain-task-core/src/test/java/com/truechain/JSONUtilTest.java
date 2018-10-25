package com.truechain;

import com.truechain.task.util.JsonUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JSONUtilTest {
    @Test
    public void  testInit(){
        Map map = new HashMap();
        map.put("A","高级");
        map.put("B","普通");
        String str = JsonUtil.toJsonString(map);
        System.out.println(str);
        Map map2 = JsonUtil.parseObject(str,Map.class);
        System.out.println(map2.get("0"));
    }
}
