import {SET_HOTJOBS_FILTER,
        SET_HOTJOB_APPLY,
        SET_HOTJOBS_DIALOG_VISIBLE,
        SET_HOTJOBS } from './../actions/hotjobsAction'

export function getInitialState(state) {
    return Object.assign(state, {}, {
        filter: 'open',
        dialog: {
            visible: false,
            field: {}
        },
        tabs: [{
            name: 'Open',
            filter: 'open'
        },{
            name: 'Applied',
            filter: 'applied'
        }],
        list: [{
            id: 0,
            title: 'hotjob1',
            applied: false
        },{
            id: 1,
            title: 'hotjob3',
            applied: false
        },{
            title: 'hotjob2',
            applied: true
        }]
    })
}

const setHotjob = (state, action) => {
    if(state.id !== action.id) {
        return state
    }
    return Object.assign({}, state, {
        applied: true
    })
}

const hotjobs = (state = {}, action = {}) => {
    if($.isEmptyObject(state)) {
        getInitialState(state)
    }
    switch (action.type) {
        case SET_HOTJOBS:
            return state
        case SET_HOTJOBS_FILTER:
            return Object.assign({}, state, {
                filter: action.filter
            })
        case SET_HOTJOB_APPLY:
            return Object.assign({}, state, {
                list: state.list.map(t => setHotjob(t, action))
            })
        case SET_HOTJOBS_DIALOG_VISIBLE:
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

export default hotjobs