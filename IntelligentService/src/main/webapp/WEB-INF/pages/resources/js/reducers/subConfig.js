/**
 * Created by freyjachang on 5/2/16.
 */

import {SET_SUBCONFIG_SWITCHER,
        SET_SUBTAB_FILTER,
        SET_SUBCONFIG } from './../actions/subConfigAction'

export function getInitialState(state) {
    return Object.assign(state, {}, {
        filter: 'event0',
        list: [{
            name: 'event0',
            subs: [{
                name: 'subscriber0000',
                desc: 'desc0',
                ison: true
            },{
                name: 'subscriber1111',
                desc: 'desc1',
                ison: false
            }]
        },{
            name: 'event1',
            subs: [{
                name: 'subscriber222',
                desc: 'desc2222222222',
                ison: false
            },{
                name: 'subscriber333',
                desc: 'desc333333333',
                ison: true
            }]
        },{
            name: 'event2',
            subs: [{
                name: 'subscriber444',
                desc: 'desc4444444',
                ison: false
            },{
                name: 'subscriber555',
                desc: 'desc555555',
                ison: true
            }]
        }]
    })
}

const switcher = (state, action) => {
    switch(action.type) {
        case SET_SUBCONFIG_SWITCHER:
            console.log(state, action)
            if( state.name === action.switcher) {
                return Object.assign({}, state, {
                    ison: !state.ison
                })
            }
            return state
        default:
            return state
    }
}

const subscriber = (state, action, filter) => {
    if( state.name === filter){
        return Object.assign({}, state, {
            subs: state.subs.map( t => switcher(t, action))
        })
    }
    return state
}

const subConfig = (state = {}, action ={}) => {
    if($.isEmptyObject(state)) {
        getInitialState(state)
    }
    switch (action.type) {
        case SET_SUBCONFIG:
            return state

        case SET_SUBTAB_FILTER:
            return Object.assign({}, state, {
                filter: action.filter
            })

        case SET_SUBCONFIG_SWITCHER:
            return Object.assign({}, state, {
                list: state.list.map( t => subscriber(t, action, state.filter) )
            })

        default:
            return state
    }
}

export default subConfig