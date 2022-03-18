package com.clone.finalProject.service;


import com.clone.finalProject.domain.Fword;
import com.clone.finalProject.repository.FwordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final FwordRepository fwordRepository;

    @Cacheable(cacheNames = "fwordStore", key ="#key")
    public HashMap<Integer,String> getCacheData(final String key) {
        System.out.println("캐시에 데이터 없을 경우 출력");
        List<Fword> fwordList = fwordRepository.findAll();
        HashMap<Integer,String> fwords = new HashMap<>();
        for (int i = 0; i<fwordList.size(); i++) {
            fwords.put(i+1,fwordList.get(i).getFWord());
        }

        return fwords;
    }

}
