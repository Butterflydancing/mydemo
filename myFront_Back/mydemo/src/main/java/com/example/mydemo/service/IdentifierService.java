package com.example.mydemo.service;

import com.example.mydemo.model.Identifier;
import com.example.mydemo.repository.IdentifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdentifierService {

    private final IdentifierRepository identifierRepository;

    @Autowired
    public IdentifierService(IdentifierRepository identifierRepository) {
        this.identifierRepository = identifierRepository;
    }

    // 修改后的分页查询方法
    public Page<Identifier> getIdentifiersPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return identifierRepository.findAll(pageable);
    }

    public List<Identifier> getAllIdentifiers() {
        return identifierRepository.findAll();
    }

    public Identifier saveIdentifier(Identifier identifier) {
        return identifierRepository.save(identifier);
    }

    public void deleteIdentifier(Long id) {
        identifierRepository.deleteById(id);
    }

    public Identifier updateIdentifier(Long id, Identifier newIdentifier) {
        // 检查是否存在要更新的 Identifier 对象
        return identifierRepository.findById(id)
                .map(existingIdentifier -> {
                    existingIdentifier.setName(newIdentifier.getName());
                    return identifierRepository.save(existingIdentifier);
                })
                .orElseThrow(() -> new IllegalArgumentException("Identifier with ID " + id + " not found"));
    }
}
