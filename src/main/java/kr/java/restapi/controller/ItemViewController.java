package kr.java.restapi.controller;

import kr.java.restapi.model.dto.ItemResponse;
import kr.java.restapi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// #(1)-1
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemViewController {
    private final ItemService itemService;

    @GetMapping // http://localhost:8080/items
    // RestAPI -> return (데이터...)
    public String itemList(Model model) {
        List<ItemResponse> items = itemService.findAll();
        model.addAttribute("items", items);
        return "items"; // items -> templates
    }
}
