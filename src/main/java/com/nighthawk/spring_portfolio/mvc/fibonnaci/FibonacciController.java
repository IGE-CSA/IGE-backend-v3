package com.nighthawk.spring_portfolio.mvc.fibonnaci;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nighthawk.spring_portfolio.mvc.fibonnaci.algorithms.Binet;
import com.nighthawk.spring_portfolio.mvc.fibonnaci.algorithms.Matrix;

@RestController
@RequestMapping("/api/fibonacci")
@CrossOrigin(origins="https://ige-csa.github.io/")
public class FibonacciController {

    @Autowired
    private Binet binet; // 92 max

    @Autowired
    private Matrix matrix; // 46 max

    @GetMapping("/binet/{n}")
    public Object calculateBinet(@PathVariable int n) {
        return binet.calculate(n);
    }

    @GetMapping("/matrix/{n}")
    public Object calculateMatrix(@PathVariable int n) {
        return matrix.calculate(n);
    }
}
