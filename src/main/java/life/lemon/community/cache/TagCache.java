package life.lemon.community.cache;

import life.lemon.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("热点话题");
        program.setTags(Arrays.asList("开学时间", "疫情结束", "远程网课", "毕业就业", "开题报告", "网站改进", "其他"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("疯狂学习");
        framework.setTags(Arrays.asList("学霸求带", "实验报告", "空自习室", "图书馆", "资料分享"));
        tagDTOS.add(framework);


        TagDTO server = new TagDTO();
        server.setCategoryName("二手交易");
        server.setTags(Arrays.asList("二手书籍", "电子产品", "二手潮品", "房屋租赁", "地下交易"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("情感交流");
        db.setTags(Arrays.asList("表白", "约会", "跑步", "生日祝福"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("日常吐槽");
        tool.setTags(Arrays.asList("小南门", "体测", "食堂", "上课", "花式吐槽"));
        tagDTOS.add(tool);
        return tagDTOS;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> StringUtils.isBlank(t) || !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }

    public static void main(String[] args) {
        int i = (5 - 1) >>> 1;
        System.out.println(i);
    }
}
