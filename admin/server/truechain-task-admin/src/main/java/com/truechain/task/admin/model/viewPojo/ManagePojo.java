package com.truechain.task.admin.model.viewPojo;

import java.util.List;

public class ManagePojo {
		
    private Long id;
    private String name;
    private String type;
    private List<Option> configdataByList;
//    private Map configdataMap;
    //    private String configdata;    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public List<Option> getConfigdataByList() {
		return configdataByList;
	}

	public void setConfigdataByList(List<Option> configdataByList) {
		this.configdataByList = configdataByList;
	}

    public static class Option{
    	private String key;
    	private String value;
    	
    	public Option(){
    		
    	}
    	
    	public Option(String key,String value){
    		this.key = key;
    		this.value = value;
    	}
    	
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public String toString(){
			return "key:"+key+",value:"+value;
		}
    }

//	
//	public static void main(String[] args) {		
//		Option o1 = new ManagePojo.Option("1","启用");
//		Option o2 = new ManagePojo.Option("0","停用");
//		List<Option> oList = new ArrayList<Option>();
//		oList.add(o1);
//		oList.add(o2);
//		String str = JsonUtil.toJsonString(oList);
//		System.out.println(str);
//		List<ManagePojo.Option> o2List = JsonUtil.parseObject(str, new TypeReference<List<ManagePojo.Option>>() {	});
//		
//		for(int i=0;i<o2List.size();i++){
//			Option o = o2List.get(i);			
//			System.out.println(o);
//		}		
//	}
}
