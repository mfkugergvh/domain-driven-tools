package io.ddd.sample.aak.applicaiton.controller;

import io.ddd.sample.aak.applicaiton.view.AccountBookView;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Account Book's REST Controller
 * <p>
 * Query Party's Account Situation
 */
@RestController
@RequestMapping("/v1/account-book/")
public class TheAccountBookController {


    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public AccountBookView queryAccountBookView() {

        return null;
    }


}
