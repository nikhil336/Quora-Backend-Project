package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.AnswerEditRequest;
import com.upgrad.quora.api.model.AnswerEditResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExampleController {

    @RequestMapping(path = "/example")
    public ResponseEntity<AnswerEditResponse> example(final AnswerEditRequest answerEditRequest) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
