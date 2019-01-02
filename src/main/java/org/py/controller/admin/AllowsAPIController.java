package org.py.controller.admin;

import org.py.controller.BaseController;
import org.py.explorer.Selector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/allows")
public class AllowsAPIController implements BaseController {
    @Autowired
    private Selector selector;
    @GetMapping({"txts"})
    public List<String> txts() {
        return selector.getAllowTxts();
    }
    @GetMapping({"imgs"})
    public List<String> imgs() {
        return selector.getAllowImgs();
    }
}
