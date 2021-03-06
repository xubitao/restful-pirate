package cn.xubitao.pirate.assmbler;

import cn.xubitao.dolphin.foundation.assmbler.DolphinAssembler;
import cn.xubitao.dolphin.foundation.resource.RestResource;
import cn.xubitao.pirate.controller.EntranceController;
import cn.xubitao.pirate.controller.ProvidersController;
import cn.xubitao.pirate.controller.RecordsController;
import cn.xubitao.pirate.controller.StatisticController;
import cn.xubitao.pirate.resource.EntranceResource;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubitao on 12/30/15.
 */
public class EntranceResourceAssembler extends DolphinAssembler {
    public static final int MISSED = 0;

    public EntranceResourceAssembler() {
        super(EntranceController.class, RestResource.class);
    }

    @Override
    public RestResource toRestResource(Object domain, Object... pathVariables) throws Exception {
        this.pathVariables = pathVariables;
        EntranceResource entranceResource = new EntranceResource();
        entranceResource.setDescription("Welcome!");
        Link selfLink = linkTo(EntranceController.class).withSelfRel();
        Link providersLink = linkTo(ProvidersController.class).withRel("providers");
        Link missedRecordsLink = linkTo(methodOn(RecordsController.class).loadAll(null, MISSED, null)).withRel("missedRecords");
        Link statisticLink = linkTo(StatisticController.class).withRel("statistic");

        entranceResource.add(selfLink);
        entranceResource.add(providersLink);
        entranceResource.add(missedRecordsLink);
        entranceResource.add(statisticLink);
        return entranceResource;
    }
}
