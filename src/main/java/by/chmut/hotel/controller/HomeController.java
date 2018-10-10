package by.chmut.hotel.controller;

import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;
import static by.chmut.hotel.controller.constant.Constants.*;


@Controller
public class HomeController {

    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private RoomService roomService;

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping(value = "/home")

    public String mainPage(ModelMap model, Locale locale) {

        String page = HOME;

        try {
            model.addAttribute(ROOMS,roomService.getAllRoom());

        } catch (ServiceException e) {

            logger.error(e);

            page = ERROR;

            model.addAttribute(MESSAGE, messageSource.getMessage(KEY_HOME_PAGE_ERROR,
                                                            new Object[]{}, locale));

        }

        return page;
    }
}
