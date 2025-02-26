package com.jdbc_template.intracting_with_data.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jdbc_template.intracting_with_data.domain.dto.BookDto;
import com.jdbc_template.intracting_with_data.domain.entities.BookEntity;
import com.jdbc_template.intracting_with_data.mappers.Mapper;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {

    private ModelMapper modelMapperr;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapperr = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapperr.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapperr.map(bookDto, BookEntity.class);
    }
    
}
