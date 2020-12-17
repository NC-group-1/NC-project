package com.nc.project.model.util;

public enum ActionType {
    CLICK(0),
    SEND_KEYS(2),
    SWITCH_TAB(0),
    COMPARE_WITH_STRING(2),
    COMPARE_WITH_CONTEXT_VALUE(1),
    SCROLL_PAGE_TO_END(0),
    SAVE_ELEMENT_TEXT_TO_CONTEXT(1),
    SAVE_ELEMENT_ATTRIBUTE_TO_CONTEXT(2),
    FIND_ELEMENT_BY_ID(2),
    FIND_ELEMENT_BY_XPATH(2),
    FIND_ELEMENT_BY_CLASS_NAME(2),
    FIND_ELEMENT_BY_TAG_NAME(2),
    FIND_ELEMENT_BY_NAME(2),
    FIND_ELEMENT_BY_PARTIAL_LINK_TEXT(2),
    FIND_ELEMENT_BY_LINK_TEXT(2),
    FIND_ELEMENT_BY_CSS_SELECTOR(2),
    COMPOUND(0);

    private final Integer needParams;

    public Integer getNeedParams() {
        return needParams;
    }

    /**
     *
     * @param needParams
     * 0 - Doesn't Need Param And Value
     * 1 - Needs ParamKey But No Value
     * 2 - Needs Both ParamKey And Value
     */
    ActionType(Integer needParams) {
        this.needParams = needParams;
    }
}
