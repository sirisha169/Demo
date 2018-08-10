package com.example.demo;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component 
public class RestClient implements CommandLineRunner{
  
    public static final String REST_SERVICE_URI = "http://homework.ad-juster.com/api/campaigns";
      public static final String REST_SERVICE_URII = "http://homework.ad-juster.com/api/creatives";
      
    
    @SuppressWarnings("unchecked")
    private static void aggregating(){
        
          
        RestTemplate restTemplate = new RestTemplate();
         RestTemplate restTemplate1 = new RestTemplate();
        List<LinkedHashMap<String, Object>> capaignsMap = restTemplate.getForObject(REST_SERVICE_URI, List.class);
          List<String> listIds = new ArrayList<String>();
          int clicks = 0;
          int impressions = 0;
          
          System.out.println("Getting ids from campaigns");
        if(capaignsMap!=null){
            for(LinkedHashMap<String, Object> map : capaignsMap){
                listIds.add(map.get("id").toString());
            }
        }else{
            System.out.println("No campaign exist----------");
        }
        
        System.out.println("listIds are :: " + listIds);
         System.out.println("Aggregating clicks and impressions");
        
        List<LinkedHashMap<String, Object>> creativesMap = restTemplate1.getForObject(REST_SERVICE_URII, List.class);
        
          if(creativesMap!=null){
            for(LinkedHashMap<String, Object> map : creativesMap)
            {
                for (String temp : listIds) {
			        if( temp.equals(map.get("parentId").toString()))
			            {
			             clicks= clicks +((Integer) map.get("clicks")).intValue();
			             impressions = impressions +((Integer) map.get("impressions")).intValue();
			            }
		    }
           
            }
        }else{
            System.out.println("No creative exist----------");
        }
          System.out.println("Total number of clicks ::  " + clicks);
          System.out.println("Total number of impressions ::  " + impressions);
          
    }


	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		aggregating(); 
		
	}
    
}