package jpashop.study.controller;

import jpashop.study.domain.item.Item;
import jpashop.study.service.BookDto;
import jpashop.study.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createItem(Model model) {

        model.addAttribute("form", new BookDto());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookDto form, BindingResult result) {

        if (result.hasErrors()) {
            return "items/createItemForm";
        }
        itemService.saveItem(form);

        return "redirect:/";
    }

    @GetMapping("/items")
    public String items(Model model) {
        List<Item> items = itemService.findItems();

        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);

        model.addAttribute("form", item);
        return "/items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String update(@PathVariable("itemId") Long itemId, BookDto form) {
        itemService.updateItemInfo(form);
        return "redirect:/items";
    }
}
