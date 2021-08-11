package com.example.demo.service.impl;

import com.example.demo.dao.LoveRepository;
import com.example.demo.dao.PersonRepository;
import com.example.demo.entity.LoveRelation;
import com.example.demo.entity.PersonNode;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author han.xue
 * @since 2021-08-09 17:49:49
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LoveRepository loveRepository;

    @Override
    public PersonNode addPerson(PersonNode person){
        return personRepository.save(person);
    }

    @Override
    public PersonNode findOnePerson(long id) {
        Optional<PersonNode> personNodeOptional = personRepository.findById(id);
        return personNodeOptional.orElse(null);
    }

    @Override
    public LoveRelation loves(LoveRelation love) {
        return loveRepository.save(love);
    }
}

