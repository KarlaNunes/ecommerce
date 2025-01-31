package br.ifrn.edu.jeferson.ecommerce.utils;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class CustomPage<T> {
    private int totalPages;
    private long totalElements;
    private int pageNumber;
    private int pageSize;
    private List<T> content;

    public CustomPage(Page<T> page) {
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.content = page.getContent();
    }
}
