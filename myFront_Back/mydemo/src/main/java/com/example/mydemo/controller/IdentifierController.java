package com.example.mydemo.controller;

import com.example.mydemo.model.Identifier;
import com.example.mydemo.service.IdentifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api/identifiers")
//@CrossOrigin(origins = "http://localhost:8081")
//public class IdentifierController {
//
//    private final IdentifierService identifierService;
//
//    @Autowired
//    public IdentifierController(IdentifierService identifierService) {
//        this.identifierService = identifierService;
//    }
//
//    @GetMapping
//    public List<Identifier> getAllIdentifiers() {
//        return identifierService.getAllIdentifiers();
//    }
//
//    @PostMapping
//    public Identifier createIdentifier(@RequestBody Identifier identifier) {
//        return identifierService.saveIdentifier(identifier);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteIdentifier(@PathVariable Long id) {
//        identifierService.deleteIdentifier(id);
//    }
//
//    @PutMapping("/{id}")
//    public Identifier updateIdentifier(@PathVariable Long id, @RequestBody Identifier identifier) {
//        return identifierService.updateIdentifier(id, identifier);
//    }
//}
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/identifiers")
@CrossOrigin(origins = "http://localhost:8081")
public class IdentifierController {

    private final IdentifierService identifierService;

    @Autowired
    public IdentifierController(IdentifierService identifierService) {
        this.identifierService = identifierService;
    }

    // 保留原来的方法
    @GetMapping
    public List<Identifier> getAllIdentifiers() {
        return identifierService.getAllIdentifiers();
    }

    // 新增分页查询方法
    @GetMapping("/page")
    public Page<Identifier> getIdentifiersPage(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "4") int size) {
        return identifierService.getIdentifiersPage(page, size);
    }

    @PostMapping
    public Identifier createIdentifier(@RequestBody Identifier identifier) {
        return identifierService.saveIdentifier(identifier);
    }

    @DeleteMapping("/{id}")
    public void deleteIdentifier(@PathVariable Long id) {
        identifierService.deleteIdentifier(id);
    }

    @PutMapping("/{id}")
    public Identifier updateIdentifier(@PathVariable Long id, @RequestBody Identifier identifier) {
        return identifierService.updateIdentifier(id, identifier);
    }
}
