package com.dnsite.history.controller;

import com.dnsite.history.model.History;
import com.dnsite.history.service.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HistoryController {

    private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);

    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "/history/all", method = RequestMethod.GET)
    @ResponseBody
    public List<History> userConfirmation(){
        logger.info("GET history");
        return historyService.findAll();
    }
}
