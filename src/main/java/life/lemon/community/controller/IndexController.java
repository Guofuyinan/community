package life.lemon.community.controller;

import life.lemon.community.cache.HotTagCache;
import life.lemon.community.dto.PaginationDTO;
import life.lemon.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public String index(Model model,//主页分页展示问题列表
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "8") Integer size,
                        @RequestParam(name = "search", required = false) String search,//搜索
                        @RequestParam(name = "tag", required = false) String tag,//标签
                        @RequestParam(name = "sort", required = false) String sort) {//分类
        PaginationDTO pagination = questionService.list(search, tag, sort, page, size);
        List<String> tags = hotTagCache.getHots();
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", tags);
        model.addAttribute("sort", sort);
        return "index";
    }
}
