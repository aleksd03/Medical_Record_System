package com.nbu.medicalrecords.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        ModelMapper mapper = modelMapper();
        return source.stream()
                .map(element -> (T) mapper.map(element, targetClass))
                .toList();
    }
}
