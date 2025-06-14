package com.election.fullstack.Config;

import com.election.fullstack.Dto.InputDistrictVotesDTO;
import com.election.fullstack.Entity.InputDistrictVotes;
import com.election.fullstack.Entity.District;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Fix ModelMapper bidirectional mapping issue for InputDistrictVotes
        return modelMapper;
    }
}
