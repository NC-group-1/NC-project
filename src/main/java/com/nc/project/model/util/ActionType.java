package com.nc.project.model.util;

public enum ActionType {
    CLICK(0,0),
    SEND_KEYS(2, 0),
    SWITCH_TAB(0, 0),
    COMPARE_WITH_STRING(2, 0),
    COMPARE_WITH_CONTEXT_VALUE(1, 2),
    SCROLL_PAGE_TO_END(0, 0),
    SAVE_ELEMENT_TEXT_TO_CONTEXT(1, 1),
    SAVE_ELEMENT_ATTRIBUTE_TO_CONTEXT(2, 1),
    FIND_ELEMENT_BY_ID(2, 0),
    FIND_ELEMENT_BY_XPATH(2, 0),
    FIND_ELEMENT_BY_CLASS_NAME(2, 0),
    FIND_ELEMENT_BY_TAG_NAME(2, 0),
    FIND_ELEMENT_BY_NAME(2, 0),
    FIND_ELEMENT_BY_PARTIAL_LINK_TEXT(2, 0),
    FIND_ELEMENT_BY_LINK_TEXT(2, 0),
    FIND_ELEMENT_BY_CSS_SELECTOR(2, 0),
    KEY_UP(0, 0),
    DOUBLE_CLICK(0, 0),
    COMPOUND(0, 0);

    private final Integer needParams;
    private final Integer contextBehaviour;

    /**
     * @return
     * 0 - Doesn't Need ParamKey And Value
     * 1 - Needs ParamKey But No Value
     * 2 - Needs Both ParamKey And Value
     */
    public Integer getNeedParams() {
        return needParams;
    }

    /**
     * @return
     * 0 - Do not works with context
     * 1 - Setter to context
     * 2 - Getter from context
     */
    public Integer getContextBehaviour() {
        return contextBehaviour;
    }

    /**
     *
     * @param needParams
     * 0 - Doesn't Need ParamKey And Value
     * 1 - Needs ParamKey But No Value
     * 2 - Needs Both ParamKey And Value
     *
     * @param contextBehaviour
     * 0 - Do not works with context
     * 1 - Setter to context
     * 2 - Getter from context
     */
    ActionType(Integer needParams, Integer contextBehaviour) {
        this.needParams = needParams;
        this.contextBehaviour = needParams;
    }
}
