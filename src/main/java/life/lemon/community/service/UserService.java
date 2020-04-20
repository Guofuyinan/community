package life.lemon.community.service;

import life.lemon.community.mapper.UserMapper;
import life.lemon.community.model.User;
import life.lemon.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();//相当于查询条件语句where
        userExample.createCriteria()
                .andOpenIdEqualTo(user.getOpenId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            // 没有用户数据 执行插入操作
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //有用户数据 执行更新操作
            User dbUser = users.get(0);//获取上文20行User集合的0下标的信息
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getFigureurlQq1());
            updateUser.setNickname(user.getNickname());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();//相当于查询条件语句where
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);//前面参数为要修改的对象，后面的参数为修改的条件
        }
    }

}
