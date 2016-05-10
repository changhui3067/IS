import {SET_COURSECONFIG_FILTER,
        SET_COURSECONFIG_DELETE,
        SET_COURSECONFIG_DIALOG_VISIBLE,
        SET_COURSECONFIG } from './../actions/courseConfigAction'

export function getInitialState(state) {
    return Object.assign(state, {}, {
        filter: 'default',
        dialog: {
            visible: false,
            field: {}
        },
        tabs:[{
            name: 'Default',
            filter: 'default'
        },{
            name: 'Custom',
            filter: 'custom'
        }],
        list: []
    })
}

const findcourse = (item, id) => {
    return item.id === id
}

const courseConfig = (state = {}, action = {}) => {
    if($.isEmptyObject(state)){
        getInitialState(state)
    }
    switch (action.type) {
        case SET_COURSECONFIG:
            return state

        case SET_COURSECONFIG_FILTER:
            return Object.assign({}, state, {
                filter: action.filter
            })

        case SET_COURSECONFIG_DELETE:
            var temp = Object.assign({}, state, {})
            var indexx = state.list.findIndex((t) => findcourse(t, action.id))
            var deleteItem = temp.list.splice(indexx, 1);
            return temp

        case SET_COURSECONFIG_DIALOG_VISIBLE:
            var dialog = {
                dialog: Object.assign({}, state.dialog, {
                    visible: action.visible,
                    id: action.id
                })
            }
            return Object.assign({}, state, dialog)

        default:
            return state
    }
}

export default courseConfig