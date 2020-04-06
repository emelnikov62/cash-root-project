package ru.wecant.model;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

/**
 * Description.
 * @author wecant
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SimpleViewModel {

    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponse() {
        return String.format("Hello %s!", name);
    }

    @Command
    public void submit() {
        BindUtils.postNotifyChange(null, null, this, "response");
    }
}
