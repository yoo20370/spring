package jpabook.jpashop.controller;

import jpabook.jpashop.entity.item.Book;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());

        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm bookForm) {

        Book book = new Book();

        // setter를 사용하지 말고 static 생성 메서드(생성자 아님)를 정의해서 사용하는게 좋다. !!!!
        // 영한님은 setter를 다 날린다.
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        model.addAttribute("items", itemService.findItems());
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        // 캐스팅이 좋은 것은 아님, 예제를 단순화하기 위해서 캐스팅 사용
        Book item = (Book) itemService.findOne(itemId);

        // 업데이트 할 때, Book 엔티티를 보내는게 아니라, Book form을 보낼 것
        // 많이 작성하는 경우

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form) {
        // 데이터를 받을 때 {itemId}를 조작할 수 있음 -> 다른 사람의 데이터를 조작할 수 있음
        // 그래서 서비스 계층에 대해서, 접속한 유저가 해당 Item에 대해서 권한이 있는지 체크해줘야 한다.

        // 북 자체는 영속성 컨텍스트에서 관리하고 있지 않음
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);

        return "redirect:/items";
    }
}
