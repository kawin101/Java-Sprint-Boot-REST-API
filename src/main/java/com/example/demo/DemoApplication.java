package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//HOW TO TEST THIS REST API WITH POSTMAN

/*
1.ADD NEW ITEM
METHOD: POST
URL: localhost:8080/items
BODY: {
    "id": 1,
    "name": "Green 1"
}

2.GET ALL
METHOD: GET
URL: localhost:8080/items
BODY: {}
RESPOND: JSON
[
    {
        "id": 1,
        "name": "green 1"
    },
    {
        "id": 2,
        "name": "green 2"
    },
    {
        "id": 3,
        "name": "Mary 3"
    }
]

3.GET BY ID
METHOD: GET
URL: localhost:8080/items/2
BODY: {
    "id": 2,
    "name": "green 2"
}

4.UPDATE BY ID
METHOD: PUT
URL: localhost:8080/items/3
BODY: {
    "id": 3,
    "name": "ZZZ 3"
}
AND THEN CALL GET ALL TO CHECK ITEM IS UPDATED!!!

5.DELETE BY ID
METHOD: DELETE
URL: localhost:8080/items/3
BODY: {
    "id": 3,
    "name": "ZZZ 3"
}
RESPOND: JSON
{
    "id": 3,
    "name": "ZZZ 3"
}
AND THEN CALL GET ALL TO CHECK ITEM IS DELETED!!!

6.ERROR
RESPOND: JSON
METHOD: PUT
BODY: {}
URL: localhost:8080/items/
{
    "timestamp": "2025-11-15T16:59:39.574+00:00",
    "status": 404,
    "error": "Not Found",
    "path": "/items/"
}
 */

@SpringBootApplication
@RestController
public class DemoApplication {
    // DO NOT MAKE THIS TO FINAL
    // FINAL = CAN NOT TO ADD NEW ITEM INTO THE LIST
    private List<Item> itemList = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemList;
    }

    @PostMapping("items")
    public Item addItem(@RequestBody Item item) {
        itemList.add(item);
        return item;
    }

    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemList.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        for (Item item : itemList) {
            if (item.getId().equals(id)) {
                item.setName(updatedItem.getName());
                return item;
            }
        }
        return null;
    }

    @DeleteMapping("/items/{id}")
    public Item deleteItem(@PathVariable Long id) {
        Item itemToDelete = null;
        for (Item item : itemList) {
            if (item.getId().equals(id)) {
                itemToDelete = item;
                break;
            }
        }

        if (itemToDelete != null) {
            itemList.remove(itemToDelete);
        }
        return itemToDelete;
    }
}



