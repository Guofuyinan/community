package life.lemon.community.service;

import life.lemon.community.mapper.AdMapper;
import life.lemon.community.model.Ad;
import life.lemon.community.model.AdExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {
    @Autowired
    private AdMapper adMapper;

    public List<Ad> list(String pos) {
        AdExample navExample = new AdExample();
        navExample.createCriteria()
                .andStatusEqualTo(1)
                .andPosEqualTo(pos)
                .andGmtStartLessThan(System.currentTimeMillis())
                .andGmtEndGreaterThan(System.currentTimeMillis());
        return adMapper.selectByExample(navExample);
    }
}
