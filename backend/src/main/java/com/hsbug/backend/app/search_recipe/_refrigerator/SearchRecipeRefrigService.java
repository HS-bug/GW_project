package com.hsbug.backend.app.search_recipe._refrigerator;

import com.hsbug.backend.admin_page.manage_recipe.ManageRecipeDto;
import com.hsbug.backend.admin_page.manage_recipe.ManageRecipeEntity;
import com.hsbug.backend.admin_page.manage_recipe.ManageRecipeRepository;
import com.hsbug.backend.app.recipe.my_recipe.MyRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SearchRecipeRefrigService {
    private final ManageRecipeRepository manageRecipeRepository;
    private final MyRecipeService myRecipeService;

    public Map<Long, Integer> findIdFromPart(ArrayList<String> product_list){
        return selectCounting(product_list);
    }

    public Map<Long, Integer> findIdFromAll(ArrayList<String> product_list) {
        HashMap<Long, Integer> idCountMap = selectCounting(product_list);
        HashMap<Long, Integer> choice_only_map = new HashMap<>();

        //가장 많이 포함하고 있는 순서대로 choice_only_map에 넣기 위함
        for (Map.Entry<Long,Integer> entry : idCountMap.entrySet()){
            for (int i = 0; i < product_list.size(); i++) {
                if (entry.getValue() == product_list.size()-i){
                    choice_only_map.put(entry.getKey(), product_list.size()-i);
                }
            }
        }
        //sort 할 필요 없는데 map -> json obj 때문에
        return mapValueSort(choice_only_map);
    }

    private HashMap<Long, Integer> selectCounting(ArrayList<String> product_list) {
        HashMap<Long, Integer> map = new HashMap<>();
        for ( Object product : product_list) {
            String productName = product.toString();

            List<ManageRecipeEntity> manageRecipeEntityList = manageRecipeRepository.findByRCPPARTSDTLSContains(productName);
            List<ManageRecipeDto> manageRecipeDtoList = new ArrayList<>();

            for (ManageRecipeEntity manageRecipeEntity : manageRecipeEntityList) {
                manageRecipeDtoList.add(this.myRecipeService.convertEntityToDto(manageRecipeEntity));
            }

            for (ManageRecipeDto manageRecipeDto : manageRecipeDtoList) {
                if (!map.containsKey(manageRecipeDto.getRCP_ID())) {
                    map.put(manageRecipeDto.getRCP_ID(), 1);
                } else {
                    map.put(manageRecipeDto.getRCP_ID(),
                            map.get(manageRecipeDto.getRCP_ID()) + 1);
                }
            }

        }
        return map;
    }

    // 관련 = 오름차순, 조회수 get 오름차순,
    public Map<Long, Integer> mapValueSort(Map<Long, Integer> map) {
        List<Map.Entry<Long, Integer>> entryList = new ArrayList<>(map.entrySet());
        Map<Long, Integer> sorted_map = new LinkedHashMap<>();
        Collections.sort(entryList, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        for(int i = 0; i<entryList.size(); i++){
            sorted_map.put(entryList.get(i).getKey(), entryList.get(i).getValue());
        }

        return sorted_map;
    }
}