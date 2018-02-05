package com.itclj.service;

import java.util.List;

import com.itclj.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CarService implements ICarService {

    @Autowired
    private JdbcTemplate jtm;

    @Override
    public List<Car> findAll() {

        String sql = "SELECT * FROM cars";

        List<Car> cars = jtm.query(sql, new BeanPropertyRowMapper(Car.class));

        return cars;
    }
}